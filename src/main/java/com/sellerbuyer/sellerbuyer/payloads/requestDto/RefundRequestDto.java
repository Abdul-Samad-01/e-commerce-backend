package com.sellerbuyer.sellerbuyer.payloads.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundRequestDto {

    private Long tId;

    private String originalTransactionId;

    private String orderId;
}
