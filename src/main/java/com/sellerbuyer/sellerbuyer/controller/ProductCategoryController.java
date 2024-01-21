package com.sellerbuyer.sellerbuyer.controller;

import com.sellerbuyer.sellerbuyer.payloads.dto.ProductCategoryDto;
import com.sellerbuyer.sellerbuyer.service.ProductCategoryService;
import com.sellerbuyer.sellerbuyer.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller/product-categories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @Operation(summary = "Create a new product category")
    @PostMapping
    public ResponseEntity<ProductCategoryDto> createCategory(@RequestBody ProductCategoryDto productCategoryDto) {
        return ResponseEntity.ok(productCategoryService.createCategory(productCategoryDto));
    }

    @Operation(summary = "Get all product categories with pagination")
    @GetMapping
    public ResponseEntity<Page<ProductCategoryDto>> getAllCategories(@RequestParam(defaultValue = "0") int pageNo,
                                                                     @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productCategoryService.getAllCategories(PaginationUtil.getPageable(pageNo, size, null)));
    }

    @Operation(summary = "Get a product category by its id")
    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryDto> getCategoryById(@PathVariable long id) {
        return ResponseEntity.ok(productCategoryService.getCategoryById(id));
    }

    @Operation(summary = "Update an existing product category")
    @PutMapping("/{id}")
    public ResponseEntity<ProductCategoryDto> updateCategory(@PathVariable long id,
                                                             @RequestBody ProductCategoryDto productCategoryDto) {
        return ResponseEntity.ok(productCategoryService.updateCategory(id, productCategoryDto));
    }

    @Operation(summary = "Delete a product category by its id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable long id) {
        productCategoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Activate a product category")
    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateCategory(@PathVariable long id) {
        productCategoryService.activateCategory(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Deactivate a product category")
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateCategory(@PathVariable long id) {
        productCategoryService.deactivateCategory(id);
        return ResponseEntity.ok().build();
    }
}