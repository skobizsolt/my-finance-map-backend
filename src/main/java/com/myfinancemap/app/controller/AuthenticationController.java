package com.myfinancemap.app.controller;

import com.myfinancemap.app.dto.PasswordDto;
import com.myfinancemap.app.dto.user.CreateUserDto;
import com.myfinancemap.app.persistence.domain.auth.VerificationToken;
import com.myfinancemap.app.service.interfaces.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Creates a new user.
     *
     * @param createUserDto provides all essential user data.
     * @return with MinimalUserDto, containing the created entity's data.
     */
    @PostMapping(value = "/register")
    @Operation(summary = "Create new User")
    public ResponseEntity<String> registerUser(@Valid @RequestBody final CreateUserDto createUserDto,
                                               final HttpServletRequest request) {
        log.info("Endpoint invoked. createUserDto = {}", createUserDto);
        return authenticationService.registerUser(createUserDto, applicationUrl(request));
    }

    /**
     * Endpoint for verifying a registration.
     *
     * @param token that refers to the user.
     * @return response message.
     */
    @GetMapping(value = "/verify-registration")
    @Operation(summary = "Verifying the newly created user")
    public ResponseEntity<String> verifyRegistration(@RequestParam("token") final String token) {
        log.info("Endpoint invoked. token = {}", token);
        return authenticationService.verifyRegistration(token);
    }

    /**
     * Endpoint for resending a valid, existing token
     *
     * @param oldToken that refers to the user.
     * @param request  HTTP servlet.
     * @return response message.
     */
    @GetMapping(value = "/resend-verification")
    @Operation(summary = "Generates a new, active verification token")
    public ResponseEntity<String> generateNewVerificationToken(@RequestParam("token") final String oldToken,
                                                          final HttpServletRequest request) {
        log.info("Endpoint invoked. oldToken = {}", oldToken);
        final VerificationToken verificationToken = authenticationService.generateNewVerificationToken(oldToken);
        resendVerificationTokenEmail(applicationUrl(request), verificationToken);
        return ResponseEntity.ok().body("New link sent!");
    }

    @PostMapping(value = "/reset-password")
    @Operation(summary = "Reset old password")
    public ResponseEntity<String> resetPassword(@RequestBody final PasswordDto passwordDto,
                                                final HttpServletRequest request) {
        log.info("Endpoint invoked. passwordDto = {}", passwordDto);
        final String token = authenticationService.createPasswordResetToken(passwordDto);
        if (token != null) {
            passwordResetTokenMail(applicationUrl(request), token);
            return ResponseEntity.ok().body("Email sent for password reset!");
        }
        return ResponseEntity.badRequest().body("User not found!");
    }

    @PostMapping(value = "/save-password")
    @Operation(summary = "Save new password")
    public ResponseEntity<String> saveNewPassword(@RequestParam("token") final String token,
                                               @RequestBody final PasswordDto passwordDto,
                                               final HttpServletRequest request) {
        log.info("Endpoint invoked. token: {}, passwordDto = {}", token, passwordDto);
        return authenticationService.saveNewPassword(token, passwordDto);
    }

    @PostMapping(value = "/change-password")
    @Operation(summary = "Change old password to a new one")
    public ResponseEntity<String> changeExistingPassword(@RequestBody final PasswordDto passwordDto,
                                               final HttpServletRequest request) {
        log.info("Endpoint invoked. passwordDto = {}", passwordDto);
        return authenticationService.changeExistingPassword(passwordDto);
    }

    private void passwordResetTokenMail(final String applicationUrl, final String token) {
        final String url = applicationUrl +
                "/api/auth/save-password?token=" + token;

        log.info("Click to the link to reset your password: {}", url);
    }

    private void resendVerificationTokenEmail(final String applicationUrl,
                                              final VerificationToken verificationToken) {
        final String url = applicationUrl +
                "/api/auth/verify-registration?token=" + verificationToken.getToken();

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
