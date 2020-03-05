/*
SQLyog Ultimate v12.4.1 (64 bit)
MySQL - 5.6.43 : Database - user_center
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`user_center` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `user_center`;

/*Table structure for table `bonus_event_log` */

DROP TABLE IF EXISTS `bonus_event_log`;

CREATE TABLE `bonus_event_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `user_id` int(11) DEFAULT NULL COMMENT 'user.id',
  `value` int(11) DEFAULT NULL COMMENT '积分操作值',
  `event` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '发生的事件',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `description` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  KEY `fk_bonus_event_log_user1_idx` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1 COMMENT='积分变更记录表';

/*Data for the table `bonus_event_log` */

insert  into `bonus_event_log`(`id`,`user_id`,`value`,`event`,`create_time`,`description`) values 
(2,1,350,'增加积分','2019-10-17 09:29:46','增加积分'),
(3,1,400,'增加积分','2019-10-18 05:58:30','增加积分'),
(4,1,400,'增加积分','2019-10-18 05:58:30','增加积分'),
(5,1,50,'CONTRIBUTE','2019-10-18 06:37:06','投稿加积分..'),
(6,1,50,'CONTRIBUTE','2019-10-18 06:47:07','投稿加积分..'),
(7,1,50,'CONTRIBUTE','2019-10-18 06:48:48','投稿加积分..'),
(8,1,50,'CONTRIBUTE','2019-10-18 06:49:18','投稿加积分..'),
(9,1,50,'CONTRIBUTE','2019-10-18 06:49:18','投稿加积分..'),
(10,1,50,'CONTRIBUTE','2019-10-18 06:49:19','投稿加积分..'),
(11,1,50,'CONTRIBUTE','2019-10-18 09:14:35','投稿加积分..'),
(12,1,800,'增加积分','2019-10-21 05:25:01','增加积分'),
(13,1,50,'CONTRIBUTE','2019-10-21 05:32:30','投稿加积分..'),
(14,1,50,'CONTRIBUTE','2019-10-21 05:55:48','投稿加积分..'),
(15,1,950,'增加积分','2019-10-21 06:30:29','增加积分'),
(16,1,950,'增加积分','2019-10-21 06:30:29','增加积分'),
(17,1,350,'增加积分','2019-10-21 06:54:39','增加积分'),
(18,1,400,'增加积分','2019-10-21 06:55:18','增加积分'),
(19,1,350,'增加积分','2019-10-21 07:11:22','增加积分'),
(20,1,350,'增加积分','2019-10-21 07:11:22','增加积分'),
(21,1,400,'增加积分','2019-10-21 07:13:54','增加积分'),
(25,1,450,'增加积分','2019-10-21 08:08:23','增加积分'),
(26,1,500,'增加积分','2019-10-21 08:33:41','增加积分'),
(27,1,50,'CONTRIBUTE','2019-10-21 08:46:35','投稿加积分..');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `wx_id` varchar(64) NOT NULL DEFAULT '' COMMENT '微信id',
  `wx_nickname` varchar(64) NOT NULL DEFAULT '' COMMENT '微信昵称',
  `roles` varchar(100) NOT NULL DEFAULT '' COMMENT '角色',
  `avatar_url` varchar(255) NOT NULL DEFAULT '' COMMENT '头像地址',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `bonus` int(11) NOT NULL DEFAULT '300' COMMENT '积分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='分享';

/*Data for the table `user` */

insert  into `user`(`id`,`wx_id`,`wx_nickname`,`roles`,`avatar_url`,`create_time`,`update_time`,`bonus`) values 
(1,'53453454tdrfg','《伟》','','','2019-10-11 13:21:35','2019-10-21 08:33:41',350);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
