package com.myfinancemap.app.service;

import com.myfinancemap.app.dto.PasswordDto;
import com.myfinancemap.app.dto.TokenType;
import com.myfinancemap.app.dto.user.CreateUserDto;
import com.myfinancemap.app.event.RegistrationEvent;
import com.myfinancemap.app.mapper.UserMapper;
import com.myfinancemap.app.persistence.domain.User;
import com.myfinancemap.app.persistence.domain.auth.AuthenticationToken;
import com.myfinancemap.app.persistence.repository.UserRepository;
import com.myfinancemap.app.persistence.repository.auth.TokenRepository;
import com.myfinancemap.app.service.interfaces.AuthenticationService;
import com.myfinancemap.app.service.interfaces.MailService;
import com.myfinancemap.app.service.interfaces.ProfileService;
import com.myfinancemap.app.util.ServerUtils;
import com.myfinancemap.app.util.TokenUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import static com.myfinancemap.app.util.TokenUtils.*;

/**
 * Default implementation of the Authentication service.
 */
@Service
@AllArgsConstructor
@Slf4j
public class DefaultAuthenticationService implements AuthenticationService {
    @Autowired
    private final TokenRepository tokenRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ProfileService profileService;
    @Autowired
    private final MailService mailService;
    @Autowired
    private final ApplicationEventPublisher publisher;

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<String> registerUser(final CreateUserDto createUserDto,
                                               final String requestUrl) {
        final User user = userMapper.toUser(createUserDto);
        user.setPublicId(UUID.randomUUID().toString());
        checkMatchingPasswords(createUserDto.getPassword(), createUserDto.getMatchingPassword());
        // password encryption
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        userRepository.save(user);
        // send verification email
        publisher.publishEvent(
                new RegistrationEvent(
                        user,
                        requestUrl));
        return ResponseEntity
                .ok()
                .body("Verification email sent to " + user.getEmail());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<String> verifyRegistration(final String token) {
        final String responseMessage = checkTokenCondition(token, TokenType.VERIFY);
        return STATUS_VALID.equals(responseMessage)
                ? ResponseEntity.ok().body("Successful verification!")
                : ResponseEntity.badRequest()
                .body("Invalid token! Please send a new registration!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<String> sendNewVerificationToken(final String oldToken,
                                                           final HttpServletRequest httpServletRequest) {
        final AuthenticationToken verificationToken =
                getToken(oldToken, TokenType.VERIFY);
        if (verificationToken != null) {
            verificationToken.setToken(UUID.randomUUID().toString());
            tokenRepository.save(verificationToken);
            mailService.resendVerificationTokenEmail(ServerUtils.applicationUrl(httpServletRequest), verificationToken);
            return ResponseEntity
                    .ok()
                    .body("New link sent!");
        }
        return ResponseEntity
                .badRequest()
                .body(getBadRequestResponseMessage(STATUS_INVALID));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<String> resetPassword(final PasswordDto passwordDto,
                                                final HttpServletRequest httpServletRequest) {
        final User user = userRepository.getUserByEmail(passwordDto.getEmail()).orElse(null);
        if (user != null) {
            final AuthenticationToken existingPasswordToken =
                    tokenRepository.findTokenByUserId(
                            user.getUserId(), TokenType.PASSWORD.name()).orElse(null);
            //Token not exist
            if (existingPasswordToken == null) {
                final String newPasswordToken = UUID.randomUUID().toString();
                saveToken(newPasswordToken, user, TokenType.PASSWORD);
                mailService.passwordResetTokenMail(
                        ServerUtils.applicationUrl(httpServletRequest),
                        newPasswordToken);
                return ResponseEntity
                        .ok()
                        .body("Email sent for password reset!");
            }
            mailService.passwordResetTokenMail(
                    ServerUtils.applicationUrl(httpServletRequest),
                    existingPasswordToken.getToken());
            return ResponseEntity
                    .ok()
                    .body("New email sent for password reset!");
        }
        return ResponseEntity
                .badRequest()
                .body("User not found!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<String> saveNewPassword(final String token, final PasswordDto passwordDto) {
        // checking if passwords are matching
        checkMatchingPasswords(passwordDto.getNewPassword(), passwordDto.getMatchingPassword());
        // checking and getting the token
        final String result = checkTokenCondition(token, TokenType.PASSWORD);
        final AuthenticationToken passwordToken = getToken(token, TokenType.PASSWORD);
        // return if token not valid
        if (!STATUS_VALID.equals(result)) {
            return ResponseEntity.badRequest().body(getBadRequestResponseMessage(result)
                    + " Please send a new password reset request!");
        }
        if (passwordToken != null) {
            final User user = passwordToken.getUser();
            // checking whether the token is associated with a user or not
            if (user != null) {
                // changing password
                return changePassword(user, passwordDto.getNewPassword());
            }
        }
        // if somehow the token is not attached to any user
        return ResponseEntity
                .badRequest()
                .body("Password reset not initiated due to an error.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<String> changeExistingPassword(final PasswordDto passwordDto) {
        final User user = userRepository.getUserByEmail(passwordDto.getEmail()).orElse(null);
        if (user == null) {
            return ResponseEntity
                    .badRequest()
                    .body("User not found.");
        }
        if (!passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid old password!");
        }
        checkMatchingPasswords(passwordDto.getNewPassword(), passwordDto.getMatchingPassword());
        return changePassword(user, passwordDto.getNewPassword());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveToken(final String token, final User user, final TokenType tokenType) {
        final AuthenticationToken verificationToken = new AuthenticationToken(token, user);
        verificationToken.setTokenType(tokenType);
        tokenRepository.save(verificationToken);
    }

    //region UTILITY METHODS
    private String checkTokenCondition(final String tokenUuid, final TokenType tokenType) {
        final AuthenticationToken token = getToken(tokenUuid, tokenType);
        if (token == null) {
            return STATUS_INVALID;
        }
        if (TokenUtils.timeDifference(token) <= 0) {
            tokenRepository.delete(token);
            // if the verification time is expired, the registration should be deleted
            if (tokenType == TokenType.VERIFY) {
                userRepository.delete(token.getUser());
            }
            return STATUS_EXPIRED;
        }
        if (tokenType == TokenType.VERIFY) {
            final User user = token.getUser();
            user.setEnabled(true);
            user.setProfile(profileService.createProfile());
            userRepository.save(user);
            tokenRepository.delete(token);
        }
        return STATUS_VALID;
    }

    private AuthenticationToken getToken(final String token, final TokenType tokenType) {
        return tokenRepository.findByToken(token, tokenType.name()).orElse(null);
    }

    private ResponseEntity<String> changePassword(final User user, final String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return ResponseEntity
                .ok()
                .body("Password changed successfully!");
    }

    private void checkMatchingPasswords(final String password, final String passwordAgain) {
        if (!password.equals(passwordAgain)) {
            throw new ServiceException("Passwords are not matching!");
        }
    }

    private String getBadRequestResponseMessage(final String response) {
        // checking if the token is exists
        if (response.equals(STATUS_INVALID)) {
            return "Invalid token!";
        }
        // checking if token is expired
        if (response.equals(STATUS_EXPIRED)) {
            return "This token is expired!";
        }
        return STATUS_VALID;
    }
    //endregion
}
