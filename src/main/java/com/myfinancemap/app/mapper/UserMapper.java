package com.myfinancemap.app.mapper;

import com.myfinancemap.app.dto.CreateUserDto;
import com.myfinancemap.app.dto.MinimalUserDto;
import com.myfinancemap.app.dto.UserDto;
import com.myfinancemap.app.persistence.domain.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(uses = ProfileMapper.class, imports = LocalDateTime.class)
@Component
public interface UserMapper {
    @Named("userDtoMapper")
    UserDto userToUserDto(User user);

    @Named("minimalUserDtoMapper")
    MinimalUserDto userToMinimalUserDto(User user);
    @IterableMapping(qualifiedByName = "minimalUserDtoMapper")
    List<MinimalUserDto> usersToUserMinimalUserDtoList(List<User> users);

    @Mapping(target = "registrationDate", expression = "java(LocalDateTime.now())")
    User createUserDtoToUser(CreateUserDto dto);
}
