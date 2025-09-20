-- 初始化示例数据
-- 注意：这个文件会在应用启动时自动执行（如果数据库为空）

-- 兼容H2和MySQL的插入语句
INSERT INTO todos (title, description, completed, created_at, updated_at) 
SELECT '学习Spring Boot', '完成Spring Boot基础教程', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM todos WHERE title = '学习Spring Boot');

INSERT INTO todos (title, description, completed, created_at, updated_at) 
SELECT '完成项目文档', '编写技术架构文档', FALSE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM todos WHERE title = '完成项目文档');

INSERT INTO todos (title, description, completed, created_at, updated_at) 
SELECT '代码审查', '审查待办事项应用代码', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (SELECT 1 FROM todos WHERE title = '代码审查');
