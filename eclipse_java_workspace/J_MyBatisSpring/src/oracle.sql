
drop table employee;
drop table department;

create table department 
(
	id int  PRIMARY KEY,
	dep_name varchar2(20)
);
insert into department (id ,dep_name) values (10,'development');

create table employee 
(
	id int  PRIMARY KEY,
	username varchar2(20) NOT NULL,
	password varchar2(20),
	birthday date,
	age int,
	employee_type int,
	raise_salary int ,
	deduct_salary int,
	department_id int
);
 
insert into employee (id,username,password,birthday,age,department_id,employee_type,raise_salary,deduct_salary)
values(101,'王','123', to_date('1980-08-01','yyyy-mm-dd'),25,10,1,200,0);

insert into employee (id,username,password,birthday,age,department_id,employee_type,raise_salary,deduct_salary)
values(102,'李','123', to_date('1985-08-01','yyyy-mm-dd'),25,10,2,0,-50);

commit;


CREATE TABLE   job_history  (
 job_id int  ,
 start_date date ,
 end_date   date ,
 job_title   varchar2(30)  NOT NULL ,
 job_requirement varchar2(200)  NOT NULL ,
 user_Id   int   NOT NULL,
 PRIMARY KEY (job_id)
);
create index INX_job_history_startDate on job_history(start_date);


insert into job_history(job_id,start_date,end_date,job_title,job_requirement,user_Id) 
values(1,to_date('2012-01-02 22:14:23','yyyy-mm-dd hh24:mi:ss'),to_date('2012-09-08 22:35:33','yyyy-mm-dd hh24:mi:ss'),'java developer',
		'<?xml version="1.0" encoding="UTF-8"?>
		 <useSkills> 
			<name>JavaEE</name>
			 <name>Oracle</name>
		  </useSkills>',1);

insert into job_history(job_id,start_date,end_date,job_title,job_requirement,user_Id) 
values(2,to_date('2012-09-01 22:14:23','yyyy-mm-dd hh24:mi:ss'),to_date('2013-09-08 22:35:33','yyyy-mm-dd hh24:mi:ss'),'C developer',
	'<?xml version="1.0" encoding="UTF-8"?>
		<useSkills> 
			<name>Weblogic</name>
			 <name>SQL Server</name>
		  </useSkills>',1);
commit;		  

