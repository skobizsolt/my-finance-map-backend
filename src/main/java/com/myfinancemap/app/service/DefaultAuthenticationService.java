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
import java.util.Objects;
import java.util.Optional;
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
        userRepository.save(user);
        // send verification email
        publisher.publishEvent(
                new RegistrationEvent(
                        user,
                        requestUrl));
        // creating a new profile
        user.setProfile(profileService.createProfile());
        return ResponseEntity.ok().body("Verification email sent to " + user.getEmail());
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

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<String> checkToken(final String token) {
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

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        final PasswordResetToken passwordResetToken =
                new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public String setNewPassword(PasswordDto passwordDto) {
        final User user = userRepository.getUserByEmail(passwordDto.getEmail()).orElse(null);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            createPasswordResetTokenForUser(user, token);
            return token;
        }
        return null;
    }

    //TODO: refactor
    @Override
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

    @Override
    public Optional<User> getUserByPasswordResetToken(final String token) {
        return Optional.ofNullable(Objects.requireNonNull(passwordResetTokenRepository.findByToken(token).orElse(null)).getUser());
    }

    @Override
    public ResponseEntity<String> setNewPassword(final User user, final String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return ResponseEntity.ok().body("Password changed successfully!");
    }

    @Override
    public ResponseEntity<String> changePassword(PasswordDto passwordDto) {
        final User user = userRepository.getUserByEmail(passwordDto.getEmail()).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        checkIfValidOldPassword(user, passwordDto.getOldPassword());
        checkMatchingPasswords(passwordDto.getOldPassword(), passwordDto.getMatchingPassword());
        return setNewPassword(user, passwordDto.getNewPassword());
    }

    private void checkIfValidOldPassword(final User user, final String oldPassword) {
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ServiceException("Invalid password!");
        }
    }

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
}
