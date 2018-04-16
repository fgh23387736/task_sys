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

 Date: 03/12/2018 11:08:20 AM
*/

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
--  Table structure for `ts_chart_record`
-- ----------------------------
DROP TABLE IF EXISTS `ts_chart_record`;
CREATE TABLE `ts_chart_record` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `send_user_id` int(8) NOT NULL,
  `receive_user_id` int(8) NOT NULL,
  `content` varchar(255) NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `send_user_id` (`send_user_id`),
  KEY `receive_user_id` (`receive_user_id`),
  CONSTRAINT `ts_chart_record_ibfk_1` FOREIGN KEY (`send_user_id`) REFERENCES `ts_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ts_chart_record_ibfk_2` FOREIGN KEY (`receive_user_id`) REFERENCES `ts_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `release_is_confirm` tinyint(1) DEFAULT NULL,
  `receive_is_confirm` tinyint(1) DEFAULT NULL,
  `release_time` datetime NOT NULL,
  `receive_time` datetime DEFAULT NULL,
  `release_confirm_time` datetime NOT NULL,
  `receive_confirm_time` datetime DEFAULT NULL,
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
