package com.myfinancemap.app.controller;

import com.myfinancemap.app.persistence.domain.auth.VerificationToken;
import com.myfinancemap.app.service.interfaces.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Endpoint for verifying a registration.
     *
     * @param token that refers to the user.
     * @return response message.
     */
    @GetMapping(value = "/verifyRegistration")
    @Operation(summary = "Verifying the newly created user")
    public ResponseEntity<String> verifyUser(@RequestParam("token") final String token) {
        log.info("Endpoint invoked. token = {}", token);
        final String response = authenticationService.checkToken(token);
        if (!response.equalsIgnoreCase("valid")) {
            return ResponseEntity.badRequest().body("Bad user.");
        }
        return ResponseEntity.ok().body("Successful verification!");
    }

    /**
     * Endpoint for resending a valid, existing token
     *
     * @param oldToken that refers to the user.
     * @param request  HTTP servlet.
     * @return response message.
     */
    @GetMapping(value = "/resendVerification")
    @Operation(summary = "Resend verification token")
    public ResponseEntity<String> resendVerificationToken(@RequestParam("token") final String oldToken,
                                                          final HttpServletRequest request) {
        log.info("Endpoint invoked. oldToken = {}", oldToken);
        final VerificationToken verificationToken = authenticationService.generateNewVerificationToken(oldToken);
        resendVerificationTokenEmail(applicationUrl(request), verificationToken);
        return ResponseEntity.ok().body("New link sent!");
    }

    private void resendVerificationTokenEmail(final String applicationUrl,
                                              final VerificationToken verificationToken) {
        final String url = applicationUrl +
                "/api/auth/verifyRegistration?token=" + verificationToken.getToken();

        log.info("Click to the link to verify your account: {}", url);
    }

    private String applicationUrl(final HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
