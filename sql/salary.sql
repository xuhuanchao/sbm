/*
Navicat MySQL Data Transfer

Source Server         : 本地mysql
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : test_sbm

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2020-06-18 00:49:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for salary
-- ----------------------------
DROP TABLE IF EXISTS `salary`;
CREATE TABLE `salary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` int(11) DEFAULT NULL,
  `payday` date DEFAULT NULL COMMENT '发放月份',
  `total_num` decimal(10,2) DEFAULT NULL COMMENT '工资数',
  `base_num` decimal(10,2) DEFAULT NULL COMMENT '基础工资',
  `achieve_num` decimal(10,2) DEFAULT NULL COMMENT '绩效工资',
  `achieve_percent` int(3) DEFAULT NULL COMMENT '绩效百分比',
  `deduct_num` decimal(10,2) DEFAULT NULL COMMENT '扣款',
  `bonus_num` decimal(10,2) DEFAULT NULL COMMENT '奖金',
  `deleted` tinyint(4) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8;
