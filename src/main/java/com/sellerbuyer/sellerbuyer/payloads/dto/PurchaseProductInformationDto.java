package com.sellerbuyer.sellerbuyer.payloads.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sellerbuyer.sellerbuyer.payloads.requestDto.ProductRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseProductInformationDto {

    @Schema(hidden = true)
    private Long id;

    private ProductRequestDto product;

    private String title;

    private String description;

    private double price;

    private long quantity;

    @JsonBackReference
    private PurchaseTransactionDto purchaseTransaction;
}
