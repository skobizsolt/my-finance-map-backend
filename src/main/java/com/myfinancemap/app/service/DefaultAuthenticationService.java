package com.myfinancemap.app.service;

import com.myfinancemap.app.persistence.domain.User;
import com.myfinancemap.app.persistence.domain.auth.VerificationToken;
import com.myfinancemap.app.persistence.repository.auth.VerificationTokenRepository;
import com.myfinancemap.app.service.interfaces.AuthenticationService;
import com.myfinancemap.app.service.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

/**
 * Default implementation of the Authentication service.
 */
@Service
public class DefaultAuthenticationService implements AuthenticationService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserService userService;

    public DefaultAuthenticationService(VerificationTokenRepository verificationTokenRepository, UserService userService) {
        this.verificationTokenRepository = verificationTokenRepository;
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
        VerificationToken verificationToken =
                verificationTokenRepository.findByToken(oldToken).orElse(new VerificationToken());
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }
}
