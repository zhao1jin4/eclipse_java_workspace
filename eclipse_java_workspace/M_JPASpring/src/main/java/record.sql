--- init db
alter user root@localhost identified WITH mysql_native_password  by 'root' ;
create user root@'%' identified WITH mysql_native_password  by 'root' ;


create database mydb default charset utf8mb4;

create user zh@'%' identified  WITH mysql_native_password   by '123'; 
GRANT ALL   ON mydb.* TO  zh@'%'

---



drop table jpa_score  
drop table jpa_good_student 
 
drop table jpa_student    

select
TABLE_NAME,COLUMN_NAME,CONSTRAINT_NAME, REFERENCED_TABLE_NAME,REFERENCED_COLUMN_NAME
from INFORMATION_SCHEMA.KEY_COLUMN_USAGE
where CONSTRAINT_SCHEMA ='mydb' AND
REFERENCED_TABLE_NAME = 'jpa_student';



mvn dependency:copy-dependencies
 


