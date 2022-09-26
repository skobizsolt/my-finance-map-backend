package com.myfinancemap.app.persistence.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "profile")
@Getter
@Setter
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long profileId;
    private String firstName;
    private String lastName;
}
