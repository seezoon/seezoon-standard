# 企业级Java 脚手架

## 说明

在企业内部，大量Java工程建立，需要有一定的规范及标准。制定属于企业内部自己的脚手架，可以提高开发效率，促进代码review，让团队成员有一致的开发思想的共识。为日后快速迭代升级打下坚实的基础。

## 工程规划

- **统一父POM**：统一maven仓库，公共依赖清单，打包形式等，产出物规范等；
- **统一运维脚本**：运维脚本统一维护，通过打包时候可以完成升级；
- **统一工程结构**：采用maven多模块的项目结构，采用DDD的思想对代码职责分层；
- **统一基础框架**：以spring boot为基础，内部微服务调用采用grpc，http服务采用spring mvc；
- **统一代码风格**：使用统一的code style插件及规范；

## 项目结构

项目主体目录布局介绍

```
seezoon-standard  父工程
├── bom 管理企业内部开源框架依赖
├── samples 编写适合企业的样例程序，通过该程序，也可以制作脚手架（Maven Archetype)
├── seezoon 企业级父POM
├── seezoon-generator-maven-plugin 代码生成，仓储、dao代码生成
├── starters 企业可以制定自己的starters
│   ├── ddd-spring-boot-starter ddd 基础对象、异常、错误定义等
│   └── mybatis-spring-boot-starter 对mybatis的基础封装，如果不喜欢可以换成mybatis plus
├── tools  工具
│   ├── code 代码相关工具  
│   │   └── eclipse-codestyle.xml 代码格式化
│   ├── maintain_script  运维脚本
│   └── maven
│       └── settings.xml 制定全员通用的文件
└── pom.xml 包含基础maven仓库，部署仓库等
```

