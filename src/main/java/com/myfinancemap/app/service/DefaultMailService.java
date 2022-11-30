package com.myfinancemap.app.service;

import com.myfinancemap.app.persistence.domain.auth.AuthenticationToken;
import com.myfinancemap.app.service.interfaces.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DefaultMailService implements MailService {
    //TODO: implement emails
    @Override
    public void passwordResetTokenMail(final String applicationUrl, final String token) {
        final String url = applicationUrl +
                "/api/auth/save-password?token=" + token;

        log.info("Click to the link to reset your password: {}", url);
    }

    @Override
    public void resendVerificationTokenEmail(final String applicationUrl,
                                             final AuthenticationToken verificationToken) {
        final String url = applicationUrl +
                "/api/auth/verify-registration?token=" + verificationToken.getToken();

        log.info("Click to the link to verify your account: {}", url);
    }
}
