package com.myfinancemap.app.util;

import com.myfinancemap.app.dto.TokenType;
import com.myfinancemap.app.event.RegistrationEvent;
import com.myfinancemap.app.persistence.domain.User;
import com.myfinancemap.app.service.interfaces.AuthenticationService;
import com.myfinancemap.app.service.interfaces.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class RegistrationEventListener implements ApplicationListener<RegistrationEvent> {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private MailService mailService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onApplicationEvent(final RegistrationEvent event) {
        final User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        authenticationService.saveToken(token, user, TokenType.VERIFY);

        // send verification email
        mailService.sendEmail(
                event.getApplicationUrl(),
                token,
                user.getEmail(),
                user.getUsername(), EmailType.REGISTRATION_EMAIL);
    }
}
