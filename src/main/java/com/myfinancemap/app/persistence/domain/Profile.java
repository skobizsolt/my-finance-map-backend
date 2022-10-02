package com.myfinancemap.app.persistence.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "profile")
@Getter
@Setter
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;
    private String firstName;
    private String lastName;
    private LocalDateTime birthDate;
    @OneToOne
    @JoinColumn(name = "addressId", referencedColumnName = "addressId")
    private Address homeAddress;
}
