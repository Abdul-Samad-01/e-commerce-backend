package com.sellerbuyer.sellerbuyer.service;

import com.razorpay.RazorpayException;
import com.sellerbuyer.sellerbuyer.enumClasses.TransactionStatus;
import com.sellerbuyer.sellerbuyer.enumClasses.TypeOfTransaction;
import com.sellerbuyer.sellerbuyer.payloads.dto.PurchaseTransactionDto;
import com.sellerbuyer.sellerbuyer.payloads.requestDto.PurchaseRequestDto;
import com.sellerbuyer.sellerbuyer.payloads.requestDto.RefundRequestDto;
import com.sellerbuyer.sellerbuyer.payloads.responseDto.RefundResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PurchaseTransactionService {

    PurchaseTransactionDto createPurchase(
            PurchaseTransactionDto purchaseTransactionDto
    ) throws RazorpayException;

    PurchaseTransactionDto updateTransaction(
            PurchaseRequestDto purchaseRequestDto
    ) throws RazorpayException;

    Page<PurchaseTransactionDto> findPurchases(Pageable pageable,
                                               TransactionStatus status,
                                               TypeOfTransaction type);

    RefundResponseDto refundThePurchase(
            RefundRequestDto refundRequestDto) throws RazorpayException;

}
