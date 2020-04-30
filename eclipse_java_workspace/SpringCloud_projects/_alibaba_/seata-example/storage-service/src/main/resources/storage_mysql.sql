create database storage default character set utf8;
create user storage@localhost IDENTIFIED WITH mysql_native_password   BY 'storage';
create user storage@'%'  IDENTIFIED WITH mysql_native_password   BY 'storage';
grant all on storage.* to storage@localhost ;
grant all on storage.* to storage@'%'; 

DROP TABLE IF EXISTS `storage_tbl`;
CREATE TABLE `storage_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `commodity_code` varchar(255) DEFAULT NULL,
  `count` int(11) DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`commodity_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

