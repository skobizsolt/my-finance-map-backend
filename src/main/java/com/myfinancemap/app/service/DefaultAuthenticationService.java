package com.myfinancemap.app.service;

import com.myfinancemap.app.dto.PasswordDto;
import com.myfinancemap.app.dto.user.CreateUserDto;
import com.myfinancemap.app.event.RegistrationEvent;
import com.myfinancemap.app.mapper.UserMapper;
import com.myfinancemap.app.persistence.domain.User;
import com.myfinancemap.app.persistence.domain.auth.PasswordResetToken;
import com.myfinancemap.app.persistence.domain.auth.VerificationToken;
import com.myfinancemap.app.persistence.repository.UserRepository;
import com.myfinancemap.app.persistence.repository.auth.PasswordResetTokenRepository;
import com.myfinancemap.app.persistence.repository.auth.VerificationTokenRepository;
import com.myfinancemap.app.service.interfaces.AuthenticationService;
import com.myfinancemap.app.service.interfaces.ProfileService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

/**
 * Default implementation of the Authentication service.
 */
@Service
public class DefaultAuthenticationService implements AuthenticationService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ProfileService profileService;

    private final ApplicationEventPublisher publisher;

    public DefaultAuthenticationService(final VerificationTokenRepository verificationTokenRepository,
                                        final PasswordResetTokenRepository passwordResetTokenRepository,
                                        final PasswordEncoder passwordEncoder,
                                        final UserMapper userMapper,
                                        final UserRepository userRepository,
                                        final ProfileService profileService,
                                        final ApplicationEventPublisher publisher) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.profileService = profileService;
        this.publisher = publisher;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<String> registerUser(final CreateUserDto createUserDto, final String requestUrl) {
        final User user = userMapper.toUser(createUserDto);
        user.setPublicId(UUID.randomUUID().toString());
        // register
        checkMatchingPasswords(createUserDto.getPassword(), createUserDto.getMatchingPassword());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        user.setProfile(profileService.createProfile());
        userRepository.save(user);
        // send verification email
        publisher.publishEvent(
                new RegistrationEvent(
                        user,
                        requestUrl));
        return ResponseEntity.ok().body("Verification email sent to " + user.getEmail());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<String> verifyRegistration(final String token) {
        final VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElse(null);
        if (verificationToken == null) {
            return getTokenStatusMessage("invalid");
        }
        final User user = verificationToken.getUser();
        final Calendar calendar = Calendar.getInstance();
        final long timeDifference = verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime();

        if (timeDifference <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return getTokenStatusMessage("expired");
        }
        user.setEnabled(true);
        userRepository.save(user);
        return getTokenStatusMessage("valid");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VerificationToken generateNewVerificationToken(final String oldToken) {
        final VerificationToken verificationToken =
                verificationTokenRepository.findByToken(oldToken).orElse(new VerificationToken());
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createPasswordResetToken(PasswordDto passwordDto) {
        final User user = userRepository.getUserByEmail(passwordDto.getEmail()).orElse(null);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            savePasswordResetTokenForUser(user, token);
            return token;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<String> saveNewPassword(String token, PasswordDto passwordDto) {
        final String result = validatePasswordResetToken(token);
        if (!result.equalsIgnoreCase(("valid"))) {
            return ResponseEntity.badRequest().body("Invalid token!");
        }
        final User user = getUserByPasswordResetToken(token);
        if (user != null) {
            return resetPassword(user, passwordDto.getNewPassword());
        }
        return ResponseEntity.badRequest().body("Password reset not initiated due to error.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<String> changeExistingPassword(PasswordDto passwordDto) {
        final User user = userRepository.getUserByEmail(passwordDto.getEmail()).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        checkIfValidOldPassword(user, passwordDto.getOldPassword());
        checkMatchingPasswords(passwordDto.getOldPassword(), passwordDto.getMatchingPassword());
        return resetPassword(user, passwordDto.getNewPassword());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveVerificationToken(String token, User user) {
        final VerificationToken verificationToken
                = new VerificationToken(token, user);
        verificationTokenRepository.save(verificationToken);
    }

    //region "UTILS"
    private void checkMatchingPasswords(final String password, final String passwordAgain) {
        if (!password.equals(passwordAgain)) {
            throw new ServiceException("Passwords are not matching!");
        }
    }

    private ResponseEntity<String> getTokenStatusMessage(final String response) {
        if (!response.equalsIgnoreCase("valid")) {
            return ResponseEntity.badRequest().body("Bad user.");
        }
        return ResponseEntity.ok().body("Successful verification!");
    }

    public void savePasswordResetTokenForUser(User user, String token) {
        final PasswordResetToken passwordResetToken =
                new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    public String validatePasswordResetToken(final String token) {
        final PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token).orElse(null);
        if (passwordResetToken == null) {
            return "invalid";
        }
        final Calendar calendar = Calendar.getInstance();
        final long timeDifference = passwordResetToken.getExpiryDate().getTime() - calendar.getTime().getTime();

        if (timeDifference <= 0) {
            passwordResetTokenRepository.delete(passwordResetToken);
            return "expired";
        }
        return "valid";
    }

    public User getUserByPasswordResetToken(final String token) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token).orElse(null);
        return resetToken != null ? resetToken.getUser() : null;
    }

    public ResponseEntity<String> resetPassword(final User user, final String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return ResponseEntity.ok().body("Password changed successfully!");
    }

    private void checkIfValidOldPassword(final User user, final String oldPassword) {
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ServiceException("Invalid password!");
        }
    }
    //endregion
}
