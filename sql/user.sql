/*
Navicat MySQL Data Transfer

Source Server         : 本地mysql
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : test_sbm

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2020-06-18 00:50:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_name` varchar(20) DEFAULT NULL COMMENT '账户名',
  `password` varchar(100) DEFAULT NULL,
  `user_name` varchar(100) DEFAULT NULL COMMENT '用户名',
  `create_time` datetime NOT NULL,
  `role_id` varchar(255) DEFAULT NULL COMMENT '拥有的角色id，多个用逗号分隔',
  `deleted` tinyint(4) NOT NULL COMMENT '是否被删除，1删除、0未删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_user_accountName` (`account_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '$2a$10$5vCGeN9r0.WXr/JGJrSJy.qUqD9gDoPyqPWLE9A6NdUd.RTiR5hm2', '超管', '2020-04-14 17:18:38', '1', '0');
INSERT INTO `user` VALUES ('2', 'test', '$2a$10$5vCGeN9r0.WXr/JGJrSJy.qUqD9gDoPyqPWLE9A6NdUd.RTiR5hm2', ' 测试用户1', '2020-04-23 15:19:57', null, '0');
