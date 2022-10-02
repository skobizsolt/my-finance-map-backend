package com.myfinancemap.app.persistence.repository;

import com.myfinancemap.app.persistence.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
}
