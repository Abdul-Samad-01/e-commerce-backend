package com.sellerbuyer.sellerbuyer.controller;

import com.razorpay.RazorpayException;
import com.sellerbuyer.sellerbuyer.payloads.dto.PurchaseTransactionDto;
import com.sellerbuyer.sellerbuyer.payloads.requestDto.PurchaseRequestDto;
import com.sellerbuyer.sellerbuyer.service.PurchaseTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buyer/purchase-transaction")
public class PurchaseTransactionController {

    private final PurchaseTransactionService
            purchaseTransactionService;

    @Autowired
    public PurchaseTransactionController(PurchaseTransactionService purchaseTransactionService) {
        this.purchaseTransactionService = purchaseTransactionService;
    }

    @Operation(summary = "Create a new purchase transaction")
    @PostMapping
    public ResponseEntity<PurchaseTransactionDto> createTransaction(
            @RequestBody PurchaseTransactionDto purchaseTransactionDto
    ) throws RazorpayException {
        return new ResponseEntity<>(this.purchaseTransactionService.createPurchase(purchaseTransactionDto), HttpStatus.OK);
    }

    @Operation(summary = "Update an existing purchase transaction")
    @PutMapping
    public ResponseEntity<PurchaseTransactionDto> updateTransaction(
            @RequestBody PurchaseRequestDto purchaseRequestDto
    ) throws RazorpayException {
        return new ResponseEntity<>(this.purchaseTransactionService.updateTransaction(purchaseRequestDto), HttpStatus.OK);
    }
}
