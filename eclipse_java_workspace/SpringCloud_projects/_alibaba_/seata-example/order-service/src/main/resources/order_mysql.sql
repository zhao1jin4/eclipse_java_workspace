create database orde default character set utf8;
create user orde@localhost IDENTIFIED WITH mysql_native_password   BY 'orde';
create user orde@'%'  IDENTIFIED WITH mysql_native_password   BY 'orde';
grant all on orde.* to orde@localhost ;
grant all on orde.* to orde@'%'; 

DROP TABLE IF EXISTS `order_tbl`;
CREATE TABLE `order_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL,
  `commodity_code` varchar(255) DEFAULT NULL,
  `count` int(11) DEFAULT 0,
  `money` int(11) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
