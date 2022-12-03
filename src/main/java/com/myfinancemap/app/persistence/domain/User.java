package com.myfinancemap.app.persistence.domain;

import com.myfinancemap.app.dto.AuthRoles;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String fullName;
    @Column(unique = true)
    @NotNull
    private String publicId;
    @Column(unique = true)
    @NotNull
    private String username;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Column(length = 60)
    @Size(min = 8, message = "Minimum of 8 characters required")
    private String password;
    @Enumerated(EnumType.STRING)
    private AuthRoles role;
    @NotNull
    @PastOrPresent
    private LocalDate registrationDate;
    private boolean enabled = false;
}
