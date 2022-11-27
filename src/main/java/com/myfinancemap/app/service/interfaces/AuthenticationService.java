package com.myfinancemap.app.service.interfaces;

import com.myfinancemap.app.dto.PasswordDto;
import com.myfinancemap.app.persistence.domain.User;
import com.myfinancemap.app.persistence.domain.auth.VerificationToken;

import java.util.Optional;

/**
 * Interface class of the Authentication service.
 */
public interface AuthenticationService {

    /**
     * Method for saving a created authentication token.
     *
     * @param token used for authentication
     * @param user  we want to grant it for.
     */
    void saveVerificationToken(final String token, final User user);

    /**
     * Method for verifying the token.
     *
     * @param token authorization token associated to the user
     * @return token verification message.
     */
    String checkToken(final String token);

    /**
     * Method for generating a new token.
     *
     * @param oldToken authorization token associated to the user
     * @return a new token.
     */
    VerificationToken generateNewVerificationToken(final String oldToken);

    /**
     * Method for creating a token for password reset.
     *
     * @param user  is the user we want to give the token
     * @param token is the token it can initiate pwd reset with.
     */
    void createPasswordResetTokenForUser(final User user, final String token);

    String resetPassword(final PasswordDto passwordDto);

    String validatePasswordResetToken(final String token);

    Optional<User> getUserByPasswordResetToken(final String token);

    void changePassword(final User user, final String newPassword);
}
