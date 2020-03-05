/*
SQLyog Ultimate v12.4.1 (64 bit)
MySQL - 5.6.43 : Database - content_center
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`content_center` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `content_center`;

/*Table structure for table `mid_user_share` */

DROP TABLE IF EXISTS `mid_user_share`;

CREATE TABLE `mid_user_share` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `share_id` int(11) NOT NULL COMMENT 'share.id',
  `user_id` int(11) NOT NULL COMMENT 'user.id',
  PRIMARY KEY (`id`),
  KEY `fk_mid_user_share_share1_idx` (`share_id`),
  KEY `fk_mid_user_share_user1_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='用户-分享中间表【描述用户购买的分享】';

/*Data for the table `mid_user_share` */

/*Table structure for table `notice` */

DROP TABLE IF EXISTS `notice`;

CREATE TABLE `notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `content` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '内容',
  `show_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否显示 0:否 1:是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `notice` */

/*Table structure for table `rocketmq_transaction_log` */

DROP TABLE IF EXISTS `rocketmq_transaction_log`;

CREATE TABLE `rocketmq_transaction_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `transaction_Id` varchar(45) NOT NULL COMMENT '事务id',
  `log` varchar(45) NOT NULL COMMENT '日志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='RocketMQ事务日志表';

/*Data for the table `rocketmq_transaction_log` */

insert  into `rocketmq_transaction_log`(`id`,`transaction_Id`,`log`) values 
(1,'612e2e57-cde0-4152-acd4-6c073a0b0abb','审核分享...'),
(2,'c94bb3dc-5864-4821-9d24-bc8ff98b3c2c','审核分享...'),
(3,'cc9e5c19-bed1-4349-afb1-f19518060e77','审核分享...'),
(4,'e1aa3c28-9488-44a1-b8fb-280a855eab36','审核分享...'),
(5,'f30cca4b-9f4d-46f9-adda-2bb5fca1543f','审核分享...'),
(6,'e3fba85a-ebb7-452f-b119-cc442aa6ec0b','审核分享...'),
(7,'bcc7209d-6903-44e1-96ad-c2176a28929d','审核分享...');

/*Table structure for table `share` */

DROP TABLE IF EXISTS `share`;

CREATE TABLE `share` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '发布人id',
  `title` varchar(80) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '标题',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `is_original` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否原创 0:否 1:是',
  `author` varchar(45) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '作者',
  `cover` varchar(256) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '封面',
  `summary` varchar(256) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '概要信息',
  `price` int(11) NOT NULL DEFAULT '0' COMMENT '价格（需要的积分）',
  `download_url` varchar(256) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '下载地址',
  `buy_count` int(11) NOT NULL DEFAULT '0' COMMENT '下载数 ',
  `show_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否显示 0:否 1:是',
  `audit_status` varchar(10) CHARACTER SET utf8 NOT NULL DEFAULT '0' COMMENT '审核状态 NOT_YET: 待审核 PASSED:审核通过 REJECTED:审核不通过',
  `reason` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '审核不通过原因',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 COMMENT='分享表';

/*Data for the table `share` */

insert  into `share`(`id`,`user_id`,`title`,`create_time`,`update_time`,`is_original`,`author`,`cover`,`summary`,`price`,`download_url`,`buy_count`,`show_flag`,`audit_status`,`reason`) values 
(1,1,'xxx','2019-10-11 03:02:44','2019-10-21 08:46:35',0,'guyawei','xxx','',0,'',1,0,'PASS','可以通过了'),
(2,1,'xxx','2019-10-11 03:02:47','2019-10-11 03:02:47',0,'guyawei','xxx','',0,'',1,0,'NOT_YET',''),
(3,1,'xxx','2019-10-11 03:08:12','2019-10-11 03:08:12',0,'guyawei','xxx','',0,'',1,0,'NOT_YET','');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
