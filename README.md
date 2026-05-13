# RTMS — 乡村外卖管理系统

> Rural Takeout Management System · 毕业设计  
> 基于 Spring Boot + Vue 3 + 微信小程序的完整外卖平台解决方案

[![Java](https://img.shields.io/badge/Java-17-ED8B00.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F.svg)](https://spring.io/projects/spring-boot)
[![Vue](https://img.shields.io/badge/Vue-3.x-4FC08D.svg)](https://vuejs.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.x-4479A1.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

---

## 项目简介

RTMS 是一套面向乡镇场景的外卖配送管理系统，覆盖**顾客下单 → 商家接单 → 骑手配送 → 评价投诉**的完整业务闭环。系统包含 Web 管理后台、顾客/商家端、微信小程序骑手端三大前端，角色涵盖管理员、顾客、商家、骑手。

- **总代码量**：约 22,000 行
- **数据库表**：13 张
- **API 接口**：50+ 个 RESTful 接口

---

## 技术栈

| 层级     | 技术                               |
| -------- | ---------------------------------- |
| 后端框架 | Spring Boot 3、MyBatis-Plus        |
| 数据库   | MySQL 8.x                          |
| API 文档 | SpringDoc OpenAPI + Knife4j        |
| Web 前端 | Vue 3 + Pinia + Vue Router + Axios |
| UI 框架  | Ant Design Vue + Element Plus      |
| 小程序   | 微信小程序 (uni-app)               |
| 构建工具 | Vite、Maven                        |

---

## 系统架构

```
┌─────────────────────────────────────────────────┐
│                    前端层                         │
│  Web 管理后台  │  商家/顾客端  │  骑手微信小程序   │
│   (Vue 3)     │   (Vue 3)    │    (uni-app)     │
├─────────────────────────────────────────────────┤
│                  RESTful API                     │
│              Spring Boot + MVC                   │
├─────────────────────────────────────────────────┤
│                  业务服务层                       │
│  账号管理 │ 店铺管理 │ 订单管理 │ 评价投诉        │
│  产品管理 │ 骑手管理 │ 地址管理 │ 系统配置        │
├─────────────────────────────────────────────────┤
│                  MyBatis-Plus                    │
│                     MySQL                        │
└─────────────────────────────────────────────────┘
```

## 角色体系

| 角色       | 端         | 核心功能                                                |
| ---------- | ---------- | ------------------------------------------------------- |
| **管理员** | Web        | 用户管理、商家审核、骑手审核、投诉处理、系统配置        |
| **商家**   | Web        | 店铺管理、分类/产品/规格管理、订单接单出餐、经营统计    |
| **顾客**   | Web        | 注册登录、浏览店铺/产品、下单支付、评价投诉             |
| **骑手**   | 微信小程序 | 接单大厅抢单、配送中/已完成订单、实时位置上报、收入统计 |

## 订单状态流转

```
待支付 ──支付──→ 已支付 ──商家接单──→ 已接单 ──商家出餐──→ 待配送
  │                                                         │
  └──── 取消 (可退款) ──────────────────────────────── 骑手接单 ↓
                                                         配送中
                                                           │
                                                     骑手送达 ↓
                                                         已送达
                                                           │
                                                     顾客确认 ↓
                                                         已完成
```

---

## 项目结构

```
rtms/
├── rtms-back/                    # Spring Boot 后端
│   ├── rtms-common/              #   公共模块（常量、枚举、DTO、工具）
│   ├── rtms-backend/             #   主业务模块
│   │   ├── config/               #     CORS、Swagger 配置
│   │   ├── controller/           #     14 个 REST 控制器
│   │   ├── mapper/               #     MyBatis-Plus Mapper
│   │   ├── pojo/entity/          #     13 个数据库实体
│   │   └── service/impl/         #     13 个服务实现
│   └── rtms-frontend/            #   空壳模块
│
├── rtms-frontend-web/            # Vue 3 前端
│   └── src/
│       ├── api/                  #   API 请求封装
│       ├── components/           #   组件（admin/store/customer/index/...）
│       ├── router/               #   路由配置 + 权限守卫
│       ├── stores/               #   Pinia 状态管理
│       └── views/                #   页面视图
│
└── redir-weixin/                 # 微信小程序（骑手端）
    ├── pages/
    │   ├── index/                #   待接单大厅
    │   ├── login/                #   骑手登录
    │   ├── order/                #   订单列表
    │   ├── order-detail/         #   订单详情
    │   └── my/                   #   我的
    └── stores/                   #   状态管理
```

---

## 快速开始

### 环境要求

- **JDK** 17+
- **Node.js** 18+
- **MySQL** 8.x
- **Maven** 3.8+

### 1. 数据库

```sql
CREATE DATABASE db_rtms DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

执行项目中的 SQL 初始化脚本建表。

### 2. 后端启动

```bash
cd rtms-back
# 修改 rtms-backend/src/main/resources/application.yaml 中的数据库连接信息
mvn clean install -DskipTests
mvn spring-boot:run -pl rtms-backend
```

后端默认运行在 `http://localhost:8090`，API 文档地址 `http://localhost:8090/swagger.html`。

### 3. Web 前端启动

```bash
cd rtms-frontend-web
npm install
npm run dev
```

Web 前端默认运行在 `http://localhost:8080`。

### 4. 微信小程序

使用微信开发者工具导入 `redir-weixin/` 目录，修改 `config.js` 中的 API 地址。

---

## 数据库设计要点

- **统一账号模型**：四种角色共用 `tb_account`，通过 `role` 字段区分
- **金额存分（BIGINT）**：避免浮点精度问题
- **订单快照机制**：下单时产品名称、价格、规格写入订单明细，防止商家改价后历史数据异常
- **软删除机制**：所有表都有 `status` 字段支持软删除，订单支持多方独立标记删除
- **灵活规格系统**：type + name 二维结构，可扩展
- **系统配置 KV 化**：`tb_system` 键值对，动态配置无需改代码
- **骑手位置追踪**：`tb_rider_location` 按时间序存储位置点

---

## 核心 API 一览

| 模块      | 主要接口                                                     |
| --------- | ------------------------------------------------------------ |
| 账号      | 注册、登录、注销、列表、封禁/解封、充值                      |
| 订单      | 创建、支付、商家接单/出餐、骑手接单/送达、顾客确认完成、取消退款 |
| 店铺      | 申请开店、列表、按类型筛选、审核、更新                       |
| 产品      | 增删改查、分类管理、规格管理                                 |
| 评价/投诉 | 提交评价、提交投诉、投诉处理                                 |
| 骑手      | 注册、列表、位置上报、今日/本月收入统计                      |

---

## 作者

**石海良 (Shi Hailiang)**

---

## License

MIT License — 仅供参考学习，请勿用于商业用途。
