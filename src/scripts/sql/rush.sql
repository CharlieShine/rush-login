/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : rush

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-01-26 16:25:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for rush_user
-- ----------------------------
DROP TABLE IF EXISTS `rush_user`;
CREATE TABLE `rush_user` (
  `id` bigint(20) unsigned NOT NULL,
  `insert_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `username` varchar(255) DEFAULT NULL,
  `password` char(32) DEFAULT NULL,
  `mobile` char(11) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `nick` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
