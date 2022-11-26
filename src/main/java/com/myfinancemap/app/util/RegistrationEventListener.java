package com.myfinancemap.app.util;

import com.myfinancemap.app.event.RegistrationEvent;
import com.myfinancemap.app.persistence.domain.User;
import com.myfinancemap.app.service.interfaces.AuthenticationService;
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


    /**
     * {@inheritDoc}
     */
    @Override
    public void onApplicationEvent(final RegistrationEvent event) {
        final User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        authenticationService.saveVerificationToken(token, user);

        final String url = event.getApplicationUrl() +
                "/api/auth/verifyRegistration?token=" + token;

        log.info("Click to the link to verify your account: {}", url);
    }
}
