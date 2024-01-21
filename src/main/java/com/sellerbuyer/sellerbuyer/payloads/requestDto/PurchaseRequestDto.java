package com.sellerbuyer.sellerbuyer.payloads.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequestDto {

    private String orderId;

    private String transactionId;


}
