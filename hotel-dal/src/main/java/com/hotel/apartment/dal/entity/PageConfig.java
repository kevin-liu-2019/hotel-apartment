package com.hotel.apartment.dal.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_page_config")
public class PageConfig {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long hotelId;

    private String pageType;

    private String pageName;

    private String components;

    private String bgColor;

    private Integer status;

    private String link;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
