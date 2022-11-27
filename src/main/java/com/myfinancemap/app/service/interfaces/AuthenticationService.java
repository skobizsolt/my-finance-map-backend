package com.myfinancemap.app.service.interfaces;

import com.myfinancemap.app.dto.PasswordDto;
import com.myfinancemap.app.dto.user.CreateUserDto;
import com.myfinancemap.app.persistence.domain.User;
import com.myfinancemap.app.persistence.domain.auth.VerificationToken;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

/**
 * Interface class of the Authentication service.
 */
public interface AuthenticationService {

    /**
     * Method for creating a new user.
     *
     * @param createUserDto provides essential data towards User entity
     * @param requestUrl    url of the verification email.
     * @return a message with the verfication link.
     */
    ResponseEntity<String> registerUser(final CreateUserDto createUserDto, final String requestUrl);

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
    ResponseEntity<String> checkToken(final String token);

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

    String setNewPassword(final PasswordDto passwordDto);

    String validatePasswordResetToken(final String token);

    Optional<User> getUserByPasswordResetToken(final String token);

    ResponseEntity<String> setNewPassword(final User user, final String newPassword);

    ResponseEntity<String> changePassword(final PasswordDto passwordDto);
}
