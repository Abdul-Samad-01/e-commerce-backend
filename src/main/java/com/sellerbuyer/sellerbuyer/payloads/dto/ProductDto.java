package com.sellerbuyer.sellerbuyer.payloads.dto;

import com.sellerbuyer.sellerbuyer.payloads.requestDto.UserRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {

    @Schema(hidden = true)
    private UUID productId;
    private boolean isActive;
    private String title;
    private String description;
    private long stockCount;
    private double price;
    private double sellingPrice;
    private ProductCategoryDto productCategory;
    private UserRequestDto createdBy;
}
