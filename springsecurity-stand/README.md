## 到目前为止，我们基本完成了springsecurity3一书中第四章功能了。
+ 用户信息存储在数据库中，使用自定义的数据库
+ 加盐
+ remember-me持久化到数据库
+ session并发控制
+ url权限信息从数据库取出
+ 界面使用layui美化

### 本机用到的数据库设计pdm，可根据此pdm生成sql文件。
链接：http://pan.baidu.com/s/1qYKrr1U 密码：fdkd

sql：
```sql
/*
SQLyog Trial v12.2.1 (64 bit)
MySQL - 5.6.33 : Database - security
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`security` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `security`;

/*Table structure for table `persistent_logins` */

DROP TABLE IF EXISTS `persistent_logins`;

CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `resource` */

DROP TABLE IF EXISTS `resource`;

CREATE TABLE `resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `name` varchar(500) NOT NULL COMMENT '资源名称',
  `descr` varchar(200) NOT NULL COMMENT '描述资源的信息，如添加用户功能',
  `type` tinyint(2) NOT NULL COMMENT '资源类型：1.网页 2.按钮',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '资源上级id,用来构建资源树状图。不能为空，默认为0',
  `url` varchar(500) NOT NULL COMMENT '资源url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='网站的资源，如一个网页或一个按钮';

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `name` varchar(50) NOT NULL COMMENT '角色名称',
  `descr` varchar(200) NOT NULL COMMENT '角色名称',
  `status` tinyint(2) NOT NULL COMMENT '角色状态',
  `create_dt` datetime NOT NULL COMMENT '创建时间',
  `modity_dt` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色定义表';

/*Table structure for table `role_resource` */

DROP TABLE IF EXISTS `role_resource`;

CREATE TABLE `role_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `role_id` bigint(20) NOT NULL COMMENT '角色Id',
  `resource_id` bigint(20) NOT NULL COMMENT '资源Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='描述角色和资源间的对应关系';

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户的主键',
  `name` varchar(50) NOT NULL COMMENT '用户名',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(200) NOT NULL COMMENT '用户密码',
  `status` tinyint(2) NOT NULL COMMENT '用户状态',
  `create_dt` datetime NOT NULL COMMENT '创建时间',
  `modify_dt` datetime DEFAULT NULL COMMENT '最后修改时间',
  `salt` varchar(50) DEFAULT NULL COMMENT '盐值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `user_id` bigint(20) NOT NULL COMMENT '用户Id',
  `role_id` bigint(20) NOT NULL COMMENT '角色Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户_角色关联表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
```
#### 测试数据

```sql
USE `security`;

insert  into `resource`(`id`,`name`,`descr`,`type`,`parent_id`,`url`) values 

(1,'INDEX','主页',1,0,'/'),

(2,'ACCOUNT','用户账户页',1,0,'/account'),

(3,'LOGIN','登陆页',1,0,'/login');

/*Data for the table `role` */

insert  into `role`(`id`,`name`,`descr`,`status`,`create_dt`,`modity_dt`) values 

(1,'ROLE_ADMIN','管理员',1,'2016-10-31 11:20:46',NULL),

(2,'ROLE_ANONYMOUS','游客',2,'2016-10-31 11:21:15',NULL),

(3,'ROLE_MEMBER','会员',1,'2016-11-02 15:30:40',NULL);

/*Data for the table `role_resource` */

insert  into `role_resource`(`id`,`role_id`,`resource_id`) values 

(1,1,1),

(2,1,2),

(3,3,1),

(4,1,3),

(5,2,3);

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`nick_name`,`password`,`status`,`create_dt`,`modify_dt`,`salt`) values 

(1,'xiaoli','小李','cf705e28a0053ced9bc6d8f9f5ca2a68',1,'2016-10-31 11:20:12',NULL,'I336y9t7Efbqq7AT1Wk5fGz6bM78HdaMuDU/WoQbWnM='),

(2,'xiaoguo','小郭','456aa06e8c2ffb28a5553ff700d573be',1,'2016-10-31 11:21:32',NULL,'U3b2ZvYOaxu2FuEbHkbxS0HC6o9K53gMCUKI8B3WaiM=');

/*Data for the table `user_role` */

insert  into `user_role`(`id`,`user_id`,`role_id`) values 

(1,1,1),

(2,2,2);


```

