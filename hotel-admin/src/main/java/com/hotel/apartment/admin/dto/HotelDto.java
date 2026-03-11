package com.hotel.apartment.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class HotelDto {

    @NotBlank(message = "酒店名称不能为空")
    private String name;

    @NotBlank(message = "地址不能为空")
    private String address;

    private String phone;

    private String intro;

    private String coverImage;

    private String facilities;

    private String latitude;

    private String longitude;

    private BigDecimal minPrice;

    private Integer status;

    private Integer sortOrder;
}
