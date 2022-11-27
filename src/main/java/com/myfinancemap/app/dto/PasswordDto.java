package com.myfinancemap.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDto {
    private String email;
    private String oldPassword;
    private String newPassword;
}
