package com.hotel.apartment.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateOrderDto {

    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @NotNull(message = "酒店ID不能为空")
    private Long hotelId;

    @NotNull(message = "房间ID不能为空")
    private Long roomId;

    @NotNull(message = "租赁类型不能为空")
    private Integer rentalType;

    @NotNull(message = "开始日期不能为空")
    private LocalDate startDate;

    @NotNull(message = "结束日期不能为空")
    private LocalDate endDate;

    private String remark;
}
