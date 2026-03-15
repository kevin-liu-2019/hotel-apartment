package com.hotel.apartment.dal.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("t_distribution")
public class Distribution {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long parentId;

    private Long grandParentId;

    private Long greatGrandParentId;

    private Integer level;

    private BigDecimal totalCommission;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}
