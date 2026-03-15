package com.hotel.apartment.dal.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_popup_config")
public class PopupConfig {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long hotelId;

    private String title;

    private String imageUrl;

    private String linkUrl;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
