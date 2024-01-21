package com.sellerbuyer.sellerbuyer.payloads.responseDto;

import com.sellerbuyer.sellerbuyer.entity.Product;
import com.sellerbuyer.sellerbuyer.payloads.dto.ProductDto;
import com.sellerbuyer.sellerbuyer.payloads.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryProductsResponseDto {
    private long categoryId;
    private String categoryName;
    private Boolean isActive;
    private List<ProductDto> productList;
}
