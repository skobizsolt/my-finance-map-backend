package com.myfinancemap.app.service.interfaces;

import com.myfinancemap.app.dto.user.CreateUserDto;
import com.myfinancemap.app.dto.user.MinimalUserDto;
import com.myfinancemap.app.dto.user.UpdateUserDto;
import com.myfinancemap.app.dto.user.UserDto;

import java.util.List;

/**
 * Interface for UserService
 */
public interface UserService {
    /**
     * Method for listing all users basic data.
     *
     * @return all users.
     */
    List<MinimalUserDto> getAllUsers();

    /**
     * Method for listing data of the given user.
     *
     * @param userId id of the user we need
     * @return UserDto object.
     */
    UserDto getUserById(final Long userId);

    /**
     * Method for creating a new user.
     *
     * @param createUserDto provides essential data towards User entity
     * @return MinimalUserDto that contains basic user data.
     */
    MinimalUserDto createUser(final CreateUserDto createUserDto);

    /**
     * Method for removing an existing user.
     *
     * @param userId id of the user we want to delete.
     */
    void deleteUser(final Long userId);

    /**
     * Method for updating an existing user's profile and address.
     *
     * @param userId        id of the user we want to update.
     * @param updateUserDto data of the user we want to update.
     * @return the updated user's data.
     */
    UserDto updateUser(final Long userId, final UpdateUserDto updateUserDto);
}
