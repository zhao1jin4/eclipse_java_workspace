--H2
drop table student;
create table student
(
	id INTEGER auto_increment NOT NULL PRIMARY KEY,
	name varchar(55),
	age int,
	birthday date
);
insert into student (name,age,birthday) values('lisi李',25,'2012-10-22');
insert into student (name,age,birthday) values('wang王',28,'2008-08-22');

drop table category;
create table category
(
	id INTEGER auto_increment NOT NULL PRIMARY KEY,
	name varchar(55),
);
insert into category (name) values('fruite水果');



---MySQL
drop table if exists student;

create table student
(
	id INTEGER auto_increment NOT NULL PRIMARY KEY,
	name varchar(55),
	age int,
	birthday date
);
insert into student (name,age,birthday) values('lisi李',25,'2005-05-22');
insert into student (name,age,birthday) values('wang王',28,'2008-08-22');


drop table if exists category;
create table category
(
	id INTEGER auto_increment NOT NULL PRIMARY KEY,
	name varchar(55)
);
insert into category (name) values('fruite水果');


