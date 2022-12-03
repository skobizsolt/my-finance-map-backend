package com.myfinancemap.app.service;

import com.myfinancemap.app.dto.location.CreateUpdateLocationDto;
import com.myfinancemap.app.dto.location.LocationDto;
import com.myfinancemap.app.exception.ServiceExpection;
import com.myfinancemap.app.mapper.LocationMapper;
import com.myfinancemap.app.persistence.domain.Location;
import com.myfinancemap.app.persistence.repository.LocationRepository;
import com.myfinancemap.app.service.interfaces.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.myfinancemap.app.exception.Error.LOCATION_NOT_FOUND;

/**
 * Default implementation of the Location service.
 */
@Service
@AllArgsConstructor
public class DefaultLocationService implements LocationService {

    @Autowired
    private final LocationRepository locationRepository;
    @Autowired
    private final LocationMapper locationMapper;

    /**
     * @inheritDoc
     */
    @Override
    public void createLocation(Location location) {
        locationRepository.save(location);
    }

    /**
     * @inheritDoc
     */
    @Override
    public LocationDto updateLocation(Long locationId, CreateUpdateLocationDto updateLocationDto) {
        final Location location = getLocation(locationId);
        locationMapper.modifyAddress(updateLocationDto, location);
        locationRepository.saveAndFlush(location);
        return locationMapper.toLocationDto(location);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deleteLocation(Long addressId) {
        final Location location = getLocation(addressId);
        locationRepository.delete(location);
    }

    private Location getLocation(final Long locationId) {
        return locationRepository.getLocationByLocationId(locationId)
                .orElseThrow(() -> {
                    throw new ServiceExpection(LOCATION_NOT_FOUND);
                });
    }
}
