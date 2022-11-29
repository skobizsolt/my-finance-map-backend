package com.myfinancemap.app.dto.location;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class LocationDto extends CreateUpdateLocationDto {
    private Long locationId;
}
