package com.myfinancemap.app.service.interfaces;

import com.myfinancemap.app.persistence.domain.auth.AuthenticationToken;

/**
 * Interface class for sending emails.
 */
public interface MailService {
    /**
     * Method for sending a password reset email.
     *
     * @param applicationUrl url of the server
     * @param token that refers to the user
     */
    void passwordResetTokenMail(final String applicationUrl, final String token);

    /**
     * Method for sending a registration verification email.
     *
     * @param applicationUrl url of the server
     * @param verificationToken that refers to the user
     */
    void resendVerificationTokenEmail(final String applicationUrl,
                                      final AuthenticationToken verificationToken);
}
