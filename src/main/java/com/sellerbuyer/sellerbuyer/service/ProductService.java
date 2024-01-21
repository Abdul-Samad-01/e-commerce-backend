package com.sellerbuyer.sellerbuyer.service;


import com.sellerbuyer.sellerbuyer.payloads.dto.ProductDto;
import com.sellerbuyer.sellerbuyer.payloads.responseDto.TopProductsResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {

    Page<ProductDto> getProducts(Pageable pageable);

    ProductDto findProductById(UUID productId);

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(UUID productId, ProductDto productDto);

    void deactivateProduct(UUID productId);

    void activateProduct(UUID productId);

    void markProductAsOutOfStock(UUID productId);

    Page<ProductDto> getBuyerProductList(Pageable pageable);

    Page<ProductDto> searchProduct(String title, Pageable pageable);

    Page<TopProductsResponseDto> topProductsBySeller(Pageable pageable);

    Page<ProductDto> getProductByCategoryId(long id, Pageable pageable);
}
