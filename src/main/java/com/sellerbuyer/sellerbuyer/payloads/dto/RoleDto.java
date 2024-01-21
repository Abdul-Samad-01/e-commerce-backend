package com.sellerbuyer.sellerbuyer.payloads.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    @Schema(hidden = true)
    private long id;
    private String role;
}
