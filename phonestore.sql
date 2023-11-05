/*
 Navicat Premium Data Transfer

 Source Server         : ca
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : localhost:3306
 Source Schema         : phonestore

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 05/11/2023 22:32:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for business
-- ----------------------------
DROP TABLE IF EXISTS `business`;
CREATE TABLE `business`  (
  `storeId` int NOT NULL AUTO_INCREMENT COMMENT '商家的门牌号',
  `mobile` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商家的电话号码',
  `password` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nickName` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商家的昵称',
  `status` int NULL DEFAULT NULL COMMENT '该商家的可运行状态',
  `balance` double NULL DEFAULT NULL COMMENT '余额',
  `profit_money` double NULL DEFAULT NULL COMMENT '盈利的金额',
  `is_delete` int NULL DEFAULT NULL COMMENT '是否被禁用',
  PRIMARY KEY (`storeId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1005 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for consumer
-- ----------------------------
DROP TABLE IF EXISTS `consumer`;
CREATE TABLE `consumer`  (
  `consumerId` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户id',
  `password` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  `mobile` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `nickName` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `gender` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '性别',
  `gradeId` int NULL DEFAULT NULL COMMENT '星级',
  `addressId` int NULL DEFAULT NULL COMMENT '默认发送地址',
  `country` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '国家',
  `province` varchar(5) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '省份',
  `city` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '城市',
  `balance` double NULL DEFAULT NULL COMMENT '余额',
  `points` int NULL DEFAULT NULL COMMENT '积分',
  `consumeMoney` double NULL DEFAULT NULL COMMENT '消费额度',
  `createTime` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `lastLoginTime` datetime NULL DEFAULT NULL COMMENT '最后一次登录时间',
  `isDelete` int NULL DEFAULT NULL COMMENT '删除用户'
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `goodsId` int NULL DEFAULT NULL COMMENT '商品的编号',
  `goodsName` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品的名字',
  `goodsInitPrice` double NULL DEFAULT NULL COMMENT '商品的成本',
  `goodsSellPrice` double NULL DEFAULT NULL COMMENT '商品的出售价格',
  `goodsCategory` int NULL DEFAULT NULL COMMENT '商品的类别',
  `storeId` int NULL DEFAULT NULL COMMENT '商品的厂商',
  `currentStorage` int NULL DEFAULT NULL COMMENT '当前库存',
  `lowestStorage` int NULL DEFAULT NULL COMMENT '最低库存',
  `status` int NULL DEFAULT NULL COMMENT '商品的上下架控制 0： 下架   1： 上架',
  `createTime` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `imageUrl` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '照片的url',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `goods_id`(`goodsId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for goods_category
-- ----------------------------
DROP TABLE IF EXISTS `goods_category`;
CREATE TABLE `goods_category`  (
  `categoryId` int NULL DEFAULT NULL COMMENT '商品类别id',
  `name` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品名字',
  `parent_id` int NULL DEFAULT NULL COMMENT '商品类别的父级id',
  `status` int NULL DEFAULT NULL COMMENT '该类别商品是否可以上架',
  `imageUrl` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '存放照片路径',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  UNIQUE INDEX `category_id`(`categoryId`) USING BTREE,
  UNIQUE INDEX `sort`(`sort`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for goods_detail_image
-- ----------------------------
DROP TABLE IF EXISTS `goods_detail_image`;
CREATE TABLE `goods_detail_image`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` int NOT NULL COMMENT '保存的照片的等级， 分 1：2',
  `storeId` int NULL DEFAULT NULL COMMENT '商家id',
  `goodsId` int NULL DEFAULT NULL COMMENT '商品id',
  `imageUrl` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '照片的url',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade`  (
  `grade_id` int NULL DEFAULT NULL COMMENT '星级id',
  `name` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '星级的名字',
  `expendMoney` varchar(12) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '消费的金额',
  `discount` varchar(3) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '折扣'
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for manager
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager`  (
  `managerName` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '密码',
  `role` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '角色'
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `orderId` int NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `consumerId` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户的id',
  `orderPrice` varchar(12) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '订单价格',
  `goodsNum` int NULL DEFAULT NULL COMMENT '商品的数量',
  `storeId` int NULL DEFAULT NULL,
  `goodsId` int NULL DEFAULT NULL,
  `skuId` int NULL DEFAULT NULL,
  `payStatus` int NULL DEFAULT NULL COMMENT '支付状态',
  `payTime` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `createTime` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `isDelete` int NULL DEFAULT NULL COMMENT '是否被删除',
  `statusText` varchar(5) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '发货状态',
  PRIMARY KEY (`orderId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for sku
-- ----------------------------
DROP TABLE IF EXISTS `sku`;
CREATE TABLE `sku`  (
  `skuId` int NOT NULL AUTO_INCREMENT COMMENT 'sku编码',
  `goodsId` int NULL DEFAULT NULL COMMENT '商品id',
  `storeId` int NULL DEFAULT NULL COMMENT '商家id',
  `currentStorage` int NULL DEFAULT NULL COMMENT '当前库存',
  `lowestStorage` int NULL DEFAULT NULL COMMENT '最低库存',
  `createTime` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `status` int NULL DEFAULT NULL COMMENT '该规格的上下架控制 0： 下架   1： 上架',
  PRIMARY KEY (`skuId`) USING BTREE,
  INDEX `FK_goods_sku`(`goodsId`) USING BTREE,
  CONSTRAINT `FK_goods_sku` FOREIGN KEY (`goodsId`) REFERENCES `goods` (`goodsId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1001 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for skudetail
-- ----------------------------
DROP TABLE IF EXISTS `skudetail`;
CREATE TABLE `skudetail`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `skuInfoId` int NULL DEFAULT NULL COMMENT '单配件的id',
  `storeId` int NULL DEFAULT NULL COMMENT '商家id',
  `name` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '单配件的名称',
  `price` double NULL DEFAULT NULL COMMENT '价格',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_sku_skuInfo`(`skuInfoId`) USING BTREE,
  CONSTRAINT `FK_sku_skuInfo` FOREIGN KEY (`skuInfoId`) REFERENCES `sku` (`skuId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for yzm
-- ----------------------------
DROP TABLE IF EXISTS `yzm`;
CREATE TABLE `yzm`  (
  `pic_code` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `pic_key` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = COMPACT;

SET FOREIGN_KEY_CHECKS = 1;
