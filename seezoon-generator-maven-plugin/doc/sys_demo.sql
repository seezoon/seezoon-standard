/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : seezoon-stack

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 23/01/2022 00:28:25
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_demo
-- ----------------------------
DROP TABLE IF EXISTS `sys_demo`;
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
    UNIQUE KEY `param_key` (`param_key`) USING BTREE,
    KEY           `create_by` (`create_by`),
    KEY           `create_date` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统参数';

SET
FOREIGN_KEY_CHECKS = 1;
