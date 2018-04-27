/*
 Navicat Premium Data Transfer

 Source Server         : mysql-localhost
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost
 Source Database       : task_system

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : utf-8

 Date: 04/27/2018 10:49:41 AM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `ts_admin`
-- ----------------------------
DROP TABLE IF EXISTS `ts_admin`;
CREATE TABLE `ts_admin` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `username` varchar(25) NOT NULL,
  `password` varchar(32) NOT NULL,
  `name` varchar(25) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `head_img` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `ts_admin`
-- ----------------------------
BEGIN;
INSERT INTO `ts_admin` VALUES ('1', 'test', 'fb469d7ef430b0baf0cab6c436e70375', '管理员1', '17862700650', null);
COMMIT;

-- ----------------------------
--  Table structure for `ts_cancel_apply`
-- ----------------------------
DROP TABLE IF EXISTS `ts_cancel_apply`;
CREATE TABLE `ts_cancel_apply` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `task_id` int(8) NOT NULL,
  `apply_type` int(1) NOT NULL,
  `reason` varchar(255) NOT NULL,
  `type` int(1) NOT NULL,
  `deal_reason` varchar(255) DEFAULT NULL,
  `time` datetime NOT NULL,
  `deal_time` datetime DEFAULT NULL,
  `deal_type` int(1) DEFAULT NULL,
  `admin_id` int(8) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `task_id` (`task_id`),
  KEY `admin_id` (`admin_id`),
  CONSTRAINT `ts_cancel_apply_ibfk_1` FOREIGN KEY (`task_id`) REFERENCES `ts_task` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ts_cancel_apply_ibfk_2` FOREIGN KEY (`admin_id`) REFERENCES `ts_admin` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `ts_chat_record`
-- ----------------------------
DROP TABLE IF EXISTS `ts_chat_record`;
CREATE TABLE `ts_chat_record` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `send_user_id` int(8) NOT NULL,
  `receive_user_id` int(8) NOT NULL,
  `content` varchar(255) NOT NULL,
  `time` datetime NOT NULL,
  `is_read` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `send_user_id` (`send_user_id`),
  KEY `receive_user_id` (`receive_user_id`),
  CONSTRAINT `ts_chat_record_ibfk_1` FOREIGN KEY (`send_user_id`) REFERENCES `ts_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ts_chat_record_ibfk_2` FOREIGN KEY (`receive_user_id`) REFERENCES `ts_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `ts_envaluate`
-- ----------------------------
DROP TABLE IF EXISTS `ts_envaluate`;
CREATE TABLE `ts_envaluate` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `envaluate_user_id` int(8) NOT NULL,
  `be_envaluated_user_id` int(8) NOT NULL,
  `task_id` int(8) NOT NULL,
  `type` int(1) NOT NULL,
  `content` varchar(255) NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `envaluate_user_id` (`envaluate_user_id`),
  KEY `be_envaluated_user_id` (`be_envaluated_user_id`),
  KEY `task_id` (`task_id`),
  CONSTRAINT `ts_envaluate_ibfk_1` FOREIGN KEY (`envaluate_user_id`) REFERENCES `ts_user` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `ts_envaluate_ibfk_2` FOREIGN KEY (`be_envaluated_user_id`) REFERENCES `ts_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ts_envaluate_ibfk_3` FOREIGN KEY (`task_id`) REFERENCES `ts_task` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `ts_internal_transfer_record`
-- ----------------------------
DROP TABLE IF EXISTS `ts_internal_transfer_record`;
CREATE TABLE `ts_internal_transfer_record` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `transaction_user_id` int(8) NOT NULL,
  `type` int(1) NOT NULL,
  `money` decimal(10,2) NOT NULL,
  `time` datetime NOT NULL,
  `task_id` int(8) NOT NULL,
  `reason` int(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `transaction_user_id` (`transaction_user_id`),
  KEY `transaction_user_id_2` (`transaction_user_id`),
  KEY `task_id` (`task_id`),
  CONSTRAINT `ts_internal_transfer_record_ibfk_1` FOREIGN KEY (`transaction_user_id`) REFERENCES `ts_user` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `ts_internal_transfer_record_ibfk_2` FOREIGN KEY (`task_id`) REFERENCES `ts_task` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `ts_notice`
-- ----------------------------
DROP TABLE IF EXISTS `ts_notice`;
CREATE TABLE `ts_notice` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `admin_id` int(8) NOT NULL,
  `content` text NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `admin_id` (`admin_id`),
  CONSTRAINT `ts_notice_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `ts_admin` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `ts_private_message`
-- ----------------------------
DROP TABLE IF EXISTS `ts_private_message`;
CREATE TABLE `ts_private_message` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `admin_id` int(8) NOT NULL,
  `user_id` int(8) NOT NULL,
  `content` varchar(255) NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `admin_id` (`admin_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `ts_private_message_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `ts_admin` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `ts_private_message_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `ts_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `ts_suspended_record`
-- ----------------------------
DROP TABLE IF EXISTS `ts_suspended_record`;
CREATE TABLE `ts_suspended_record` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `admin_id` int(8) NOT NULL,
  `user_id` int(8) NOT NULL,
  `reason` varchar(255) NOT NULL,
  `time` datetime NOT NULL,
  `suspended_deadline` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `admin_id` (`admin_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `ts_suspended_record_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `ts_admin` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `ts_suspended_record_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `ts_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `ts_system_transfer_record`
-- ----------------------------
DROP TABLE IF EXISTS `ts_system_transfer_record`;
CREATE TABLE `ts_system_transfer_record` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `transaction_user_id` int(8) NOT NULL,
  `type` int(1) NOT NULL,
  `money` decimal(10,2) NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `transaction_user_id` (`transaction_user_id`),
  KEY `transaction_user_id_2` (`transaction_user_id`),
  CONSTRAINT `ts_system_transfer_record_ibfk_1` FOREIGN KEY (`transaction_user_id`) REFERENCES `ts_user` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `ts_system_transfer_record`
-- ----------------------------
BEGIN;
INSERT INTO `ts_system_transfer_record` VALUES ('1', '1', '0', '200.00', '2018-04-17 11:59:13'), ('2', '1', '0', '20.00', '2018-04-17 23:06:04'), ('3', '4', '0', '30.00', '2018-04-18 01:14:23'), ('4', '4', '0', '1000.00', '2018-04-18 01:33:57'), ('5', '2', '0', '100.00', '2018-04-18 08:36:25'), ('6', '2', '0', '100.00', '2018-04-18 10:47:21'), ('7', '1', '0', '100.00', '2018-04-20 11:16:07'), ('8', '3', '0', '100.00', '2018-04-26 11:44:53'), ('9', '1', '0', '1000.00', '2018-04-26 15:53:09');
COMMIT;

-- ----------------------------
--  Table structure for `ts_task`
-- ----------------------------
DROP TABLE IF EXISTS `ts_task`;
CREATE TABLE `ts_task` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `price` decimal(10,2) NOT NULL,
  `content` text NOT NULL,
  `name` varchar(25) DEFAULT NULL,
  `address` varchar(255) NOT NULL,
  `release_user_id` int(8) NOT NULL,
  `receive_user_id` int(8) DEFAULT NULL,
  `state` int(1) NOT NULL,
  `release_is_confirm` tinyint(1) unsigned zerofill NOT NULL,
  `receive_is_confirm` tinyint(1) unsigned zerofill NOT NULL,
  `release_time` datetime NOT NULL,
  `receive_time` datetime DEFAULT NULL,
  `release_confirm_time` datetime DEFAULT NULL,
  `receive_confirm_time` datetime DEFAULT NULL,
  `release_is_envaluate` tinyint(1) unsigned zerofill NOT NULL,
  `receive_is_envaluate` tinyint(1) unsigned zerofill NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ts_task_ibfk_1` (`release_user_id`),
  KEY `ts_task_ibfk_2` (`receive_user_id`),
  CONSTRAINT `ts_task_ibfk_1` FOREIGN KEY (`release_user_id`) REFERENCES `ts_user` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `ts_task_ibfk_2` FOREIGN KEY (`receive_user_id`) REFERENCES `ts_user` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `ts_user`
-- ----------------------------
DROP TABLE IF EXISTS `ts_user`;
CREATE TABLE `ts_user` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `username` varchar(25) NOT NULL,
  `password` varchar(32) NOT NULL,
  `name` varchar(25) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `release_task_number` int(10) NOT NULL DEFAULT '0',
  `receive_task_number` int(11) NOT NULL DEFAULT '0',
  `suspended_deadline` datetime DEFAULT NULL,
  `money` decimal(10,2) NOT NULL DEFAULT '0.00',
  `good_envaluate_number` int(10) NOT NULL DEFAULT '0',
  `middle_envaluate_number` int(10) NOT NULL DEFAULT '0',
  `bad_envaluate_number` int(10) NOT NULL DEFAULT '0',
  `head_img` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `ts_user`
-- ----------------------------
BEGIN;
INSERT INTO `ts_user` VALUES ('1', 'test', 'fb469d7ef430b0baf0cab6c436e70375', '冯鈜鉝', '17862700650', '1', '12', null, '1354.00', '2', '1', '2', '/task_sys/upload/headImg/15245540754058.jpeg'), ('2', 'test3', 'fb469d7ef430b0baf0cab6c436e70375', '无名氏1', '17862700651', '13', '0', null, '191.00', '0', '0', '0', '/task_sys/assets/images/100.jpg'), ('3', 'test4', 'fb469d7ef430b0baf0cab6c436e70375', '无名氏', null, '1', '1', null, '99.00', '0', '0', '0', '/task_sys/assets/images/100.jpg'), ('4', 'test5', 'fb469d7ef430b0baf0cab6c436e70375', '无名氏', null, '5', '0', null, '980.00', '1', '0', '0', '/task_sys/assets/images/100.jpg');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
