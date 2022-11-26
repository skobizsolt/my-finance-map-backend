package com.myfinancemap.app.service.interfaces;

import com.myfinancemap.app.persistence.domain.User;
import com.myfinancemap.app.persistence.domain.auth.VerificationToken;

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
}
