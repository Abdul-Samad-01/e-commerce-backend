package com.sellerbuyer.sellerbuyer.payloads.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sellerbuyer.sellerbuyer.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {

    @Schema(hidden = true)
    private long uuid;
    private String userName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;
    private String phoneNumber;
    private String countryCode;
    private boolean isSuperUser;
    private boolean isStaff;
    private boolean isActive;
    private Date lastLogin;
    private Date lastLogout;
    private Role role;
}
