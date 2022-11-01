package com.myfinancemap.app.dto.user;

import lombok.Data;

@Data
public class MinimalUserDto {
    private Long userId;
    private String publicId;
    private String username;
    private String email;
}
