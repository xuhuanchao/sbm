/*
Navicat MySQL Data Transfer

Source Server         : 本地mysql
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : test_sbm

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2020-06-18 00:49:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `rolename` varchar(50) DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否被删除，1删除、0未删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_role_name` (`rolename`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'admin', '超管', '0');
INSERT INTO `role` VALUES ('2', 'user', '普通用户', '0');
INSERT INTO `role` VALUES ('3', 'dba', '数据库管理员', '0');
INSERT INTO `role` VALUES ('4', 'guest', '访客', '0');
