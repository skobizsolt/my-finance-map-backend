package com.myfinancemap.app.persistence.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "location")
@Getter
@Setter
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;
    @Column(precision = 10, scale = 6, name = "longitude")
    @Min(value = -180)
    @Max(value = 180)
    @NotNull
    private BigDecimal coordinateX;
    @Column(precision = 10, scale = 6, name = "latitude")
    @Min(value = -90)
    @Max(value = 90)
    @NotNull
    private BigDecimal coordinateY;
}
