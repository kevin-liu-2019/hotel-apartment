package com.hotel.apartment.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomDto {

    @NotNull(message = "酒店ID不能为空")
    private Long hotelId;

    @NotBlank(message = "房间名称不能为空")
    private String name;

    @NotNull(message = "价格不能为空")
    private BigDecimal price;

    private Integer area;

    private Integer floor;

    private String orientation;

    private String images;

    private Integer stock;

    private String description;

    private Integer status;
}
