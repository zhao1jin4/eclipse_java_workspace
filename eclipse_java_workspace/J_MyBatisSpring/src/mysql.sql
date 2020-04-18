
--MySQL  
/*
create database mydb  default char set utf8 ;
create user user1 identified by 'user1';  
create user user1@localhost identified by 'user1'; 
GRANT ALL   ON mydb.* TO 'user1'@'%';
*/

drop table if exists employee;
drop table if exists  department;

create table department 
(
	id int  PRIMARY KEY,
	dep_name varchar(20)
);
insert into department (id ,dep_name) values (10,'development');
insert into department (id ,dep_name) values (20,'test');


create table employee 
(
	id int  PRIMARY KEY,
	username varchar(20) NOT NULL,
	password varchar(20),
	birthday date,
	age int,
	employee_type int,
	raise_salary int ,
	deduct_salary int,
	department_id int
);
 
insert into employee (id,username,password,birthday,age,department_id,employee_type,raise_salary,deduct_salary)
values(101,'王','123', '1980-08-01',24,10,1,200,0);

insert into employee (id,username,password,birthday,age,department_id,employee_type,raise_salary,deduct_salary)
values(102,'李','123', '1985-08-01',25,10,2,0,-50);

insert into employee (id,username,password,birthday,age,department_id,employee_type,raise_salary,deduct_salary)
values(103,'孙','124', '1986-08-01',26,10,2,0,-50);

 
insert into employee (id,username,password,birthday,age,department_id,employee_type,raise_salary,deduct_salary)
values(201,'王2','123', '1980-08-01',24,20,1,200,0);

insert into employee (id,username,password,birthday,age,department_id,employee_type,raise_salary,deduct_salary)
values(202,'李2','123', '1985-08-01',25,20,2,0,-50);

insert into employee (id,username,password,birthday,age,department_id,employee_type,raise_salary,deduct_salary)
values(203,'孙2','124', '1986-08-01',26,20,2,0,-50);

commit;



----========== H2 MySQL table
CREATE TABLE IF NOT EXISTS  USER  ( 
 userId   int auto_increment NOT NULL,
 -- userId   int  NOT NULL,
 user_Name   varchar(30) NOT NULL ,
 password   varchar(80) NOT NULL ,
 comment   varchar(250)  ,
PRIMARY KEY (userId)
);
 
CREATE TABLE  IF NOT EXISTS job_history  (
 job_id int auto_increment NOT NULL,
 start_date date ,
 end_date   date ,
 job_title   varchar(30)  NOT NULL ,
 job_requirement varchar(200)  NOT NULL ,
 user_Id   int   NOT NULL,
 PRIMARY KEY (job_id)
);


insert into USER(user_Name,password,comment) values('lisi','123','test for lisi');
insert into USER(user_Name,password,comment) values('王5','123','test for 王');

insert into job_history(start_date,end_date,job_title,job_requirement,user_Id) 
values('2012-01-02','2012-09-08','java developer',
		'<?xml version="1.0" encoding="UTF-8"?>
		 <useSkills> 
			<name>JavaEE</name>
			 <name>Oracle</name>
		  </useSkills>',1);

insert into job_history(start_date,end_date,job_title,job_requirement,user_Id) 
values('2012-09-02','2013-09-08','C developer',
	'<?xml version="1.0" encoding="UTF-8"?>
		<useSkills> 
			<name>Weblogic</name>
			 <name>SQL Server</name>
		  </useSkills>',1);

commit;	