package com.sellerbuyer.sellerbuyer.payloads.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundResponseDto {

    private String transactionId;

    private String refundId;

    private double amountRefunded;

    private String message;
}
