package com.myfinancemap.app.service;

import com.myfinancemap.app.dto.PasswordDto;
import com.myfinancemap.app.persistence.domain.User;
import com.myfinancemap.app.persistence.domain.auth.PasswordResetToken;
import com.myfinancemap.app.persistence.domain.auth.VerificationToken;
import com.myfinancemap.app.persistence.repository.auth.PasswordResetTokenRepository;
import com.myfinancemap.app.persistence.repository.auth.VerificationTokenRepository;
import com.myfinancemap.app.service.interfaces.AuthenticationService;
import com.myfinancemap.app.service.interfaces.UserService;
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
    private final UserService userService;

    public DefaultAuthenticationService(final VerificationTokenRepository verificationTokenRepository,
                                        final PasswordResetTokenRepository passwordResetTokenRepository,
                                        final PasswordEncoder passwordEncoder,
                                        final UserService userService) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
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
    public String checkToken(final String token) {
        final VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElse(null);
        if (verificationToken == null) {
            return "invalid";
        }

        final User user = verificationToken.getUser();
        final Calendar calendar = Calendar.getInstance();
        final long timeDifference = verificationToken.getExpiryDate().getTime() - calendar.getTime().getTime();

        if (timeDifference <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }
        userService.verifyUser(user);
        return "valid";
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
    public String resetPassword(PasswordDto passwordDto) {
        final User user = userService.getUserEntityByEmail(passwordDto.getEmail());
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
    public void changePassword(final User user, final String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        //TODO: continue (1:17:12)
    }
}
