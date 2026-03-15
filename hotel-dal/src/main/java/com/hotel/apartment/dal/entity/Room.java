package com.hotel.apartment.dal.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("t_room")
public class Room {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long hotelId;

    private String name;

    private BigDecimal price;

    private Integer area;

    private Integer floor;

    private String orientation;

    private String images;

    private Integer stock;

    private String description;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableLogic
    private Integer deleted;
}
