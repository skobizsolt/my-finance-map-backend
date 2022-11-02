package com.myfinancemap.app.service.interfaces;

import com.myfinancemap.app.persistence.domain.BusinessCategory;

public interface BusinessCategoryService {
    /**
     * Method for receiving the selected business category.
     *
     * @param categoryId id of the category
     * @return the Business category as a dto.
     */
    BusinessCategory getBusinessEntityById(final Long categoryId);
}
