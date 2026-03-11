-- 酒店表
CREATE TABLE IF NOT EXISTS `t_hotel` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '酒店ID',
  `name` VARCHAR(100) NOT NULL COMMENT '酒店名称',
  `address` VARCHAR(255) NOT NULL COMMENT '地址',
  `phone` VARCHAR(20) COMMENT '联系电话',
  `intro` TEXT COMMENT '酒店简介',
  `cover_image` VARCHAR(500) COMMENT '封面图片URL',
  `facilities` JSON COMMENT '设施列表(JSON数组)',
  `latitude` VARCHAR(20) COMMENT '纬度',
  `longitude` VARCHAR(20) COMMENT '经度',
  `min_price` DECIMAL(10,2) COMMENT '最低价格',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:0下架,1上架',
  `sort_order` INT NOT NULL DEFAULT 0 COMMENT '排序',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除:0正常,1删除',
  PRIMARY KEY (`id`),
  KEY `idx_status` (`status`, `sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店表';

-- 房间表
CREATE TABLE IF NOT EXISTS `t_room` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '房间ID',
  `hotel_id` BIGINT NOT NULL COMMENT '酒店ID',
  `name` VARCHAR(100) NOT NULL COMMENT '房间名称',
  `price` DECIMAL(10,2) NOT NULL COMMENT '月租价格',
  `area` INT COMMENT '面积(平方米)',
  `floor` INT COMMENT '楼层',
  `orientation` VARCHAR(20) COMMENT '朝向',
  `images` JSON COMMENT '图片列表(JSON数组)',
  `stock` INT NOT NULL DEFAULT 0 COMMENT '库存数量',
  `description` TEXT COMMENT '描述',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:0下架,1上架',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `idx_hotel_id` (`hotel_id`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房间表';

-- 用户表
CREATE TABLE IF NOT EXISTS `t_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `openid` VARCHAR(64) NOT NULL COMMENT '微信openid',
  `unionid` VARCHAR(64) COMMENT '微信unionid',
  `phone` VARCHAR(20) COMMENT '手机号',
  `nickname` VARCHAR(64) COMMENT '昵称',
  `avatar` VARCHAR(500) COMMENT '头像URL',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态:0禁用,1正常',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_openid` (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 订单表
CREATE TABLE IF NOT EXISTS `t_order` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` VARCHAR(32) NOT NULL COMMENT '订单号',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `hotel_id` BIGINT NOT NULL COMMENT '酒店ID',
  `room_id` BIGINT NOT NULL COMMENT '房间ID',
  `rental_type` TINYINT NOT NULL COMMENT '租赁类型:0短租,1长租',
  `start_date` DATE NOT NULL COMMENT '开始日期',
  `end_date` DATE NOT NULL COMMENT '结束日期',
  `total_price` DECIMAL(10,2) NOT NULL COMMENT '总价',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态:0待签约,1待支付,2已支付,3已签约,4进行中,5已完成,6已取消',
  `pay_time` DATETIME COMMENT '支付时间',
  `transaction_id` VARCHAR(64) COMMENT '微信支付交易号',
  `contract_id` BIGINT COMMENT '合同ID',
  `remark` VARCHAR(500) COMMENT '备注',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`, `status`),
  KEY `idx_room_id` (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 合同表
CREATE TABLE IF NOT EXISTS `t_contract` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '合同ID',
  `order_id` BIGINT NOT NULL COMMENT '订单ID',
  `contract_no` VARCHAR(32) NOT NULL COMMENT '合同编号',
  `third_party_id` VARCHAR(64) COMMENT '第三方合同ID',
  `file_url` VARCHAR(500) COMMENT '合同文件URL',
  `sign_status` TINYINT NOT NULL DEFAULT 0 COMMENT '签署状态:0未签,1已签',
  `sign_time` DATETIME COMMENT '签署时间',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_contract_no` (`contract_no`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='合同表';

-- 页面配置表
CREATE TABLE IF NOT EXISTS `t_page_config` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  `hotel_id` BIGINT NOT NULL COMMENT '酒店ID',
  `page_type` VARCHAR(50) NOT NULL COMMENT '页面类型',
  `page_name` VARCHAR(100) COMMENT '页面名称',
  `components` JSON COMMENT '组件配置(JSON)',
  `bg_color` VARCHAR(20) COMMENT '背景颜色',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态',
  `link` VARCHAR(500) COMMENT '链接',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_hotel_page` (`hotel_id`, `page_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='页面配置表';

-- 弹窗配置表
CREATE TABLE IF NOT EXISTS `t_popup_config` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '弹窗ID',
  `hotel_id` BIGINT NOT NULL COMMENT '酒店ID',
  `title` VARCHAR(100) COMMENT '标题',
  `image_url` VARCHAR(500) COMMENT '图片URL',
  `link_url` VARCHAR(500) COMMENT '链接',
  `start_time` DATETIME COMMENT '开始时间',
  `end_time` DATETIME COMMENT '结束时间',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_hotel_id` (`hotel_id`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='弹窗配置表';

-- 分销关系表
CREATE TABLE IF NOT EXISTS `t_distribution` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `parent_id` BIGINT COMMENT '上级用户ID',
  `grand_parent_id` BIGINT COMMENT '上上级用户ID',
  `great_grand_parent_id` BIGINT COMMENT '上上上级用户ID',
  `level` TINYINT NOT NULL DEFAULT 1 COMMENT '层级',
  `total_commission` DECIMAL(10,2) NOT NULL DEFAULT 0 COMMENT '累计佣金',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分销关系表';

-- 管理员表
CREATE TABLE IF NOT EXISTS `t_admin` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码(BCrypt)',
  `nickname` VARCHAR(50) COMMENT '昵称',
  `role` VARCHAR(20) NOT NULL DEFAULT 'ADMIN' COMMENT '角色:SUPER_ADMIN,ADMIN',
  `hotel_id` BIGINT COMMENT '所属酒店ID(null表示超级管理员)',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';
