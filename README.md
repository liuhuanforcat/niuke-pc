# 牛客社区论坛系统

## 工程简介

牛客社区论坛系统是一个基于 Spring Boot 开发的社区交流平台，类似于牛客网。系统提供了完整的用户管理、帖子发布、评论互动、点赞关注、私信通知、全文搜索等核心功能，支持用户注册登录、邮件激活、敏感词过滤、数据统计等特性。

## 技术栈

### 后端技术
- **框架**: Spring Boot 2.3.7
- **持久层**: MyBatis 2.1.4
- **数据库**: MySQL 8.0
- **缓存**: Redis (Spring Data Redis)
- **搜索引擎**: Elasticsearch 7.x
- **消息队列**: Apache Kafka
- **安全框架**: Spring Security
- **模板引擎**: Thymeleaf
- **邮件服务**: Spring Mail
- **验证码**: Kaptcha 2.3.2
- **工具库**: 
  - Lombok
  - FastJSON 1.2.78
  - Apache Commons Lang3 3.9

### 前端技术
- **模板引擎**: Thymeleaf
- **样式**: CSS3
- **脚本**: JavaScript

## 核心功能模块

### 1. 用户管理模块
- 用户注册（邮箱验证）
- 用户登录（验证码、记住我）
- 邮件激活账户
- 忘记密码（邮件找回）
- 个人资料管理
- 头像上传
- 权限管理（普通用户、版主、管理员）

### 2. 帖子管理模块
- 发布帖子
- 查看帖子详情
- 帖子列表（分页）
- 帖子置顶
- 帖子加精
- 帖子删除
- 帖子搜索（Elasticsearch）

### 3. 评论系统模块
- 评论帖子
- 回复评论
- 评论列表（分页）
- 评论点赞

### 4. 互动功能模块
- 点赞功能（帖子、评论）
- 关注/取消关注用户
- 粉丝列表
- 关注列表

### 5. 消息系统模块
- 私信发送
- 私信列表
- 系统通知（评论、点赞、关注）
- 未读消息统计

### 6. 搜索功能模块
- 全文搜索（Elasticsearch）
- 搜索结果高亮
- 搜索结果分页

### 7. 数据统计模块
- UV（独立访客）统计
- DAU（日活跃用户）统计
- 数据可视化展示

### 8. 安全功能模块
- 敏感词过滤
- 登录拦截器
- 权限拦截器
- 异常统一处理

## 项目结构

```
niuke/
├── src/main/java/com/niukedemo/
│   ├── annotation/          # 自定义注解
│   │   └── LoginRequired.java
│   ├── aspect/              # AOP切面
│   │   └── ServiceAspect.java
│   ├── config/              # 配置类
│   │   ├── EsConfig.java           # Elasticsearch配置
│   │   ├── KaptchaConfig.java      # 验证码配置
│   │   ├── RedisConfig.java        # Redis配置
│   │   ├── SecurityConfig.java     # 安全配置
│   │   └── WebMvcConfig.java       # Web MVC配置
│   ├── controller/          # 控制器层
│   │   ├── advice/          # 异常处理
│   │   ├── interceptor/     # 拦截器
│   │   ├── CommentController.java      # 评论控制器
│   │   ├── DataController.java         # 数据统计控制器
│   │   ├── DiscussPostController.java  # 帖子控制器
│   │   ├── FollowController.java      # 关注控制器
│   │   ├── HomeController.java        # 首页控制器
│   │   ├── LikeController.java        # 点赞控制器
│   │   ├── LoginController.java       # 登录控制器
│   │   ├── MessageController.java     # 消息控制器
│   │   ├── SearchController.java      # 搜索控制器
│   │   └── UserController.java        # 用户控制器
│   ├── entity/              # 实体类
│   │   ├── Comment.java
│   │   ├── DiscussPost.java
│   │   ├── Event.java
│   │   ├── LoginTicket.java
│   │   ├── Message.java
│   │   ├── Page.java
│   │   └── User.java
│   ├── event/               # 事件处理
│   │   ├── EventConsumer.java    # 事件消费者
│   │   └── EventProducer.java    # 事件生产者
│   ├── mapper/              # 数据访问层
│   │   ├── elasticsearch/   # Elasticsearch Repository
│   │   ├── CommentMapper.java
│   │   ├── DiscussPostMapper.java
│   │   ├── LoginTicketMapper.java
│   │   ├── MessageMapper.java
│   │   └── UserMapper.java
│   ├── service/             # 服务层
│   │   ├── CommentService.java
│   │   ├── DataService.java
│   │   ├── DiscussPostService.java
│   │   ├── ElasticsearchService.java
│   │   ├── FollowService.java
│   │   ├── LikeService.java
│   │   ├── MessageService.java
│   │   └── UserService.java
│   ├── util/                # 工具类
│   │   ├── CommunityConstant.java    # 常量定义
│   │   ├── CommunityUtil.java        # 通用工具
│   │   ├── CookieUtil.java          # Cookie工具
│   │   ├── HostHolder.java          # 用户持有者
│   │   ├── MailClient.java          # 邮件客户端
│   │   ├── RedisKeyUtil.java        # Redis键工具
│   │   └── SensitiveFilter.java     # 敏感词过滤
│   └── NiukeApplication.java        # 启动类
├── src/main/resources/
│   ├── mapper/              # MyBatis映射文件
│   ├── static/              # 静态资源
│   │   ├── css/            # 样式文件
│   │   ├── js/             # JavaScript文件
│   │   └── img/            # 图片资源
│   ├── templates/           # 模板文件
│   │   ├── error/          # 错误页面
│   │   ├── mail/           # 邮件模板
│   │   └── site/           # 站点页面
│   ├── application.yml      # 应用配置
│   └── sensitive-words.txt  # 敏感词库
└── pom.xml                  # Maven配置

```

