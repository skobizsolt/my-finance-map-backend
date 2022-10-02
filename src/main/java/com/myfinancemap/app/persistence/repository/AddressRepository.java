package com.myfinancemap.app.persistence.repository;

import com.myfinancemap.app.persistence.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
