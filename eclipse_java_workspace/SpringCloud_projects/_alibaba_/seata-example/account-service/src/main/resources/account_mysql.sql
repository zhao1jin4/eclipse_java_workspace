create database account default character set utf8;
create user account@localhost IDENTIFIED WITH mysql_native_password   BY 'account';
create user account@'%'  IDENTIFIED WITH mysql_native_password   BY 'account';
grant all on account.* to account@localhost ;
grant all on account.* to account@'%'; 


DROP TABLE IF EXISTS `account_tbl`;
CREATE TABLE `account_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL,
  `money` int(11) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

