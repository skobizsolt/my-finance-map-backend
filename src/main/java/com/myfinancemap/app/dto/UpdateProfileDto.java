package com.myfinancemap.app.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateProfileDto {
    private String firstName;
    private String lastName;
    private LocalDateTime birthDate;
}
