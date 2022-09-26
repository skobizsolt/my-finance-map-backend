package com.myfinancemap.app.persistence.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private Long publicId;
    private String username;
    private String email;
    private String password;
    private Boolean isAdmin;
    private LocalDateTime registrationDate;
    @ManyToOne
    @JoinColumn(name = "profileId")
    private Profile profile;
}
