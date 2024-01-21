package com.sellerbuyer.sellerbuyer.payloads.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sellerbuyer.sellerbuyer.enumClasses.PaymentMethod;
import com.sellerbuyer.sellerbuyer.enumClasses.TransactionStatus;
import com.sellerbuyer.sellerbuyer.enumClasses.TypeOfTransaction;
import com.sellerbuyer.sellerbuyer.payloads.requestDto.UserRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseTransactionDto {

    @Schema(hidden = true)
    private Long tId;

    private String originalTransactionId;

    private double amount;

    private TypeOfTransaction transactionType;

    private TransactionStatus transactionStatus;

    private PaymentMethod paymentMethod;

    private String orderId;

    @JsonManagedReference
    private List<PurchaseProductInformationDto>
            purchaseProductInformationList;

    private UserRequestDto purchasedById;
}
