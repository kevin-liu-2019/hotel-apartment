-- ============================================================
-- 酒店公寓系统 — 数据库初始化 DDL
-- 版本: V1  创建时间: 2024-01-01
-- ============================================================

-- 酒店表
CREATE TABLE IF NOT EXISTS `t_hotel` (
  `id`          BIGINT       NOT NULL AUTO_INCREMENT                          COMMENT '酒店ID',
  `name`        VARCHAR(100) NOT NULL                                         COMMENT '酒店名称',
  `address`     VARCHAR(255) NOT NULL                                         COMMENT '详细地址',
  `phone`       VARCHAR(20)                                                   COMMENT '联系电话',
  `intro`       TEXT                                                          COMMENT '酒店简介',
  `cover_image` VARCHAR(500)                                                  COMMENT '封面图片URL',
  `facilities`  JSON                                                          COMMENT '设施列表(JSON字符串数组)',
  `latitude`    VARCHAR(20)                                                   COMMENT '纬度',
  `longitude`   VARCHAR(20)                                                   COMMENT '经度',
  `min_price`   DECIMAL(10,2)                                                 COMMENT '最低月租价格(元)',
  `status`      TINYINT      NOT NULL DEFAULT 1                               COMMENT '状态: 0=下架, 1=上架',
  `sort_order`  INT          NOT NULL DEFAULT 0                               COMMENT '排序权重(升序)',
  `created_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP               COMMENT '创建时间',
  `updated_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted`     TINYINT      NOT NULL DEFAULT 0                               COMMENT '逻辑删除: 0=正常, 1=已删除',
  PRIMARY KEY (`id`),
  KEY `idx_status_sort`  (`status`, `sort_order`),
  KEY `idx_min_price`    (`min_price`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='酒店表';

-- 房间表
CREATE TABLE IF NOT EXISTS `t_room` (
  `id`          BIGINT        NOT NULL AUTO_INCREMENT                         COMMENT '房间ID',
  `hotel_id`    BIGINT        NOT NULL                                        COMMENT '所属酒店ID',
  `name`        VARCHAR(100)  NOT NULL                                        COMMENT '房型名称',
  `price`       DECIMAL(10,2) NOT NULL                                        COMMENT '月租价格(元)',
  `area`        INT                                                           COMMENT '面积(平方米)',
  `floor`       INT                                                           COMMENT '楼层',
  `orientation` VARCHAR(20)                                                   COMMENT '朝向',
  `images`      JSON                                                          COMMENT '图片URL列表(JSON字符串数组)',
  `stock`       INT           NOT NULL DEFAULT 0                              COMMENT '可用库存套数',
  `description` TEXT                                                          COMMENT '房间描述',
  `status`      TINYINT       NOT NULL DEFAULT 1                              COMMENT '状态: 0=下架, 1=上架',
  `created_at`  DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at`  DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted`     TINYINT       NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `idx_hotel_status` (`hotel_id`, `status`),
  KEY `idx_price`        (`price`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='房间表';

-- 用户表
CREATE TABLE IF NOT EXISTS `t_user` (
  `id`         BIGINT       NOT NULL AUTO_INCREMENT                           COMMENT '用户ID',
  `openid`     VARCHAR(64)  NOT NULL                                          COMMENT '微信 openid',
  `unionid`    VARCHAR(64)                                                    COMMENT '微信 unionid',
  `phone`      VARCHAR(20)                                                    COMMENT '绑定手机号',
  `nickname`   VARCHAR(64)                                                    COMMENT '昵称',
  `avatar`     VARCHAR(500)                                                   COMMENT '头像URL',
  `status`     TINYINT      NOT NULL DEFAULT 1                                COMMENT '状态: 0=禁用, 1=正常',
  `created_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_openid` (`openid`),
  KEY `idx_phone`        (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 订单表
CREATE TABLE IF NOT EXISTS `t_order` (
  `id`             BIGINT        NOT NULL AUTO_INCREMENT                      COMMENT '订单ID',
  `order_no`       VARCHAR(32)   NOT NULL                                     COMMENT '订单号(全局唯一)',
  `user_id`        BIGINT        NOT NULL                                     COMMENT '下单用户ID',
  `hotel_id`       BIGINT        NOT NULL                                     COMMENT '酒店ID',
  `room_id`        BIGINT        NOT NULL                                     COMMENT '房间ID',
  `rental_type`    TINYINT       NOT NULL                                     COMMENT '租赁类型: 0=短租, 1=长租',
  `start_date`     DATE          NOT NULL                                     COMMENT '入住日期',
  `end_date`       DATE          NOT NULL                                     COMMENT '退房日期',
  `total_price`    DECIMAL(10,2) NOT NULL                                     COMMENT '订单总价(元)',
  `status`         TINYINT       NOT NULL DEFAULT 0                           COMMENT '状态: 0=待签约, 1=待支付, 2=已支付, 3=已签约, 4=进行中, 5=已完成, 6=已取消',
  `pay_time`       DATETIME                                                   COMMENT '支付成功时间',
  `transaction_id` VARCHAR(64)                                                COMMENT '微信支付交易号',
  `contract_id`    BIGINT                                                     COMMENT '关联合同ID',
  `remark`         VARCHAR(500)                                               COMMENT '用户备注',
  `created_at`     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at`     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted`        TINYINT       NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no`   (`order_no`),
  KEY `idx_user_status`      (`user_id`, `status`),
  KEY `idx_hotel_status`     (`hotel_id`, `status`),
  KEY `idx_room_id`          (`room_id`),
  KEY `idx_created_at`       (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

-- 合同表
CREATE TABLE IF NOT EXISTS `t_contract` (
  `id`             BIGINT       NOT NULL AUTO_INCREMENT                       COMMENT '合同ID',
  `order_id`       BIGINT       NOT NULL                                      COMMENT '关联订单ID',
  `contract_no`    VARCHAR(32)  NOT NULL                                      COMMENT '合同编号(全局唯一)',
  `third_party_id` VARCHAR(64)                                                COMMENT '第三方电子签章平台合同ID',
  `file_url`       VARCHAR(500)                                               COMMENT '合同PDF文件URL',
  `sign_status`    TINYINT      NOT NULL DEFAULT 0                            COMMENT '签署状态: 0=未签, 1=已签',
  `sign_time`      DATETIME                                                   COMMENT '完成签署时间',
  `created_at`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at`     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_contract_no` (`contract_no`),
  KEY `idx_order_id`         (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='合同表';

-- 页面配置表（支持按酒店定制各页面内容）
CREATE TABLE IF NOT EXISTS `t_page_config` (
  `id`         BIGINT       NOT NULL AUTO_INCREMENT                           COMMENT '配置ID',
  `hotel_id`   BIGINT       NOT NULL                                          COMMENT '所属酒店ID(0=全局)',
  `page_type`  VARCHAR(50)  NOT NULL                                          COMMENT '页面类型标识,如 HOME / HOTEL_DETAIL / ROOM_DETAIL',
  `page_name`  VARCHAR(100)                                                   COMMENT '页面显示名称',
  `components` JSON                                                           COMMENT '组件配置(JSON数组)',
  `bg_color`   VARCHAR(20)                                                    COMMENT '页面背景色(十六进制)',
  `status`     TINYINT      NOT NULL DEFAULT 1                                COMMENT '状态: 0=禁用, 1=启用',
  `link`       VARCHAR(500)                                                   COMMENT '跳转链接',
  `created_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_hotel_page` (`hotel_id`, `page_type`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='页面配置表';

-- 弹窗配置表
CREATE TABLE IF NOT EXISTS `t_popup_config` (
  `id`         BIGINT       NOT NULL AUTO_INCREMENT                           COMMENT '弹窗ID',
  `hotel_id`   BIGINT       NOT NULL                                          COMMENT '所属酒店ID(0=全局)',
  `title`      VARCHAR(100)                                                   COMMENT '弹窗标题',
  `image_url`  VARCHAR(500)                                                   COMMENT '弹窗图片URL',
  `link_url`   VARCHAR(500)                                                   COMMENT '点击跳转链接',
  `start_time` DATETIME                                                       COMMENT '展示开始时间',
  `end_time`   DATETIME                                                       COMMENT '展示结束时间',
  `status`     TINYINT      NOT NULL DEFAULT 1                                COMMENT '状态: 0=禁用, 1=启用',
  `sort_order` INT          NOT NULL DEFAULT 0                                COMMENT '同酒店内排序(升序)',
  `created_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_hotel_status_time` (`hotel_id`, `status`, `start_time`, `end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='弹窗配置表';

-- 首页 Banner / 轮播图表
CREATE TABLE IF NOT EXISTS `t_banner` (
  `id`         BIGINT       NOT NULL AUTO_INCREMENT                           COMMENT 'Banner ID',
  `hotel_id`   BIGINT       NOT NULL DEFAULT 0                               COMMENT '所属酒店ID(0=全局首页)',
  `title`      VARCHAR(100)                                                   COMMENT 'Banner 标题',
  `image_url`  VARCHAR(500) NOT NULL                                          COMMENT 'Banner 图片URL',
  `link_url`   VARCHAR(500)                                                   COMMENT '点击跳转链接',
  `link_type`  TINYINT      NOT NULL DEFAULT 0                                COMMENT '跳转类型: 0=无跳转, 1=小程序页面, 2=外部链接',
  `status`     TINYINT      NOT NULL DEFAULT 1                                COMMENT '状态: 0=禁用, 1=启用',
  `sort_order` INT          NOT NULL DEFAULT 0                                COMMENT '展示排序(升序)',
  `created_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted`    TINYINT      NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `idx_hotel_status_sort` (`hotel_id`, `status`, `sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Banner/轮播图表';

-- 分销关系表（记录三级分销上下级绑定关系及累计佣金汇总）
CREATE TABLE IF NOT EXISTS `t_distribution` (
  `id`                   BIGINT        NOT NULL AUTO_INCREMENT                COMMENT '记录ID',
  `user_id`              BIGINT        NOT NULL                               COMMENT '当前用户ID',
  `parent_id`            BIGINT                                               COMMENT '一级上级用户ID',
  `grand_parent_id`      BIGINT                                               COMMENT '二级上级用户ID',
  `great_grand_parent_id` BIGINT                                              COMMENT '三级上级用户ID',
  `level`                TINYINT       NOT NULL DEFAULT 1                     COMMENT '在上级链路中的层级',
  `total_commission`     DECIMAL(10,2) NOT NULL DEFAULT 0.00                  COMMENT '累计已结算佣金(元)',
  `created_at`           DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id`   (`user_id`),
  KEY `idx_parent_id`       (`parent_id`),
  KEY `idx_grand_parent_id` (`grand_parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分销关系表';

-- 佣金流水表（记录每笔订单触发的分销佣金明细）
CREATE TABLE IF NOT EXISTS `t_commission_record` (
  `id`              BIGINT        NOT NULL AUTO_INCREMENT                     COMMENT '流水ID',
  `order_id`        BIGINT        NOT NULL                                    COMMENT '关联订单ID',
  `order_no`        VARCHAR(32)   NOT NULL                                    COMMENT '关联订单号',
  `beneficiary_id`  BIGINT        NOT NULL                                    COMMENT '佣金受益用户ID',
  `payer_id`        BIGINT        NOT NULL                                    COMMENT '支付用户ID(订单所有者)',
  `level`           TINYINT       NOT NULL DEFAULT 1                          COMMENT '分销层级: 1=直接下级, 2=二级, 3=三级',
  `amount`          DECIMAL(10,2) NOT NULL                                    COMMENT '本次佣金金额(元)',
  `rate`            DECIMAL(5,4)  NOT NULL DEFAULT 0.0000                     COMMENT '佣金比例',
  `status`          TINYINT       NOT NULL DEFAULT 0                          COMMENT '状态: 0=待结算, 1=已结算, 2=已撤销',
  `settled_at`      DATETIME                                                  COMMENT '结算时间',
  `created_at`      DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at`      DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_order_id`       (`order_id`),
  KEY `idx_beneficiary`    (`beneficiary_id`, `status`),
  KEY `idx_payer_id`       (`payer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分销佣金流水表';

-- 管理员表
CREATE TABLE IF NOT EXISTS `t_admin` (
  `id`         BIGINT       NOT NULL AUTO_INCREMENT                           COMMENT '管理员ID',
  `username`   VARCHAR(50)  NOT NULL                                          COMMENT '登录用户名',
  `password`   VARCHAR(100) NOT NULL                                          COMMENT '登录密码(BCrypt哈希)',
  `nickname`   VARCHAR(50)                                                    COMMENT '显示昵称',
  `role`       VARCHAR(20)  NOT NULL DEFAULT 'ADMIN'                          COMMENT '角色: SUPER_ADMIN=超级管理员, ADMIN=酒店管理员',
  `hotel_id`   BIGINT                                                         COMMENT '所属酒店ID(NULL=超级管理员)',
  `status`     TINYINT      NOT NULL DEFAULT 1                                COMMENT '状态: 0=禁用, 1=正常',
  `created_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  KEY `idx_hotel_id`       (`hotel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员表';
