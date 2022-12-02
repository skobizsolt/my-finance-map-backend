package com.myfinancemap.app.service;

import com.myfinancemap.app.dto.user.MinimalUserDto;
import com.myfinancemap.app.dto.user.UpdateUserDto;
import com.myfinancemap.app.dto.user.UserDto;
import com.myfinancemap.app.mapper.UserMapper;
import com.myfinancemap.app.persistence.domain.User;
import com.myfinancemap.app.persistence.repository.UserRepository;
import com.myfinancemap.app.service.interfaces.ProfileService;
import com.myfinancemap.app.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Default implementation of User service.
 */
@Service
@AllArgsConstructor
public class DefaultUserService implements UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private final ProfileService profileService;

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<MinimalUserDto> getAllUsers() {
        return userMapper.toMinimalUserDtoList(userRepository.findAll());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or hasUser(#userId)")
    public UserDto getUserById(final Long userId) {
        final User user = getUserEntityById(userId);
        return userMapper.toUserDto(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or hasUser(#userId)")
    public void deleteUser(final Long userId) {
        final User user = getUserEntityById(userId);
        // delete user
        userRepository.delete(user);
        //delete profile
        profileService.deleteProfile(user.getProfile().getProfileId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or hasUser(#userId)")
    public UserDto updateUser(final Long userId, final UpdateUserDto updateUserDto) {
        final User user = getUserEntityById(userId);
        //updating profile
        profileService.updateProfile(user.getProfile().getProfileId(), updateUserDto.getProfile());
        //updating user
        userMapper.modifyUser(updateUserDto, user);
        userRepository.saveAndFlush(user);
        return userMapper.toUserDto(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUserEntityById(final Long userId) {
        return userRepository.getUserByUserId(userId)
                .orElseThrow(() -> {
                    throw new NoSuchElementException("Felhasználó nem található!");
                });
    }
}
