-- ============================================================
-- 酒店公寓系统 — 初始化种子数据
-- 版本: V2  创建时间: 2024-01-01
-- 说明: 所有 INSERT 均使用 INSERT IGNORE，保证幂等性
-- ============================================================

-- ── 管理员 ──────────────────────────────────────────────────
-- 超级管理员  用户名: admin  密码: admin123  (BCrypt 10轮)
INSERT IGNORE INTO `t_admin` (`id`, `username`, `password`, `nickname`, `role`, `hotel_id`, `status`)
VALUES (1, 'admin', '$2a$10$6OerBScVv6M1JdPi.kafZeCcd/JGnffzdWeMfGBZJ9JGacykQzMG.', '超级管理员', 'SUPER_ADMIN', NULL, 1);

-- 市中心店酒店管理员  用户名: admin_center  密码: admin123
INSERT IGNORE INTO `t_admin` (`id`, `username`, `password`, `nickname`, `role`, `hotel_id`, `status`)
VALUES (2, 'admin_center', '$2a$10$6OerBScVv6M1JdPi.kafZeCcd/JGnffzdWeMfGBZJ9JGacykQzMG.', '市中心店管理员', 'ADMIN', 1, 1);

-- 望京店酒店管理员  用户名: admin_wangjing  密码: admin123
INSERT IGNORE INTO `t_admin` (`id`, `username`, `password`, `nickname`, `role`, `hotel_id`, `status`)
VALUES (3, 'admin_wangjing', '$2a$10$6OerBScVv6M1JdPi.kafZeCcd/JGnffzdWeMfGBZJ9JGacykQzMG.', '望京店管理员', 'ADMIN', 2, 1);

-- ── 酒店 ──────────────────────────────────────────────────────
INSERT IGNORE INTO `t_hotel`
  (`id`, `name`, `address`, `phone`, `intro`, `cover_image`, `facilities`, `latitude`, `longitude`, `min_price`, `status`, `sort_order`)
VALUES
  (1,
   '示范公寓·市中心店',
   '北京市朝阳区建国路88号',
   '010-12345678',
   '位于市中心黄金地段，紧邻国贸CBD，交通便利，步行5分钟可达地铁1号线。公寓配套设施完善，拎包入住，是商务出行和长期租住的理想选择。',
   'https://images.example.com/hotels/center/cover.jpg',
   '["WiFi","空调","洗衣机","冰箱","热水器","电视","独立卫浴","门禁系统","停车场","健身房"]',
   '39.9042',
   '116.4074',
   2800.00, 1, 1),
  (2,
   '示范公寓·望京店',
   '北京市朝阳区望京街道科技园路12号',
   '010-87654321',
   '坐落于望京核心科技园区，周边互联网企业林立，商圈成熟，地铁14号线/15号线双线可达。精装修公寓，适合长期租住的科技白领。',
   'https://images.example.com/hotels/wangjing/cover.jpg',
   '["WiFi","空调","洗衣机","冰箱","热水器","电视","独立卫浴","门禁系统","停车场","会议室","屋顶花园"]',
   '40.0000',
   '116.4833',
   3200.00, 1, 2);

-- ── 房间 ──────────────────────────────────────────────────────
INSERT IGNORE INTO `t_room`
  (`id`, `hotel_id`, `name`, `price`, `area`, `floor`, `orientation`, `images`, `stock`, `description`, `status`)
VALUES
  -- 市中心店
  (1, 1, '标准单间', 2800.00, 25,  5,  '南',
   '["https://images.example.com/rooms/1/img1.jpg","https://images.example.com/rooms/1/img2.jpg","https://images.example.com/rooms/1/img3.jpg"]',
   5, '25㎡精装修单间，独立卫浴，配备1.5m大床、智能电视、中央空调及基础家具，拎包入住。', 1),

  (2, 1, '精品大床房', 3500.00, 35,  8,  '南北通透',
   '["https://images.example.com/rooms/2/img1.jpg","https://images.example.com/rooms/2/img2.jpg","https://images.example.com/rooms/2/img3.jpg"]',
   3, '35㎡南北通透大床房，1.8m超大床，配备智能家居系统、独立浴室及独立阳台，城市景观一览无余。', 1),

  (3, 1, '豪华套房',  5000.00, 60, 15,  '东南',
   '["https://images.example.com/rooms/3/img1.jpg","https://images.example.com/rooms/3/img2.jpg","https://images.example.com/rooms/3/img3.jpg","https://images.example.com/rooms/3/img4.jpg"]',
   2, '60㎡顶层豪华套房，270°全景落地窗，独立卧室+客厅，配备高端厨电，俯瞰国贸CBD夜景。', 1),

  -- 望京店
  (4, 2, '舒适单间',  3200.00, 28,  3,  '南',
   '["https://images.example.com/rooms/4/img1.jpg","https://images.example.com/rooms/4/img2.jpg"]',
   4, '28㎡阳光单人公寓，采光极佳，配备全套家电家具，近地铁站500米，通勤便捷。', 1),

  (5, 2, '商务双人间', 4200.00, 45, 10,  '南',
   '["https://images.example.com/rooms/5/img1.jpg","https://images.example.com/rooms/5/img2.jpg","https://images.example.com/rooms/5/img3.jpg"]',
   2, '45㎡商务双人公寓，配备独立办公区、高速网络及双人床，适合商务差旅及双人居住，视野开阔。', 1),

  (6, 2, '精品Loft',  5500.00, 55,  7,  '东南',
   '["https://images.example.com/rooms/6/img1.jpg","https://images.example.com/rooms/6/img2.jpg","https://images.example.com/rooms/6/img3.jpg"]',
   1, '55㎡挑高Loft设计，上层卧室下层客厅，工业风精装，俯瞰望京中央公园绿化带，独特居住体验。', 1);

