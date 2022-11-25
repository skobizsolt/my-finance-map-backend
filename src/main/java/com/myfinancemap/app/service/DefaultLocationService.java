package com.myfinancemap.app.service;

import com.myfinancemap.app.dto.location.CreateUpdateLocationDto;
import com.myfinancemap.app.dto.location.LocationDto;
import com.myfinancemap.app.mapper.LocationMapper;
import com.myfinancemap.app.persistence.domain.Location;
import com.myfinancemap.app.persistence.repository.LocationRepository;
import com.myfinancemap.app.service.interfaces.LocationService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * Default implementation of the Location service.
 */
@Service
public class DefaultLocationService implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public DefaultLocationService(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

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
        final Location location = locationRepository.getLocationByLocationId(locationId)
                .orElseThrow(() -> {
                    throw new NoSuchElementException("A keresett hely nem található!");
                });
        locationMapper.modifyAddress(updateLocationDto, location);
        locationRepository.saveAndFlush(location);
        return locationMapper.toLocationDto(location);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deleteLocation(Long addressId) {
        final Location location = locationRepository.getLocationByLocationId(addressId)
                .orElseThrow(() -> {
                    throw new NoSuchElementException("A keresett hely nem található!");
                });
        locationRepository.delete(location);
    }
}
