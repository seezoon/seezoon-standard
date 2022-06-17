# 使用手册

生成DDD开发结构的目录结构

## 添加依赖

插件

```xml

<plugin>
    <groupId>com.seezoon.maven</groupId>
    <artifactId>seezoon-generator-maven-plugin</artifactId>
    <version>${latest}</version>
    <configuration>
        <!-- 按实际情况填写,如果担心配置污染代码可以去掉configuration采用命令行传参数-->
        <url>jdbc:mysql://127.0.0.1:3306/seezoon-stack</url>
        <username>root</username>
        <password>xxxx</password>
        <!-- tables下为空则生成全部 -->
        <tables>
            <table>sys_demo</table>
        </tables>
    </configuration>
</plugin>
```

## 生成

生成路径为`target/seezoon-generated`,可以多次生成。

- 可选择IDEA中执行maven 插件
  ![img.png](doc/idea-execute.png)
- 手动maven 命令
  `mvn seezoon-generator:generate`

> 命令行时候添加参数(-Dxxx=)时候，仅能覆盖configuration 中不存在的参数.

## 配置项

|  配置项   | 说明  |  默认值   |
|  ----  | ----  |  ----  | 
| seezoon.generate.db.url  | DB链接(必须) |无 |
| seezoon.generate.db.username  | DB账号 |root |
| seezoon.generate.db.password | DB密码 |无|
| seezoon.generate.db.tables | 生成表，多个逗号分隔，如果xml中配置了tables则无效 |无|
| seezoon.generate.db.table.prefix | 表前缀|无|
| seezoon.generate.db.field.prefix | 字段前缀|无|
| seezoon.generate.base.sqlmappper.path | sql文件根目录,如果自定义请注意mybatis的sql扫描路径，不建议改 |src/main/resources|
| seezoon.generate.base.repository.package | 仓储类根路径 |${project.groupId}.domain| |
seezoon.generate.base.controller.package | controller根路径 |${project.groupId}.interfaces|

## 使用约定

- 一定需要主键，如果有联合主键则取第一个，生成后手动修改
- 表名一定存在一个下划线`_`,通常最前面表示模块名，后面部分表示功能名，如果有所以自动会生成查询

## 表样例

其中id,status,create_by,create_time,update_by,update_time,remarks为推荐的默认值，可以少。主键也不一定叫id.
默认值的意义在于生成可以自动处理创建时间，更新时间这些。

```sql
CREATE TABLE `sys_demo`
(
    `id`          int          NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`        varchar(50)  NOT NULL COMMENT '名称',
    `param_key`   varchar(50)  NOT NULL COMMENT '键',
    `param_value` varchar(100) NOT NULL COMMENT '值',
    `status`      tinyint      NOT NULL COMMENT '状态',
    `create_by`   int          NOT NULL COMMENT '创建者',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    `update_by`   int          NOT NULL COMMENT '更新者',
    `update_time` datetime     NOT NULL COMMENT '更新时间',
    `remarks`     varchar(255) DEFAULT NULL COMMENT '备注信息',
    PRIMARY KEY (`id`),
    UNIQUE
        KEY `param_key` (`param_key`) USING BTREE,
    KEY `create_by` (`create_by`),
    KEY `create_date` (`create_time`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='系统参数';

```