-- ── Banner / 轮播图 ─────────────────────────────────────────
INSERT IGNORE INTO `t_banner`
  (`id`, `hotel_id`, `title`, `image_url`, `link_url`, `link_type`, `status`, `sort_order`)
VALUES
  (1, 0, '品质生活 从这里开始',
   'https://images.example.com/banners/banner_home_1.jpg',
   '/pages/hotel-list/index', 1, 1, 1),

  (2, 0, '限时优惠 首月立减500元',
   'https://images.example.com/banners/banner_home_2.jpg',
   '/pages/hotel-list/index', 1, 1, 2),

  (3, 0, '长租优惠 满6月享9折',
   'https://images.example.com/banners/banner_home_3.jpg',
   '/pages/hotel-list/index', 1, 1, 3),

  (4, 1, '市中心店 开业特惠',
   'https://images.example.com/banners/banner_center_1.jpg',
   '/pages/hotel-detail/index?id=1', 1, 1, 1),

  (5, 2, '望京店 全新上线',
   'https://images.example.com/banners/banner_wangjing_1.jpg',
   '/pages/hotel-detail/index?id=2', 1, 1, 1);

-- ── 弹窗配置 ────────────────────────────────────────────────
INSERT IGNORE INTO `t_popup_config`
  (`id`, `hotel_id`, `title`, `image_url`, `link_url`, `start_time`, `end_time`, `status`, `sort_order`)
VALUES
  (1, 0, '新用户专享礼包',
   'https://images.example.com/popups/new_user_gift.jpg',
   '/pages/hotel-list/index',
   '2024-01-01 00:00:00', '2099-12-31 23:59:59', 1, 1),

  (2, 1, '市中心店限时特惠',
   'https://images.example.com/popups/center_promo.jpg',
   '/pages/hotel-detail/index?id=1',
   '2024-01-01 00:00:00', '2099-12-31 23:59:59', 1, 1),

  (3, 2, '望京店开业大酬宾',
   'https://images.example.com/popups/wangjing_promo.jpg',
   '/pages/hotel-detail/index?id=2',
   '2024-01-01 00:00:00', '2099-12-31 23:59:59', 1, 1);

-- ── 页面配置 ────────────────────────────────────────────────
-- 全局首页配置
INSERT IGNORE INTO `t_page_config`
  (`id`, `hotel_id`, `page_type`, `page_name`, `bg_color`, `status`, `components`)
VALUES
  (1, 0, 'HOME', '首页',  '#f5f5f5', 1,
   '[{"type":"BANNER","dataSource":"banner"},{"type":"HOTEL_LIST","title":"精选公寓","limit":6}]'),

  -- 市中心店酒店详情页配置
  (2, 1, 'HOTEL_DETAIL', '市中心店详情页', '#ffffff', 1,
   '[{"type":"IMAGE_SWIPER","dataSource":"hotel.coverImage"},{"type":"HOTEL_INFO"},{"type":"FACILITY_TAGS"},{"type":"ROOM_LIST","title":"可选房型"}]'),

  -- 望京店酒店详情页配置
  (3, 2, 'HOTEL_DETAIL', '望京店详情页', '#ffffff', 1,
   '[{"type":"IMAGE_SWIPER","dataSource":"hotel.coverImage"},{"type":"HOTEL_INFO"},{"type":"FACILITY_TAGS"},{"type":"ROOM_LIST","title":"可选房型"}]');
