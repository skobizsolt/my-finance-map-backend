package com.myfinancemap.app.dto.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class MinimalUserDto {
    private Long userId;
    private String publicId;
    private String username;
    private String email;
}
