package com.myfinancemap.app.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ShopCoordinateResponse {
    private Long shopId;
    private String coordinateX;
    private String coordinateY;
}
