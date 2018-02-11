/*
SQLyog Enterprise v12.09 (64 bit)
MySQL - 5.5.28 : Database - test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`test` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `test`;

/*Table structure for table `schedule` */

DROP TABLE IF EXISTS `schedule`;

CREATE TABLE `schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) NOT NULL,
  `start_date` timestamp NULL DEFAULT NULL,
  `end_date` timestamp NULL DEFAULT NULL,
  `remind` varchar(1) DEFAULT NULL,
  `priority` varchar(1) DEFAULT NULL,
  `remarks` text,
  `create_time` timestamp NULL DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

/*Data for the table `schedule` */

insert  into `schedule`(`id`,`name`,`start_date`,`end_date`,`remind`,`priority`,`remarks`,`create_time`,`user_id`) values (2,'test','2018-01-23 11:44:55','2018-01-27 11:44:58','1','3','ddddddddddd',NULL,NULL),(3,'and','2018-01-18 13:30:01','2018-01-26 13:30:04','2','3','而我',NULL,NULL),(5,'二','2018-01-17 13:44:26','2018-01-27 13:44:29','2','1','而我却无群',NULL,NULL),(6,'32','2018-01-11 13:53:18','2018-01-26 13:53:23','1','2','453534',NULL,NULL),(7,'432','2018-01-17 13:54:03','2018-01-17 13:54:06',NULL,'2','43243',NULL,NULL),(9,'4342','2018-01-25 14:30:18','2018-01-27 14:30:21','1','2','认为二无',NULL,NULL),(10,'34243','2018-01-11 14:32:14','2018-01-27 14:32:17','1','2','31打算','2018-01-12 14:32:23',1),(11,'测试10','2018-01-11 15:06:15','2018-01-31 15:06:18','1','1','多大的多大的','2018-01-15 15:06:27',1),(12,'长城长','2018-01-11 16:48:35','2018-02-28 16:48:38','1','3','的顶顶顶顶顶顶顶顶顶顶','2018-01-15 16:48:50',1),(13,'erer','2018-01-24 18:13:59','2018-01-31 18:14:02','1','3','4535435','2018-01-15 18:14:09',1),(14,'xxxx','2018-01-09 15:45:27','2018-01-25 15:45:29','1','3','dadsdsa','2018-01-16 15:45:36',1),(15,'明天','2018-01-26 17:06:05','2018-01-29 17:06:11','1','2','多大的多大的','2018-01-25 17:06:20',1),(16,'后天','2018-01-27 18:04:43','2018-01-31 18:04:48','1','2','213213213','2018-01-25 18:04:54',1),(17,'哇哈哈','2018-01-26 10:01:42','2018-01-27 10:01:51','2','1','我问问','2018-01-26 10:02:05',1),(18,'232332','2018-01-27 10:14:11','2018-01-26 10:14:12','2','2','333','2018-01-26 10:14:23',1),(19,'xxxx','2018-01-26 10:14:43','2018-02-02 10:14:44','2','2','fefewre','2018-01-26 10:14:51',1),(20,'43432','2018-02-14 10:16:42','2018-02-28 10:17:00','2','2','','2018-01-26 10:17:09',1),(21,'点点滴滴','2018-01-26 10:20:28','2018-01-27 10:39:52','1','2','3432432','2018-01-26 10:40:05',1),(22,'提醒啦','2018-01-26 13:29:29','2018-02-02 13:29:33','1','2','33333','2018-01-26 13:29:49',1),(23,'你好啊','2018-01-26 15:03:15','2018-01-30 15:03:17','1','2','费大幅度发','2018-01-26 15:03:29',1),(24,'任务名哈哈哈','2018-01-26 18:11:39','2018-01-30 18:11:41','1','2','4543543','2018-01-26 18:12:00',1),(25,'通天塔','2018-02-09 18:28:40','2018-02-27 18:28:48','1','2','法第三方第三方','2018-01-26 18:28:55',1);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) NOT NULL COMMENT '登陆名',
  `pass_word` varchar(32) NOT NULL COMMENT '密码',
  `name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`user_name`,`pass_word`,`name`,`email`) values (1,'admin','698d51a19d8a121ce581499d7b701668',NULL,NULL),(2,'flx','202cb962ac59075b964b07152d234b70','付立新','fulixinjing@qq.com');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
