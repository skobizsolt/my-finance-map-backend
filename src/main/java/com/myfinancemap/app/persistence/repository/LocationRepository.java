package com.myfinancemap.app.persistence.repository;

import com.myfinancemap.app.persistence.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> getLocationByLocationId(Long locationId);
}
