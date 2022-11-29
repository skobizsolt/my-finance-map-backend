package com.myfinancemap.app.service;

import com.myfinancemap.app.persistence.domain.BusinessCategory;
import com.myfinancemap.app.persistence.repository.BusinessCategoryRepository;
import com.myfinancemap.app.service.interfaces.BusinessCategoryService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * Default implementation of Business category service.
 */
@Service
public class DefaultBusinessCategoryService implements BusinessCategoryService {
    private final BusinessCategoryRepository businessCategoryRepository;

    public DefaultBusinessCategoryService(BusinessCategoryRepository businessCategoryRepository) {
        this.businessCategoryRepository = businessCategoryRepository;
    }

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
