package com.myfinancemap.app.persistence.repository;

import com.myfinancemap.app.persistence.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    Optional<Shop> getShopByShopId(Long shopId);
}
