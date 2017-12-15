
---MySQL
drop table if exists user;

create table user
(
	id INTEGER auto_increment NOT NULL PRIMARY KEY,
	name varchar(55),
	age int,
	birthday date
);

insert into user (name,age,birthday) values('lisi李',25,'2005-05-22');
insert into user (name,age,birthday) values('wang王',28,'2008-08-22');