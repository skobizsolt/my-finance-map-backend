package com.myfinancemap.app.dto.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class CreateUserDto {
    private String username;
    private String email;
    private String password;
}
