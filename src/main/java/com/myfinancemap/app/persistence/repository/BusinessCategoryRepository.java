package com.myfinancemap.app.persistence.repository;

import com.myfinancemap.app.persistence.domain.BusinessCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessCategoryRepository extends JpaRepository<BusinessCategory, Long> {

    Optional<BusinessCategory> getBusinessCategoryByCategoryId(Long categoryId);
}
