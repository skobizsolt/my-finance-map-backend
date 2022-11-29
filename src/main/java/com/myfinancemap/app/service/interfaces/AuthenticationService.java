package com.myfinancemap.app.service.interfaces;

import com.myfinancemap.app.dto.PasswordDto;
import com.myfinancemap.app.dto.user.CreateUserDto;
import com.myfinancemap.app.persistence.domain.User;
import com.myfinancemap.app.persistence.domain.auth.VerificationToken;
import org.springframework.http.ResponseEntity;

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
     * Method for verifying the registration.
     *
     * @param token authorization token associated to the user
     * @return token verification message.
     */
    ResponseEntity<String> verifyRegistration(final String token);

    /**
     * Method for generating a new token.
     *
     * @param oldToken authorization token associated to the user
     * @return a new token.
     */
    VerificationToken generateNewVerificationToken(final String oldToken);

    /**
     * Method for create a password reset token.
     *
     * @param passwordDto dto that contains the email
     * @return Status message.
     */
    String createPasswordResetToken(final PasswordDto passwordDto);

    /**
     * Method for saving the new password
     *
     * @param token which came from the reset password email
     * @param passwordDto dto that contains the new pwd twice
     * @return ResponseEntity whether the pwd change was successful.
     */
    ResponseEntity<String> saveNewPassword(final String token, final PasswordDto passwordDto);

    /**
     * Method for changing old password to a new one.
     *
     * @param passwordDto dto that contains an email, the old pwd and the new pwd twice
     * @return ResponseEntity whether the pwd change was successful or not.
     */
    ResponseEntity<String> changeExistingPassword(final PasswordDto passwordDto);

    /**
     * Method for saving a created authentication token.
     *
     * @param token used for authentication
     * @param user  we want to grant it for.
     */
    void saveVerificationToken(final String token, final User user);


}
