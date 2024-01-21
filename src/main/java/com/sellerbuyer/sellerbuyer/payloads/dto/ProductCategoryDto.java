package com.sellerbuyer.sellerbuyer.payloads.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.sellerbuyer.sellerbuyer.payloads.requestDto.UserRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductCategoryDto {

    @Schema(hidden = true)
    private long categoryId;
    private String categoryName;
    private Boolean isActive;
    private UserRequestDto createdBy;
}
