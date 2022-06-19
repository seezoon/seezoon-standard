# DDD 框架脚手架说明

当前应用架构设计领域有很多形式，但主要目的都是为了提升软件质量.

## 术语

- DDD：简称领域驱动设计，英文全称domain-driven
  design.[概念](https://zh.wikipedia.org/wiki/%E9%A0%98%E5%9F%9F%E9%A9%85%E5%8B%95%E8%A8%AD%E8%A8%88)
  , [实例体会](https://zhuanlan.zhihu.com/p/343388831)
- CQRS：命令查询责任分离，英文全称Command Query Responsibility
  Segregation.[https://zhuanlan.zhihu.com/p/115685384](https://zhuanlan.zhihu.com/p/115685384)
- COLA：整洁面向对象分层架构.[https://github.com/alibaba/COLA](https://github.com/alibaba/COLA)

## 软件架构演进模式

![img.png](doc/software_architecture_evolution.png)

## DDD 四层架构

![img.png](doc/ddd.png)

### 分层职责

```aidl
ddd-demo
├── conf # 项目执行打包后生成要发布的配置
├── ddd-demo-server # server程序
│   ├── application # 应用层:负责组合、流程编排、安全认证、权限校验、事务控制、发送或订阅领域事件等;
│   │   ├── convertor # 负责将应用层参数转换为持久化对象、或者实体、值对象
│   │   ├── dto # 应用层数据传输对象，存放接口出参和入参
│   │   │   └── clientobject 返回的出参
│   │   ├── event # 接收事件
│   │   ├── executor # 对应接口处理逻辑通常一个接口一个类
│   │   └── scheduler # 定时任务
│   ├── interfaces # 接口层:暴露协议，简单参数转换;
│   ├── domain # 领域层:实现企业核心业务逻辑，通过各种校验手段保证业务的正确性;
│   │   ├── entity # 实体，如果对实体抽象不好，还不如不抽象
│   │   ├── repository # 仓储（包装dao）
│   │   │   ├── mapper # mybatis mapper
│   │   │   └── po # 持久化对象和属性DB表对应
│   │   ├── service # 领域服务
│   │   └── valueobject # 值对象
│   └── infrastructure # 基础设施层:第三方工具、驱动、消息中间件、网关、文件、缓存以及数据库;
│       ├── configuration # 工程配置类
│       ├── error # 错误码
│       ├── exception # 异常处理
│       └── utils # 工具类
└── ddd-demo-stub # trpc 自动生成的桩代码
```

### 分层依赖

![img.png](doc/layer.png)

