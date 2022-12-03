package com.myfinancemap.app.service.interfaces;

import com.myfinancemap.app.util.EmailType;

/**
 * Interface class for sending emails.
 */
public interface MailService {
    /**
     * Method for sending a password reset email.
     *
     * @param applicationUrl url of the server
     * @param token          that refers to the user
     * @param toEmail        the user's email address
     * @param username
     * @param emailType
     */
    void sendEmail(final String applicationUrl,
                   final String token,
                   final String toEmail,
                   final String username,
                   final EmailType emailType);
}
