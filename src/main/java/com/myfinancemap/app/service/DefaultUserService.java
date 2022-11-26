package com.myfinancemap.app.service;

import com.myfinancemap.app.dto.user.CreateUserDto;
import com.myfinancemap.app.dto.user.MinimalUserDto;
import com.myfinancemap.app.dto.user.UpdateUserDto;
import com.myfinancemap.app.dto.user.UserDto;
import com.myfinancemap.app.event.RegistrationEvent;
import com.myfinancemap.app.mapper.UserMapper;
import com.myfinancemap.app.persistence.domain.User;
import com.myfinancemap.app.persistence.repository.UserRepository;
import com.myfinancemap.app.service.interfaces.ProfileService;
import com.myfinancemap.app.service.interfaces.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Default implementation of User service.
 */
@Service
public class DefaultUserService implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher publisher;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProfileService profileService;

    public DefaultUserService(PasswordEncoder passwordEncoder, ApplicationEventPublisher publisher, UserRepository userRepository, UserMapper userMapper, ProfileService profileService) {
        this.passwordEncoder = passwordEncoder;
        this.publisher = publisher;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.profileService = profileService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MinimalUserDto> getAllUsers() {
        return userMapper.toMinimalUserDtoList(userRepository.findAll());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDto getUserById(final Long userId) {
        final User user = getUserEntityById(userId);
        return userMapper.toUserDto(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String registerUser(final CreateUserDto createUserDto, final String requestUrl) {
        final User user = userMapper.toUser(createUserDto);
        user.setPublicId(UUID.randomUUID().toString());
        // creating a new profile
        user.setProfile(profileService.createProfile());
        // register
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        userRepository.save(user);
        // send verification email
        publisher.publishEvent(
                new RegistrationEvent(
                        user,
                        requestUrl));
        return "Account needs to be verified at at url: " + requestUrl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void verifyUser(final User user) {
        user.setEnabled(true);
        userRepository.save(user);
    }
}
