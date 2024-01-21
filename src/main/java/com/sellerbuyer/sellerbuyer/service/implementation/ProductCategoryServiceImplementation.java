package com.sellerbuyer.sellerbuyer.service.implementation;

import com.sellerbuyer.sellerbuyer.entity.ProductCategory;
import com.sellerbuyer.sellerbuyer.entity.User;
import com.sellerbuyer.sellerbuyer.exception.CustomException;
import com.sellerbuyer.sellerbuyer.payloads.dto.ProductCategoryDto;
import com.sellerbuyer.sellerbuyer.repository.ProductCategoryRepository;
import com.sellerbuyer.sellerbuyer.repository.UserRepository;
import com.sellerbuyer.sellerbuyer.service.ProductCategoryService;
import com.sellerbuyer.sellerbuyer.util.DtoConverter;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.sellerbuyer.sellerbuyer.util.Constants.*;

@Service
public class ProductCategoryServiceImplementation implements ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;
    private final DtoConverter dtoConverter;
    private final UserRepository userRepository;
    private final Logger logger;

    @Autowired
    public ProductCategoryServiceImplementation(ProductCategoryRepository productCategoryRepository,
                                                DtoConverter dtoConverter, UserRepository userRepository,
                                                Logger logger) {
        this.productCategoryRepository = productCategoryRepository;
        this.dtoConverter = dtoConverter;
        this.userRepository = userRepository;
        this.logger = logger;
    }

    @Override
    public ProductCategoryDto createCategory(ProductCategoryDto productCategoryDto) {
        ProductCategory productCategory = dtoConverter.convertToEntity(productCategoryDto, ProductCategory.class);
        productCategory.setCreatedBy(getCurrentSeller());
        try {
            productCategoryRepository.save(productCategory);
        } catch (Exception e) {
            logger.error(ERROR_WHILE_SAVING_PRODUCT_CATEGORY + e.getMessage());
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_WHILE_SAVING_PRODUCT_CATEGORY);
        }
        return dtoConverter.convertToDto(productCategory, ProductCategoryDto.class);
    }

    @Override
    public Page<ProductCategoryDto> getAllCategories(Pageable pageable) {
        Page<ProductCategory> productCategoryPage = productCategoryRepository.findAll(pageable);
        return productCategoryPage.map((productCategory) -> this.dtoConverter.convertToEntity(productCategory,
                ProductCategoryDto.class));
    }

    @Override
    public ProductCategoryDto getCategoryById(long id) {
        ProductCategory productCategory = productCategoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, CATEGORY_NOT_FOUND));
        return dtoConverter.convertToDto(productCategory, ProductCategoryDto.class);
    }

    @Override
    public ProductCategoryDto updateCategory(long id, ProductCategoryDto productCategoryDto) {
        ProductCategory productCategory = getProductCategoryByIdAndValidateSeller(id);
        productCategory.setCategoryName(productCategoryDto.getCategoryName());
        productCategory.setIsActive(productCategoryDto.getIsActive());
        try {
            productCategoryRepository.save(productCategory);
        } catch (Exception e) {
            logger.error(ERROR_WHILE_SAVING_PRODUCT_CATEGORY + e.getMessage());
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_WHILE_SAVING_PRODUCT_CATEGORY);
        }
        return dtoConverter.convertToDto(productCategory, ProductCategoryDto.class);
    }

    @Override
    public void deleteCategory(long id) {
        ProductCategory productCategory = getProductCategoryByIdAndValidateSeller(id);
        productCategoryRepository.deleteById(id);
        logger.info("Deleted product category with id " + id);
    }

    @Override
    public void activateCategory(long id) {
        ProductCategory productCategory = getProductCategoryByIdAndValidateSeller(id);
        productCategory.setIsActive(true);
        try {
            productCategoryRepository.save(productCategory);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_WHILE_SAVING_PRODUCT_CATEGORY);
        }
    }

    @Override
    public void deactivateCategory(long id) {
        ProductCategory productCategory = getProductCategoryByIdAndValidateSeller(id);
        productCategory.setIsActive(false);
        try {
            productCategoryRepository.save(productCategory);
        } catch (Exception e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_WHILE_SAVING_PRODUCT_CATEGORY);
        }
    }

    // Helper method to fetch the currently authenticated seller
    private User getCurrentSeller() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return userRepository.findByUserName(userName);
    }

    private ProductCategory getProductCategoryByIdAndValidateSeller(long id) {
        ProductCategory productCategory = productCategoryRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, CATEGORY_NOT_FOUND));
        User currentSeller = getCurrentSeller();
        if (!productCategory.getCreatedBy().getUserName().equals(currentSeller.getUserName())) {
            throw new CustomException(HttpStatus.FORBIDDEN, NOT_AUTHORISED_FOR_CATEGORY);
        }
        return productCategory;
    }
}

