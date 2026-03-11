-- 初始超级管理员 (密码: admin123)
INSERT IGNORE INTO `t_admin` (`username`, `password`, `nickname`, `role`, `hotel_id`, `status`)
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '超级管理员', 'SUPER_ADMIN', NULL, 1);

-- 示例酒店数据
INSERT IGNORE INTO `t_hotel` (`id`, `name`, `address`, `phone`, `intro`, `min_price`, `status`, `sort_order`)
VALUES
  (1, '示范公寓·市中心店', '北京市朝阳区建国路88号', '010-12345678', '位于市中心黄金地段，交通便利，配套设施齐全', 2800.00, 1, 1),
  (2, '示范公寓·望京店', '北京市朝阳区望京街道12号', '010-87654321', '望京核心区域，周边商圈成熟，适合白领居住', 3200.00, 1, 2);

-- 示例房间数据
INSERT IGNORE INTO `t_room` (`hotel_id`, `name`, `price`, `area`, `floor`, `orientation`, `stock`, `description`, `status`)
VALUES
  (1, '标准单间', 2800.00, 25, 5, '南', 5, '精装修单间，独立卫浴，含基础家具', 1),
  (1, '精品大床房', 3500.00, 35, 8, '南北通透', 3, '宽敞大床房，配备智能家居系统', 1),
  (1, '豪华套房', 5000.00, 60, 15, '东南', 2, '顶层豪华套房，270度城市景观', 1),
  (2, '舒适单间', 3200.00, 28, 3, '南', 4, '精心设计的单人公寓，阳光充足', 1),
  (2, '商务双人间', 4200.00, 45, 10, '南', 2, '适合商务出行，配备办公桌及高速网络', 1);
