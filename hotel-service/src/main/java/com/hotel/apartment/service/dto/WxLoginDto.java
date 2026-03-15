package com.hotel.apartment.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WxLoginDto {

    @NotBlank(message = "code不能为空")
    private String code;

    private String encryptedData;

    private String iv;
}
