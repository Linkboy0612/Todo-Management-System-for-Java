-- Todo应用数据库初始化脚本
-- 在MySQL中执行此脚本来创建数据库和表结构

-- 创建数据库
CREATE DATABASE IF NOT EXISTS todoapp CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE todoapp;

-- 创建todos表
CREATE TABLE IF NOT EXISTS todos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    completed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 创建索引优化查询性能
CREATE INDEX IF NOT EXISTS idx_todos_completed ON todos(completed);
CREATE INDEX IF NOT EXISTS idx_todos_created_at ON todos(created_at);
CREATE INDEX IF NOT EXISTS idx_todos_title ON todos(title);

-- 插入示例数据
INSERT IGNORE INTO todos (title, description, completed) VALUES 
('学习Spring Boot', '完成Spring Boot基础教程', FALSE),
('完成项目文档', '编写技术架构文档', FALSE),
('代码审查', '审查待办事项应用代码', TRUE);

-- 显示表结构
DESCRIBE todos;

-- 显示初始数据
SELECT * FROM todos;
