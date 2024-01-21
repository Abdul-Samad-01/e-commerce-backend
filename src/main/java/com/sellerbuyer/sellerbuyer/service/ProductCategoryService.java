package com.sellerbuyer.sellerbuyer.service;

import com.sellerbuyer.sellerbuyer.payloads.dto.ProductCategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductCategoryService {
    ProductCategoryDto createCategory(ProductCategoryDto category);
    Page<ProductCategoryDto> getAllCategories(Pageable pageable);
    ProductCategoryDto getCategoryById(long id);
    ProductCategoryDto updateCategory(long id, ProductCategoryDto category);
    void deleteCategory(long id);
    void activateCategory(long id);
    void deactivateCategory(long id);
}
