package com.sellerbuyer.sellerbuyer.controller;

import com.razorpay.RazorpayException;
import com.sellerbuyer.sellerbuyer.enumClasses.TransactionStatus;
import com.sellerbuyer.sellerbuyer.enumClasses.TypeOfTransaction;
import com.sellerbuyer.sellerbuyer.payloads.dto.ProductDto;
import com.sellerbuyer.sellerbuyer.payloads.dto.PurchaseTransactionDto;
import com.sellerbuyer.sellerbuyer.payloads.requestDto.RefundRequestDto;
import com.sellerbuyer.sellerbuyer.payloads.responseDto.RefundResponseDto;
import com.sellerbuyer.sellerbuyer.service.ProductService;
import com.sellerbuyer.sellerbuyer.service.PurchaseTransactionService;
import com.sellerbuyer.sellerbuyer.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buyer")
public class BuyerController {

    private final ProductService productService;

    private final PurchaseTransactionService
            purchaseTransactionService;

    @Autowired
    public BuyerController(ProductService productService,
                           PurchaseTransactionService purchaseTransactionService) {
        this.productService = productService;
        this.purchaseTransactionService = purchaseTransactionService;
    }

    @Operation(summary = "Get all products available for buyers")
    @GetMapping("/products")
    public ResponseEntity<Page<ProductDto>> getAllProducts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "5")
            int pageSize,
            @RequestParam(name = "sort", defaultValue = "productId")
            List<String> sortByProperties
    ) {
        return ResponseEntity.ok(this.productService.getBuyerProductList(PaginationUtil.getPageable(page, pageSize, sortByProperties)));
    }

    @Operation(summary = "Search for products by title")
    @GetMapping("/products/search")
    public ResponseEntity<Page<ProductDto>> searchProduct(
            @RequestParam("q") String title,
            @RequestParam(name = "page", defaultValue = "0",
                    required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "5",
                    required = false) int pageSize,
            @RequestParam(name = "sort", defaultValue = "productId",
                    required = false) List<String> sortByProperties
    ) {

        return ResponseEntity.ok(this.productService.searchProduct(
                title, PaginationUtil.getPageable(page, pageSize,
                        sortByProperties
                )
        ));

    }

    @Operation(summary = "Find purchase transactions for a buyer")
    @GetMapping("/purchases")
    public ResponseEntity<Page<PurchaseTransactionDto>> findPurchases(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "5")
            int pageSize,
            @RequestParam(name = "sort", defaultValue = "tId")
            List<String> sortByProperties,
            @RequestParam(name = "type", defaultValue = "PURCHASE")
            String type,
            @RequestParam(name = "status", defaultValue = "COMPLETED")
            String status
    ) {
        return ResponseEntity.ok(this.purchaseTransactionService.findPurchases(
                PaginationUtil.getPageable(page, pageSize,
                        sortByProperties),
                TransactionStatus.valueOf(status),
                TypeOfTransaction.valueOf(type)
        ));
    }

    @Operation(summary = "Initiate a refund for a purchase")
    @PostMapping("/refund")
    public ResponseEntity<RefundResponseDto> initiateRefund(@RequestBody RefundRequestDto refundRequestDto) throws RazorpayException {
        return ResponseEntity.ok(this.purchaseTransactionService.refundThePurchase(refundRequestDto));
    }
}
