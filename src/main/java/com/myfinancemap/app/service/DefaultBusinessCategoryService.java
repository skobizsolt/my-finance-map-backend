package com.myfinancemap.app.service;

import com.myfinancemap.app.persistence.domain.BusinessCategory;
import com.myfinancemap.app.persistence.repository.BusinessCategoryRepository;
import com.myfinancemap.app.service.interfaces.BusinessCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * Default implementation of Business category service.
 */
@Service
@AllArgsConstructor
public class DefaultBusinessCategoryService implements BusinessCategoryService {
    @Autowired
    private final BusinessCategoryRepository businessCategoryRepository;

    /**
     * @inheritDoc
     */
    @Override
    public BusinessCategory getBusinessEntityById(final Long categoryId) {
        return businessCategoryRepository.getBusinessCategoryByCategoryId(categoryId)
                .orElseThrow(() -> {
                    throw new NoSuchElementException("Kategória nem található!");
                });
    }
}
