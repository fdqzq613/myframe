/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3396
 Source Server Type    : MySQL
 Source Server Version : 100400
 Source Host           : localhost:3396
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 100400
 File Encoding         : 65001

 Date: 27/05/2020 13:49:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_kc
-- ----------------------------
DROP TABLE IF EXISTS `t_kc`;
CREATE TABLE `t_kc`  (
  `id` bigint(20) NOT NULL,
  `goods_no` bigint(20) NULL DEFAULT NULL COMMENT '商品编码',
  `num` decimal(10, 1) NULL DEFAULT NULL COMMENT '库存最终数量',
  `lock_num` decimal(10, 1) NULL DEFAULT NULL COMMENT '锁定库存数量-订单生成未支付时',
  `create_userid` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_userid` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_kc_log
-- ----------------------------
DROP TABLE IF EXISTS `t_kc_log`;
CREATE TABLE `t_kc_log`  (
  `id` bigint(20) NOT NULL,
  `order_no` bigint(20) NULL DEFAULT NULL COMMENT '订单号',
  `goods_no` bigint(20) NULL DEFAULT NULL COMMENT '商品编码',
  `num` int(11) NULL DEFAULT NULL COMMENT '数量',
  `op_type` tinyint(4) NULL DEFAULT NULL COMMENT '操作字段类型；1锁定库存数；2最终库存数',
  `kc_type` tinyint(4) NULL DEFAULT NULL COMMENT '操作库存类型；1加库存；0减库存',
  `create_userid` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_userid` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` bigint(20) NOT NULL,
  `order_no` bigint(20) NULL DEFAULT NULL COMMENT '订单号',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '订单状态',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '金额',
  `create_userid` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_userid` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail`;
CREATE TABLE `t_order_detail`  (
  `id` bigint(20) NOT NULL,
  `order_no` bigint(20) NULL DEFAULT NULL COMMENT '订单号',
  `goods_no` bigint(20) NULL DEFAULT NULL COMMENT '商品编码',
  `num` int(11) NULL DEFAULT NULL COMMENT '数量',
  `create_userid` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_userid` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
