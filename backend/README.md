# Todo Backend API

基于 Java Spring Boot 3.0 的 RESTful 待办事项管理 API，支持完整的 CRUD 操作、状态切换、批量删除、过滤查询、健康检查和 Swagger 文档等功能。

## 🚀 快速开始

### 环境要求

- Java 21+
- Maven 3.6+
- MySQL 8.4.6
- IDE: IntelliJ IDEA / Eclipse / VS Code

### 数据库配置

1. **安装MySQL 8.4.6**
2. **创建数据库**：
   ```sql
   CREATE DATABASE todoapp CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```
3. **配置数据库连接**（已在 `application.yml` 中配置）：
   - 地址：`localhost:3306`
   - 用户名：`mysql`
   - 密码：空
   - 数据库名：`todoapp`

### 项目启动

1. **克隆项目**
   ```bash
   git clone <repository-url>
   cd backend
   ```

2. **安装依赖**
   ```bash
   mvn clean install
   ```

3. **运行应用**
   ```bash
   mvn spring-boot:run
   ```

4. **访问应用**
   - API Base URL: `http://localhost:8000/api/v1`
   - Swagger UI: `http://localhost:8000/swagger-ui.html`
   - 健康检查: `http://localhost:8000/health`

## 📋 技术栈

- **框架**: Spring Boot 3.0.12
- **Java版本**: Java 21
- **数据库**: MySQL 8.4.6
- **ORM**: Spring Data JPA + Hibernate
- **文档**: Swagger (springdoc-openapi)
- **工具库**: Lombok
- **测试**: JUnit 5 + Mockito + H2 Database
- **构建工具**: Maven

## 🛠 项目结构

```
src/
├── main/
│   ├── java/com/todoapp/
│   │   ├── TodoBackendApplication.java     # 启动类
│   │   ├── config/                         # 配置类
│   │   │   ├── CorsConfig.java            # CORS配置
│   │   │   └── OpenApiConfig.java         # Swagger配置
│   │   ├── controller/                     # 控制器层
│   │   │   ├── TodoController.java        # Todo API控制器
│   │   │   └── HealthController.java      # 健康检查控制器
│   │   ├── dto/                           # 数据传输对象
│   │   │   ├── ApiResponse.java           # 统一响应格式
│   │   │   ├── TodoCreateRequest.java     # 创建请求DTO
│   │   │   ├── TodoUpdateRequest.java     # 更新请求DTO
│   │   │   ├── TodoResponse.java          # 响应DTO
│   │   │   └── DeleteResponse.java        # 删除响应DTO
│   │   ├── entity/                        # 实体类
│   │   │   └── Todo.java                  # Todo实体
│   │   ├── exception/                     # 异常处理
│   │   │   ├── TodoNotFoundException.java # 自定义异常
│   │   │   └── GlobalExceptionHandler.java# 全局异常处理器
│   │   ├── repository/                    # 数据访问层
│   │   │   └── TodoRepository.java        # Todo仓库接口
│   │   └── service/                       # 业务逻辑层
│   │       └── TodoService.java           # Todo服务类
│   └── resources/
│       └── application.yml                # 配置文件
└── test/                                  # 测试代码
    ├── java/com/todoapp/
    │   ├── controller/                    # 控制器测试
    │   ├── service/                       # 服务层测试
    │   └── repository/                    # 数据访问层测试
    └── resources/
        └── application-test.yml           # 测试配置
```

## 📚 API 接口文档

### 基础信息
- **Base URL**: `http://localhost:8000/api/v1`
- **Content-Type**: `application/json`
- **响应格式**: JSON

### 核心接口

| 方法 | 路径 | 描述 |
|------|------|------|
| `GET` | `/api/v1/todos` | 获取所有待办事项 |
| `GET` | `/api/v1/todos/{id}` | 获取单个待办事项 |
| `POST` | `/api/v1/todos` | 创建待办事项 |
| `PUT` | `/api/v1/todos/{id}` | 更新待办事项 |
| `PATCH` | `/api/v1/todos/{id}/toggle` | 切换待办事项状态 |
| `DELETE` | `/api/v1/todos/{id}` | 删除待办事项 |
| `DELETE` | `/api/v1/todos/completed` | 批量删除已完成的待办事项 |
| `DELETE` | `/api/v1/todos/all` | 删除所有待办事项 |
| `GET` | `/api/v1/todos/stats` | 获取统计信息 |
| `GET` | `/health` | 健康检查 |

### 示例请求

#### 创建待办事项
```bash
curl -X POST http://localhost:8000/api/v1/todos \
  -H "Content-Type: application/json" \
  -d '{
    "title": "完成项目文档",
    "description": "编写技术架构文档和用户指南"
  }'
```

#### 响应示例
```json
{
  "code": 201,
  "message": "Todo created successfully",
  "data": {
    "id": 1,
    "title": "完成项目文档",
    "description": "编写技术架构文档和用户指南",
    "completed": false,
    "createdAt": "2024-01-01T10:00:00",
    "updatedAt": "2024-01-01T10:00:00"
  }
}
```

#### 获取待办事项列表（支持过滤）
```bash
# 获取所有待办事项
curl http://localhost:8000/api/v1/todos

# 获取已完成的待办事项
curl http://localhost:8000/api/v1/todos?completed=true

# 获取未完成的待办事项
curl http://localhost:8000/api/v1/todos?completed=false
```

