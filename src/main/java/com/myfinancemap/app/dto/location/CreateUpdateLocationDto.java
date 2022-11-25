package com.myfinancemap.app.dto.location;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
public class CreateUpdateLocationDto {
    private BigDecimal coordinateX;
    private BigDecimal coordinateY;
}
