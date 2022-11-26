package com.myfinancemap.app.mapper;

import com.myfinancemap.app.dto.AuthRoles;
import com.myfinancemap.app.dto.user.CreateUserDto;
import com.myfinancemap.app.dto.user.MinimalUserDto;
import com.myfinancemap.app.dto.user.UpdateUserDto;
import com.myfinancemap.app.dto.user.UserDto;
import com.myfinancemap.app.persistence.domain.User;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Mapper(uses = ProfileMapper.class, imports = LocalDate.class)
@Component
public interface UserMapper {
    @Named("userDtoMapper")
    UserDto toUserDto(User user);

    @Named("minimalUserDtoMapper")
    MinimalUserDto toMinimalUserDto(User user);

    @IterableMapping(qualifiedByName = "minimalUserDtoMapper")
    List<MinimalUserDto> toMinimalUserDtoList(List<User> users);

    @Mapping(target = "registrationDate", expression = "java(LocalDate.now())")
    User toUser(CreateUserDto dto);

    void modifyUser(UpdateUserDto updateUserDto, @MappingTarget User user);

    @AfterMapping
    default void setRole(CreateUserDto createUserDto, @MappingTarget User user) {
        user.setRole(AuthRoles.USER);
    }
}
