package com.myfinancemap.app.service.interfaces;

import com.myfinancemap.app.dto.location.CreateUpdateLocationDto;
import com.myfinancemap.app.dto.location.LocationDto;
import com.myfinancemap.app.persistence.domain.Location;

/**
 * Interface class for shop locations
 */
public interface LocationService {
    /**
     * Method for creating a shop location.
     */
    void createLocation(Location location);

    /**
     * Method for updating the given address.
     *
     * @param locationId        id of the selected address.
     * @param updateLocationDto data we want to modify the address with
     * @return a dto, containing the updated Address.
     */
    LocationDto updateLocation(final Long locationId, final CreateUpdateLocationDto updateLocationDto);

    /**
     * Method for deleting the selected address from the repository.
     *
     * @param locationId id of the address we want to delete.
     */
    void deleteLocation(final Long locationId);
}
