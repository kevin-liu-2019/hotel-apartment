package com.hotel.apartment.dal.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("t_contract")
public class Contract {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long orderId;

    private String contractNo;

    private String thirdPartyId;

    private String fileUrl;

    private Integer signStatus;

    private LocalDateTime signTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
