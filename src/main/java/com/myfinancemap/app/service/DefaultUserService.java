package com.myfinancemap.app.service;

import com.myfinancemap.app.dto.CreateUserDto;
import com.myfinancemap.app.dto.MinimalUserDto;
import com.myfinancemap.app.dto.UserDto;
import com.myfinancemap.app.mapper.UserMapper;
import com.myfinancemap.app.persistence.domain.User;
import com.myfinancemap.app.persistence.repository.UserRepository;
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

    public DefaultUserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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
        //TODO: publicId generation problem
        final User user = userMapper.toUser(createUserDto);
        user.setPublicId(UUID.randomUUID().toString());
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
        userRepository.delete(user);
    }
}
