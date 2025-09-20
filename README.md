# 📝 待办事项管理系统

一个完整的全栈待办事项管理应用，采用现代化技术栈开发，具备美观的UI设计和完整的后端API。

## 🌟 项目概述

本项目是基于**逆向开发**理念构建的待办事项管理系统，从需求分析到前后端实现，完整展示了现代Web应用的开发流程。

### ✨ 核心特性

- 🎯 **完整CRUD操作** - 创建、查看、编辑、删除待办事项
- ⚡ **实时状态更新** - 即时切换完成状态，无需刷新页面
- 🎨 **现代化UI设计** - 响应式界面，支持桌面和移动端
- 📊 **智能筛选** - 按完成状态筛选，快速查找目标事项
- 🔄 **批量操作** - 一键清除已完成或所有待办事项
- 📈 **实时统计** - 动态显示完成进度和数量统计
- 🔍 **API文档** - 完整的Swagger接口文档
- 🩺 **健康监控** - 系统健康检查和状态监控

## 🛠 技术架构

### 后端技术栈
- **Java 21** - 现代Java特性
- **Spring Boot 3.0** - 企业级框架
- **Spring Data JPA** - 数据持久化
- **MySQL 8.4** - 关系型数据库
- **Swagger/OpenAPI** - API文档生成
- **Maven** - 项目构建管理

### 前端技术栈
- **React 18** - 现代化前端框架
- **TypeScript** - 类型安全开发
- **CSS3** - 响应式样式设计
- **Axios** - HTTP客户端
- **Create React App** - 快速项目搭建

### 核心功能模块
```
┌─ 前端应用 (React + TypeScript)
│  ├─ 用户界面层
│  ├─ 状态管理
│  ├─ API通信
│  └─ 响应式设计
│
├─ 后端服务 (Spring Boot)
│  ├─ RESTful API
│  ├─ 业务逻辑层
│  ├─ 数据访问层
│  └─ 异常处理
│
└─ 数据存储 (MySQL)
   ├─ 数据持久化
   ├─ 事务管理
   └─ 索引优化
```

## 🚀 快速开始

### 环境要求

#### 后端环境
- Java 21+
- Maven 3.6+
- MySQL 8.4+

#### 前端环境
- Node.js 16.0+
- npm 8.0+

### 安装和启动

#### 1. 克隆项目
```bash
git clone <repository-url>
```

#### 2. 数据库配置
```sql
-- 创建数据库
CREATE DATABASE todoapp CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 初始化表结构
mysql -u root -p todoapp < backend/database-schema.sql
```

#### 3. 启动后端服务
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

#### 4. 启动前端应用
```bash
cd frontend
npm install
npm start
```

### 访问应用
- **前端应用**: http://localhost:3000
- **后端API**: http://localhost:8000
- **API文档**: http://localhost:8000/swagger-ui.html
- **健康检查**: http://localhost:8000/health

## 📚 项目结构

```
作业4：逆向开发待办事项应用/
├── README.md                    # 项目主文档
├── 需求文档.md                  # 原始需求规格
├── backend/                     # 后端Java应用
│   ├── src/main/java/com/todoapp/
│   │   ├── TodoBackendApplication.java  # 启动类
│   │   ├── config/             # 配置类
│   │   ├── controller/         # REST控制器
│   │   ├── dto/               # 数据传输对象
│   │   ├── entity/            # JPA实体
│   │   ├── exception/         # 异常处理
│   │   ├── repository/        # 数据访问层
│   │   └── service/           # 业务逻辑层
│   ├── src/main/resources/
│   │   ├── application.yml    # 应用配置
│   │   └── data.sql          # 初始数据
│   ├── src/test/             # 测试代码
│   ├── database-schema.sql   # 数据库脚本
│   ├── pom.xml              # Maven配置
│   └── README.md            # 后端文档
└── frontend/                # 前端React应用
    ├── src/
    │   ├── components/      # React组件
    │   ├── services/        # API服务
    │   ├── types/          # TypeScript类型
    │   ├── styles/         # 样式文件
    │   ├── App.tsx         # 主应用组件
    │   └── index.tsx       # 应用入口
    ├── public/             # 静态资源
    ├── package.json        # 项目配置
    ├── tsconfig.json       # TS配置
    └── README.md           # 前端文档
```

## 🔧 核心功能

### 📋 待办事项管理
- **添加事项**: 支持标题和详细描述
- **编辑内容**: 实时编辑标题和描述
- **状态切换**: 一键标记完成/未完成
- **删除操作**: 单个删除和批量清理

