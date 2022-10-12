package com.myfinancemap.app.persistence.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(unique = true, columnDefinition = "integer auto_increment")
    private Long publicId;
    @Column(unique = true)
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private Boolean isAdmin;
    private LocalDateTime registrationDate;
    @OneToOne
    @JoinColumn(name = "profileId", referencedColumnName = "profileId")
    private Profile profile;
}
