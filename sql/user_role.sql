/*
Navicat MySQL Data Transfer

Source Server         : 本地mysql
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : test_sbm

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2020-06-18 00:50:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `role_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否被删除，1删除、0未删除',
  PRIMARY KEY (`role_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '0');
INSERT INTO `user_role` VALUES ('2', '2', '0');
INSERT INTO `user_role` VALUES ('3', '2', '0');
