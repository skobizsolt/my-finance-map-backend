package com.myfinancemap.app.service;

import com.myfinancemap.app.dto.CreateUserDto;
import com.myfinancemap.app.dto.MinimalUserDto;
import com.myfinancemap.app.dto.UserDto;
import com.myfinancemap.app.mapper.UserMapper;
import com.myfinancemap.app.persistence.domain.User;
import com.myfinancemap.app.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public DefaultUserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<MinimalUserDto> getAllUsers() {
        return userMapper.usersToUserMinimalUserDtoList(userRepository.findAll());
    }

    @Override
    public UserDto getUserById(Long userId) {
        final User user = userRepository.getUserByUserId(userId).orElse(null);
        return userMapper.userToUserDto(user);
    }

    @Override
    public MinimalUserDto createUser(CreateUserDto createUserDto) {
        if (createUserDto != null) {
            final User user = userMapper.createUserDtoToUser(createUserDto);
            userRepository.save(user);
            return userMapper.userToUserDto(user);
        }
        return new MinimalUserDto();
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.getUserByUserId(userId).ifPresent(userRepository::delete);
    }
}
