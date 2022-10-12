package com.myfinancemap.app.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MinimalUserDto {
    private Long userId;
    private Long publicId;
    private String username;
    private String email;
    private LocalDateTime registrationDate;
}