### 🎯 状态筛选
- **全部事项**: 显示所有待办事项
- **未完成**: 仅显示待处理事项
- **已完成**: 仅显示已完成事项

### 📊 数据统计
- **总数显示**: 实时统计事项总数
- **完成进度**: 动态显示完成百分比
- **分类计数**: 按状态分类统计

### 🔄 批量操作
- **清除已完成**: 一键删除所有已完成事项
- **清空全部**: 删除所有待办事项

## 🌐 API接口

### 基础信息
- **Base URL**: `http://localhost:8000/api/v1`
- **Content-Type**: `application/json`
- **文档地址**: http://localhost:8000/swagger-ui.html

### 主要端点
| 方法 | 端点 | 描述 |
|------|------|------|
| `GET` | `/todos` | 获取所有待办事项 |
| `GET` | `/todos/{id}` | 获取单个待办事项 |
| `POST` | `/todos` | 创建待办事项 |
| `PUT` | `/todos/{id}` | 更新待办事项 |
| `PATCH` | `/todos/{id}/toggle` | 切换完成状态 |
| `DELETE` | `/todos/{id}` | 删除待办事项 |
| `DELETE` | `/todos/completed` | 批量删除已完成 |
| `DELETE` | `/todos/all` | 删除所有事项 |

### 数据格式
```typescript
interface Todo {
  id: number;
  title: string;
  description?: string;
  completed: boolean;
  createdAt: string;
  updatedAt: string;
}
```

## 🧪 测试

### 后端测试
```bash
cd backend
mvn test                    # 运行所有测试
mvn clean test jacoco:report # 生成覆盖率报告
```

### 前端测试
```bash
cd frontend
npm test                    # 运行测试套件
npm run build              # 构建生产版本
```

### 测试覆盖
- **单元测试**: Service层和Repository层
- **集成测试**: Controller层API接口
- **端到端测试**: 前后端交互流程

## 🎨 设计特色

### UI/UX设计
- **现代化界面**: 渐变背景和毛玻璃效果
- **响应式布局**: 适配各种屏幕尺寸
- **流畅动画**: 平滑的过渡和交互反馈
- **直观操作**: 清晰的视觉层次和操作指引

### 技术亮点
- **实时更新**: 编辑后立即显示更新时间
- **字段统一**: 前后端使用一致的驼峰命名
- **错误处理**: 完善的异常处理和用户提示
- **CORS支持**: 完整的跨域资源共享配置

## 📈 性能优化

### 后端优化
- **数据库索引**: 常用查询字段添加索引
- **连接池**: HikariCP高性能连接池
- **JPA优化**: 合理使用懒加载和查询优化
- **缓存机制**: 适当的数据缓存策略

### 前端优化
- **组件优化**: React.memo避免不必要渲染
- **状态管理**: 合理的状态提升和传递
- **代码分割**: 按需加载减少初始包大小
- **样式优化**: CSS优化和响应式设计

## 🔒 安全特性

- **输入验证**: 前后端双重参数校验
- **SQL注入防护**: JPA参数化查询
- **XSS防护**: React自带安全机制
- **CORS限制**: 严格的跨域访问控制

## 📦 部署指南

### 生产环境构建
```bash
# 后端打包
cd backend
mvn clean package -DskipTests

# 前端构建
cd frontend
npm run build
```

### Docker部署
```dockerfile
# 后端Dockerfile示例
FROM openjdk:21-jdk-slim
COPY target/todo-backend-1.0.0.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 🤝 贡献指南

### 开发规范
- 遵循Java和TypeScript编码规范
- 保持代码注释完整性
- 单元测试覆盖率不低于80%
- 提交前运行完整测试套件

### 提交格式
```
feat: 添加新功能
fix: 修复问题
docs: 更新文档
test: 添加测试
refactor: 代码重构
```

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 📞 联系方式

- **项目文档**: 详见各模块README.md
- **问题反馈**: 请在Issues中提交
- **技术讨论**: 欢迎Pull Request

---

## 🎯 项目亮点

✅ **完整的全栈应用** - 从需求到实现的完整开发流程  
✅ **现代化技术栈** - Java 21 + Spring Boot 3.0 + React 18  
✅ **专业级代码质量** - 完整测试覆盖和文档  
✅ **美观的用户界面** - 现代化设计和流畅交互  
✅ **生产就绪** - 完善的错误处理和性能优化  

**🎉 立即体验现代化的待办事项管理！**
