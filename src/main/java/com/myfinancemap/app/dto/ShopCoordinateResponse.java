package com.myfinancemap.app.dto;

import com.myfinancemap.app.dto.location.LocationDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ShopCoordinateResponse {
    private Long shopId;
    private LocationDto location;
}
