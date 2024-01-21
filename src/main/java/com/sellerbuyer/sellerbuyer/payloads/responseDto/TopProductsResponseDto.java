package com.sellerbuyer.sellerbuyer.payloads.responseDto;

import com.sellerbuyer.sellerbuyer.entity.Product;
import com.sellerbuyer.sellerbuyer.payloads.dto.ProductDto;
import com.sellerbuyer.sellerbuyer.payloads.requestDto.ProductRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TopProductsResponseDto {

    private ProductDto product;
    private long totalQuantity;
}
