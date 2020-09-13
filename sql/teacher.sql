/*
Navicat MySQL Data Transfer

Source Server         : 本地mysql
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : test_sbm

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2020-06-18 00:49:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `sex` tinyint(1) DEFAULT '1' COMMENT '1男，2女',
  `age` int(3) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '1已删除，0未删除',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