## 环境要求

- **JDK**: 1.8+
- **Maven**: 3.6+
- **MySQL**: 5.7+ 或 8.0+
- **Redis**: 5.0+
- **Elasticsearch**: 7.x
- **Kafka**: 2.8+ (可选，用于异步消息处理)

## 快速开始

### 1. 克隆项目
```bash
git clone <repository-url>
cd niuke
```

### 2. 数据库配置
创建数据库 `newcoder`，并执行相应的建表SQL脚本。

### 3. 修改配置
编辑 `src/main/resources/application.yml`，配置以下内容：
- MySQL数据库连接信息
- Redis连接信息
- Elasticsearch连接信息
- Kafka连接信息（如使用）
- 邮件服务配置（SMTP）
- 文件上传路径

### 4. 启动依赖服务
确保以下服务已启动：
- MySQL服务
- Redis服务
- Elasticsearch服务
- Kafka服务（如使用）

### 5. 运行项目
```bash
# 使用Maven运行
mvn spring-boot:run

# 或使用Maven Wrapper
./mvnw spring-boot:run  # Linux/Mac
mvnw.cmd spring-boot:run  # Windows
```

### 6. 访问系统
浏览器访问：`http://localhost:8080/community`

## 配置说明

### application.yml 主要配置项

```yaml
# 数据库配置
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/newcoder?serverTimezone=UTC&characterEncoding=utf-8
    username: root
    password: 123456

# Redis配置
  redis:
    host: 192.168.241.132
    port: 6379
    database: 0

# Elasticsearch配置
  elasticsearch:
    rest:
      uris: 127.0.0.1:9200

# Kafka配置
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: test-consumer-group

# 邮件配置
  mail:
    host: smtp.qq.com
    port: 587
    username: your-email@qq.com
    password: your-email-password

# 自定义配置
community:
  path:
    domain: http://localhost:8080
    upload: E:\img  # 文件上传路径

# 服务器配置
server:
  servlet:
    context-path: /community
```

## 核心功能说明

### 1. 用户认证与授权
- 使用 Spring Security 进行安全控制
- 基于 Ticket 的登录凭证管理
- 支持"记住我"功能（12小时/100天）
- 自定义拦截器实现权限控制

### 2. 异步消息处理
- 使用 Kafka 实现异步事件处理
- 支持评论、点赞、关注等事件通知
- 解耦业务逻辑，提升系统性能

### 3. 全文搜索
- 集成 Elasticsearch 实现全文搜索
- 支持中文分词（IK Analyzer）
- 实时同步帖子数据到搜索引擎

### 4. 缓存优化
- Redis 缓存热点数据
- 点赞数据存储在 Redis
- 关注关系缓存优化

### 5. 敏感词过滤
- 基于前缀树（Trie）算法
- 支持敏感词替换
- 可配置敏感词库

### 6. 数据统计
- 使用 Redis HyperLogLog 统计 UV
- 使用 Redis Bitmap 统计 DAU
- 提供数据可视化展示

## 数据库设计

### 主要数据表
- **user**: 用户表
- **discuss_post**: 帖子表
- **comment**: 评论表
- **message**: 消息表
- **login_ticket**: 登录凭证表
- **follow**: 关注关系表

### 实体关系
- 用户与帖子：一对多
- 用户与评论：一对多
- 帖子与评论：一对多
- 评论与回复：一对多（自关联）
- 用户与用户：多对多（关注关系）

## 权限说明

系统支持三种用户角色：
- **普通用户 (user)**: 基础功能权限
- **版主 (moderator)**: 可置顶、加精帖子
- **管理员 (admin)**: 所有权限，包括删除帖子

## 注意事项

1. **邮件配置**: 需要配置有效的SMTP邮箱账号和授权码
2. **文件上传**: 确保配置的文件上传路径存在且有写权限
3. **Elasticsearch**: 需要安装IK分词器插件
4. **Redis**: 确保Redis服务正常运行
5. **敏感词库**: 可根据需要更新 `sensitive-words.txt`

## 延伸阅读

### 技术文档
- [Spring Boot 官方文档](https://spring.io/projects/spring-boot)
- [MyBatis 官方文档](https://mybatis.org/mybatis-3/)
- [Elasticsearch 官方文档](https://www.elastic.co/guide/en/elasticsearch/reference/current/index.html)
- [Redis 官方文档](https://redis.io/documentation)
- [Kafka 官方文档](https://kafka.apache.org/documentation/)

### 相关资源
- Spring Security 安全框架学习
- Thymeleaf 模板引擎使用
- 分布式系统设计
- 高并发系统优化

### 后续优化方向
- 引入分布式Session（Redis Session）
- 实现分布式锁（Redis/Zookeeper）
- 添加消息队列削峰填谷
- 实现CDN加速静态资源
- 引入缓存预热机制
- 实现读写分离
- 添加监控和日志系统

