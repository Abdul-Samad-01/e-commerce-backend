package com.sellerbuyer.sellerbuyer.service.implementation;


import com.sellerbuyer.sellerbuyer.entity.Product;
import com.sellerbuyer.sellerbuyer.entity.TopProduct;
import com.sellerbuyer.sellerbuyer.entity.User;
import com.sellerbuyer.sellerbuyer.exception.CustomException;
import com.sellerbuyer.sellerbuyer.payloads.dto.ProductDto;
import com.sellerbuyer.sellerbuyer.payloads.responseDto.TopProductsResponseDto;
import com.sellerbuyer.sellerbuyer.repository.ProductRepository;
import com.sellerbuyer.sellerbuyer.repository.PurchaseProductInformationRepository;
import com.sellerbuyer.sellerbuyer.repository.UserRepository;
import com.sellerbuyer.sellerbuyer.service.ProductService;
import com.sellerbuyer.sellerbuyer.util.DtoConverter;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.sellerbuyer.sellerbuyer.util.Constants.*;

@Service
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepository;
    private final DtoConverter dtoConverter;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PurchaseProductInformationRepository purchaseProductInformationRepository;
    private final Logger logger;

    @Autowired
    public ProductServiceImplementation(ProductRepository productRepository, DtoConverter dtoConverter,
                                        ModelMapper modelMapper, UserRepository userRepository,
                                        PurchaseProductInformationRepository purchaseProductInformationRepository,
                                        Logger logger) {
        this.productRepository = productRepository;
        this.dtoConverter = dtoConverter;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.purchaseProductInformationRepository = purchaseProductInformationRepository;
        this.logger = logger;
    }

    @Override
    @Transactional
    public Page<ProductDto> getProducts(Pageable pageable) {
        Page<Product> page;
        try {
            page = productRepository.findByCreatedBy(getCurrentSeller(), pageable);
        } catch (Exception e) {
            logger.error(ERROR_FETCHING_PRODUCTS + e.getMessage());
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        if (page.isEmpty()) {
            logger.warn(PRODUCTS_NOT_FOUND);
            throw new CustomException(HttpStatus.NOT_FOUND, PRODUCTS_NOT_FOUND);
        }
        return page.map((product) -> this.dtoConverter.convertToEntity(product, ProductDto.class));
    }

    @Override
    @Transactional
    public ProductDto findProductById(UUID productId) {
        Product product = getProductByIdAndValidateSeller(productId);
        return dtoConverter.convertToDto(product, ProductDto.class);
    }

    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        productDto.setProductId(UUID.randomUUID());
        Product product = dtoConverter.convertToEntity(productDto, Product.class);
        product.setCreatedBy(getCurrentSeller());
        try {
            productRepository.save(product);
        } catch (Exception e) {
            logger.error(ERROR_WHILE_SAVING_PRODUCT + e.getMessage());
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_WHILE_SAVING_PRODUCT);
        }
        return dtoConverter.convertToDto(product, ProductDto.class);
    }

    @Override
    @Transactional
    public ProductDto updateProduct(UUID productId, ProductDto productDto) {
        Product existingProduct = getProductByIdAndValidateSeller(productId);
        modelMapper.map(productDto, existingProduct);
        existingProduct.setProductId(productId);
        try {
            productRepository.save(existingProduct);
        } catch (Exception e) {
            logger.error(ERROR_WHILE_SAVING_PRODUCT + e.getMessage());
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_WHILE_SAVING_PRODUCT);
        }
        return dtoConverter.convertToDto(existingProduct, ProductDto.class);
    }

    @Override
    @Transactional
    public void deactivateProduct(UUID productId) {
        Product product = getProductByIdAndValidateSeller(productId);
        product.setActive(false);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void activateProduct(UUID productId) {
        Product product = getProductByIdAndValidateSeller(productId);
        product.setActive(true);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void markProductAsOutOfStock(UUID productId) {
        Product product = getProductByIdAndValidateSeller(productId);
        product.setStockCount(0);
        productRepository.save(product);
    }

    @Override
    public Page<ProductDto> getBuyerProductList(Pageable pageable) {
        Page<Product> allProducts = this.productRepository.findAll(pageable);
        return allProducts.map((product) -> this.dtoConverter.convertToDto(product, ProductDto.class));
    }

    @Override
    public Page<ProductDto> searchProduct(String title,
                                          Pageable pageable) {
        Page<Product> searchedProduct =
                this.productRepository.findByTitleContaining(title,
                        pageable);
        return searchedProduct.map((product) -> this.dtoConverter.convertToEntity(product, ProductDto.class));
    }

    @Override
    public Page<TopProductsResponseDto> topProductsBySeller(Pageable pageable) {
        Page<TopProduct> topProductPage;
        try {
            topProductPage = purchaseProductInformationRepository.findTopProductsBySeller(getCurrentSeller().getUuid(), pageable);
        } catch (Exception e) {
            logger.error(ERROR_FETCHING_PRODUCTS + e.getMessage());
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return topProductPage.map((topProduct) -> this.dtoConverter.convertToDto(topProduct, TopProductsResponseDto.class));
    }

    @Override
    public Page<ProductDto> getProductByCategoryId(long id, Pageable pageable) {
        Page<Product> productPage;
        try {
            productPage = productRepository.findByProductCategory_CategoryId(id, pageable);
        } catch (Exception e) {
            logger.error(ERROR_FETCHING_PRODUCTS + e.getMessage());
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return productPage.map((product) -> this.dtoConverter.convertToDto(product, ProductDto.class));
    }

    // Helper method to fetch the currently authenticated seller
    private User getCurrentSeller() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        return userRepository.findByUserName(userName);
    }

    private Product getProductByIdAndValidateSeller(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, PRODUCT_NOT_FOUND));
        User currentSeller = getCurrentSeller();
        if (!product.getCreatedBy().equals(currentSeller)) {
            throw new CustomException(HttpStatus.FORBIDDEN, NO_PERMISSION_FOR_PRODUCT);
        }
        return product;
    }
}