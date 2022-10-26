package com.myfinancemap.app.dto;

import lombok.Data;

@Data
public class MinimalUserDto {
    private Long userId;
    private String publicId;
    private String username;
    private String email;
}