## 🧪 测试

项目包含完整的测试套件，包括单元测试和集成测试。

### 运行测试

```bash
# 运行所有测试
mvn test

# 运行测试并生成覆盖率报告
mvn clean test jacoco:report
```

### 测试覆盖率

- **目标覆盖率**: 80%+
- **测试框架**: JUnit 5 + Mockito
- **集成测试**: Spring Boot Test + H2 Database
- **覆盖率工具**: JaCoCo

### 测试类别

1. **单元测试**
   - `TodoServiceTest`: 业务逻辑测试
   - `TodoRepositoryTest`: 数据访问测试
   - `HealthControllerTest`: 控制器单元测试

2. **集成测试**
   - `TodoControllerIntegrationTest`: API接口集成测试

## 🔧 配置说明

### 应用配置 (`application.yml`)

```yaml
server:
  port: 8000

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/todoapp
    username: mysql
    password: 
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### CORS配置

支持来自 `http://localhost:3000` 的跨域请求，适配前端应用。

### Swagger配置

- 文档地址: `http://localhost:8000/swagger-ui.html`
- API文档: `http://localhost:8000/api-docs`

## 📊 监控和健康检查

### 健康检查接口

```bash
curl http://localhost:8000/health
```

响应：
```json
{
  "status": "UP",
  "timestamp": "2024-01-01T10:00:00Z",
  "version": "1.0.0"
}
```

### Spring Boot Actuator

支持的监控端点：
- `/actuator/health`: 健康状态
- `/actuator/info`: 应用信息
- `/actuator/metrics`: 应用指标

## 🚨 错误处理

### 统一错误响应格式

```json
{
  "code": 404,
  "message": "Todo not found with id: 999"
}
```

### 参数验证错误

```json
{
  "code": 400,
  "message": "Validation failed",
  "errors": {
    "title": "标题不能为空"
  }
}
```

## 🔒 安全特性

1. **输入验证**: 使用Bean Validation进行参数校验
2. **SQL注入防护**: 使用JPA/Hibernate的参数化查询
3. **XSS防护**: JSON序列化自动转义特殊字符
4. **CORS配置**: 限制跨域访问来源

## 📈 性能优化

1. **数据库索引**: 为常用查询字段添加索引
2. **连接池**: 使用HikariCP连接池
3. **查询优化**: 使用JPA查询优化和懒加载
4. **响应时间**: 平均响应时间 < 200ms

## 🛠 开发工具

### 推荐IDE配置

#### IntelliJ IDEA
1. 安装Lombok插件
2. 启用注解处理器：Settings > Build > Annotation Processors > Enable annotation processing

#### VS Code
1. 安装Java扩展包
2. 安装Lombok插件

### 数据库管理工具

- **MySQL Workbench**: GUI管理工具
- **DataGrip**: JetBrains数据库工具
- **命令行**: mysql客户端

## 📦 部署

### 生产环境配置

1. **打包应用**
   ```bash
   mvn clean package -DskipTests
   ```

2. **运行JAR包**
   ```bash
   java -jar target/todo-backend-1.0.0.jar
   ```

3. **环境变量配置**
   ```bash
   export SPRING_PROFILES_ACTIVE=prod
   export SPRING_DATASOURCE_URL=jdbc:mysql://prod-host:3306/todoapp
   export SPRING_DATASOURCE_USERNAME=prod_user
   export SPRING_DATASOURCE_PASSWORD=prod_password
   ```

### Docker部署

```dockerfile
FROM openjdk:21-jdk-slim
COPY target/todo-backend-1.0.0.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 🤝 开发规范

### 代码风格
- 使用Java标准命名规范
- 类和方法必须有JavaDoc注释
- 使用Lombok减少样板代码

### Git提交规范
```
feat: 添加新功能
fix: 修复bug
docs: 更新文档
test: 添加测试
refactor: 代码重构
```

### 分支策略
- `main`: 主分支，用于生产环境
- `develop`: 开发分支
- `feature/*`: 功能分支
- `hotfix/*`: 热修复分支

## 📋 待办事项

- [ ] 添加用户认证和授权
- [ ] 实现数据缓存机制
- [ ] 添加审计日志功能
- [ ] 支持批量操作API
- [ ] 实现实时通知功能

## 🐛 已知问题

目前没有已知的重要问题。

### 已修复问题
- ✅ **日期格式兼容性**: 修复LocalDateTime序列化，前端可正确解析时间
- ✅ **更新时间同步**: 优化更新机制，确保编辑后立即显示最新时间
- ✅ **字段命名统一**: 统一使用驼峰命名(createdAt/updatedAt)

如果发现新问题，请在Issues中报告。

## 📞 联系方式

- **项目负责人**: Todo App Team
- **邮箱**: todoapp@example.com
- **文档**: 参考需求文档.md

## 📄 许可证

本项目采用 MIT 许可证。详见 LICENSE 文件。

---

**注意**: 在生产环境中使用前，请确保：
1. 修改默认数据库密码
2. 配置SSL/TLS加密
3. 设置适当的CORS策略
4. 启用应用监控和日志记录
