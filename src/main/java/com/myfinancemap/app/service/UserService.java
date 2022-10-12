package com.myfinancemap.app.service;

import com.myfinancemap.app.dto.CreateUserDto;
import com.myfinancemap.app.dto.MinimalUserDto;
import com.myfinancemap.app.dto.UserDto;

import java.util.List;

/**
 * Interface for UserService
 */
public interface UserService {
    List<MinimalUserDto> getAllUsers();

    UserDto getUserById(final Long userId);

    MinimalUserDto createUser(final CreateUserDto createUserDto);

    void deleteUser(final Long userId);
}
