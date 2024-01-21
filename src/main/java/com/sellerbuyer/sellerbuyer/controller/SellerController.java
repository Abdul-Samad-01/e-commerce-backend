package com.sellerbuyer.sellerbuyer.controller;

import com.sellerbuyer.sellerbuyer.payloads.responseDto.TopProductsResponseDto;
import com.sellerbuyer.sellerbuyer.service.ProductService;
import com.sellerbuyer.sellerbuyer.service.PurchaseProductInformationService;
import com.sellerbuyer.sellerbuyer.payloads.dto.PurchaseProductInformationDto;
import com.sellerbuyer.sellerbuyer.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    private final ProductService productService;
    private final PurchaseProductInformationService purchaseProductInformationService;

    @Autowired
    public SellerController(ProductService productService,
                            PurchaseProductInformationService purchaseProductInformationService) {
        this.productService = productService;
        this.purchaseProductInformationService = purchaseProductInformationService;
    }

    @Operation(summary = "Get a page of top products sold by the seller")
    @GetMapping("/top-products")
    public ResponseEntity<Page<TopProductsResponseDto>> getTopProducts(@RequestParam(defaultValue = "0") int pageNo,
                                                                       @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.topProductsBySeller(PaginationUtil.getPageable(pageNo,size,null)));
    }

    @Operation(summary = "Get a page of purchases made from the seller")
    @GetMapping("/purchases")
    public ResponseEntity<Page<PurchaseProductInformationDto>> getPurchases(@RequestParam(defaultValue = "0") int pageNo,
                                                                            @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(purchaseProductInformationService.getPurchasesBySeller(
                PaginationUtil.getPageable(pageNo,size,null)));
    }
}
