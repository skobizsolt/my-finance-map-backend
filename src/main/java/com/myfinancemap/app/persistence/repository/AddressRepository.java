package com.myfinancemap.app.persistence.repository;

import com.myfinancemap.app.persistence.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> getAddressByAddressId(Long addressId);
}
