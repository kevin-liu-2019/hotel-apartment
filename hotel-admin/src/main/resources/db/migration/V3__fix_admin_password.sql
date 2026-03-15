-- ============================================================
-- 修复管理员密码哈希
-- V2 中误用了 "123456" 的 BCrypt 哈希，注释却写的是 admin123
-- 本迁移将所有管理员密码更新为 admin123 的正确 BCrypt 哈希
-- ============================================================
UPDATE `t_admin`
SET `password` = '$2a$10$6OerBScVv6M1JdPi.kafZeCcd/JGnffzdWeMfGBZJ9JGacykQzMG.'
WHERE `password` = '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi';
