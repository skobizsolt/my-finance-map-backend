package com.myfinancemap.app.persistence.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
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
    @Size(min = 8, message = "Minimum of 8 characters required")
    private String password;
    private Boolean isAdmin;
    @NotNull
    @PastOrPresent
    private LocalDateTime registrationDate;
    @OneToOne
    @JoinColumn(name = "profileId", referencedColumnName = "profileId")
    private Profile profile;
}
