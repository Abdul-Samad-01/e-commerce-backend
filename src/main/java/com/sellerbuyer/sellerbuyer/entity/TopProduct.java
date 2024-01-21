package com.sellerbuyer.sellerbuyer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TopProduct {

    private Product product;
    private long totalQuantity;
}
