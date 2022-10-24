package com.myfinancemap.app.service;

import com.myfinancemap.app.dto.CreateUserDto;
import com.myfinancemap.app.dto.MinimalUserDto;
import com.myfinancemap.app.dto.UserDto;
import com.myfinancemap.app.mapper.UserMapper;
import com.myfinancemap.app.persistence.domain.User;
import com.myfinancemap.app.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        final User user = userRepository.getUserByUserId(userId).orElse(null);
        return userMapper.toUserDto(user);
    }

    /**
     * @inheritDoc
     */
    @Override
    public MinimalUserDto createUser(CreateUserDto createUserDto) {
        if (createUserDto != null) {
            final User user = userMapper.toUser(createUserDto);
            userRepository.save(user);
            return userMapper.toUserDto(user);
        }
        return new MinimalUserDto();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deleteUser(Long userId) {
        userRepository.getUserByUserId(userId).ifPresent(userRepository::delete);
    }
}
