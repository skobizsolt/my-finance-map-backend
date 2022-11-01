package com.myfinancemap.app.service;

import com.myfinancemap.app.dto.user.CreateUserDto;
import com.myfinancemap.app.dto.user.MinimalUserDto;
import com.myfinancemap.app.dto.user.UpdateUserDto;
import com.myfinancemap.app.dto.user.UserDto;
import com.myfinancemap.app.mapper.UserMapper;
import com.myfinancemap.app.persistence.domain.User;
import com.myfinancemap.app.persistence.repository.UserRepository;
import com.myfinancemap.app.service.interfaces.ProfileService;
import com.myfinancemap.app.service.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Default implementation of User service.
 */
@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProfileService profileService;

    public DefaultUserService(UserRepository userRepository, UserMapper userMapper, ProfileService profileService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.profileService = profileService;
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<MinimalUserDto> getAllUsers() {
        return userMapper.toMinimalUserDtoList(userRepository.findAll());
    }

    /**
     * @inheritDoc
     */
    @Override
    public UserDto getUserById(Long userId) {
        final User user = userRepository.getUserByUserId(userId)
                .orElseThrow(() -> {
                    throw new NoSuchElementException("Felhasználó nem található!");
                });
        return userMapper.toUserDto(user);
    }

    /**
     * @inheritDoc
     */
    @Override
    public MinimalUserDto createUser(CreateUserDto createUserDto) {
        final User user = userMapper.toUser(createUserDto);
        user.setPublicId(UUID.randomUUID().toString());
        // creating a new profile
        user.setProfile(profileService.createProfile());
        userRepository.save(user);
        return userMapper.toUserDto(user);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deleteUser(Long userId) {
        final User user = userRepository.getUserByUserId(userId)
                .orElseThrow(() -> {
                    throw new NoSuchElementException("Felhasználó nem található!");
                });
        // delete user
        userRepository.delete(user);
        //delete profile
        profileService.deleteProfile(user.getProfile().getProfileId());
    }

    @Override
    public UserDto updateUser(Long userId, UpdateUserDto updateUserDto) {
        final User user = userRepository.getUserByUserId(userId)
                .orElseThrow(() -> {
                    throw new NoSuchElementException("Felhasználó nem található!");
                });
        //updating profile
        profileService.updateProfile(user.getProfile().getProfileId(), updateUserDto.getProfile());
        //updating user
        userMapper.modifyUser(updateUserDto, user);
        userRepository.saveAndFlush(user);
        return userMapper.toUserDto(user);
    }
}
