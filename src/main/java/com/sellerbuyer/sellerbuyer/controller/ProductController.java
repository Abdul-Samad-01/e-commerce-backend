package com.sellerbuyer.sellerbuyer.controller;

import com.sellerbuyer.sellerbuyer.payloads.dto.ProductDto;
import com.sellerbuyer.sellerbuyer.service.ProductService;
import com.sellerbuyer.sellerbuyer.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/seller/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get all products with pagination")
    @GetMapping
    public ResponseEntity<Page<ProductDto>> getProducts(@RequestParam(defaultValue = "0") int pageNo,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam(name = "sort", defaultValue = "title")
                                                            List<String> sortByProperties
    ) {
        return ResponseEntity.ok(productService.getProducts(PaginationUtil.getPageable(pageNo, size, sortByProperties)));
    }

    @Operation(summary = "Get a product by its id")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable UUID id) {
        ProductDto ProductDto = productService.findProductById(id);
        return ResponseEntity.ok(ProductDto);
    }

    @Operation(summary = "Create a new product")
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto ProductDto) {
        ProductDto createdProduct = productService.createProduct(ProductDto);
        return ResponseEntity.ok(createdProduct);
    }

    @Operation(summary = "Update an existing product")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable UUID id, @RequestBody ProductDto productDetails) {
        ProductDto updatedProduct = productService.updateProduct(id, productDetails);
        return ResponseEntity.ok(updatedProduct);
    }

    @Operation(summary = "Deactivate a product by its id")
    @PatchMapping("/{productId}/deactivate")
    public ResponseEntity<Void> deactivateProduct(@PathVariable UUID productId) {
        productService.deactivateProduct(productId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Activate a product by its id")
    @PatchMapping("/{productId}/activate")
    public ResponseEntity<Void> activateProduct(@PathVariable UUID productId) {
        productService.activateProduct(productId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Mark a product out of stock by its id")
    @PatchMapping("/{productId}/out-of-stock")
    public ResponseEntity<Void> markProductAsOutOfStock(@PathVariable UUID productId) {
        productService.markProductAsOutOfStock(productId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get all products by its category id with pagination")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<ProductDto>> getProductByCategory(@PathVariable long categoryId,
                                                                 @RequestParam(defaultValue = "0") int pageNo,
                                                                 @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(productService.getProductByCategoryId(categoryId,PaginationUtil.getPageable(pageNo,size,
                null)));
    }
}
