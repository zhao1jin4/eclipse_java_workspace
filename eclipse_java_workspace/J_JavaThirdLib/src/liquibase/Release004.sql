--liquibase formatted sql


--comment:     changeset格式   --changeset author:id attribute1:value1 attribute2:value2 可以设置endDelimiter(默认为;) https://docs.liquibase.com/concepts/basic/sql-format.html
--changeset gpl:Release0004-1
CREATE TABLE table2 (
  id int(11) NOT NULL,
  name varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=MyISAM;
--rollback drop table table2;
 
--changeset gpl:Release0004-2
ALTER TABLE  table2 CHANGE  id  id INT( 11 ) AUTO_INCREMENT;
--rollback ALTER TABLE  table2 CHANGE  id  id INT( 11 ) NOT NULL;
 
--changeset gpl:Release0004-3
ALTER TABLE  table2 CHANGE  name  firstname VARCHAR( 255 );
--rollback ALTER TABLE  table2 CHANGE  firstname  name VARCHAR( 255 );
 
--changeset gpl:Release0004-4
INSERT INTO table2 (id, firstname) VALUES (NULL, 'name1'),(NULL, 'name2'), (NULL, 'name3');
--rollback DELETE FROM table2 WHERE firstname IN('name1','name2','name3');

