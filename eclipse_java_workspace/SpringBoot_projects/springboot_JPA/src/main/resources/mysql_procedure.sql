# this for mysql
delimiter /

DROP procedure IF EXISTS plus1inout
/
CREATE procedure plus1inout (IN arg int, OUT res int)  
BEGIN  
	set res = arg + 1; 
END
/

/*
-- test
set @res=2;
call plus1inout(2,@res) ;
select @res;

*/

DROP table subscription IF EXISTS
/
CREATE TABLE subscription(id IDENTITY, product_name VARCHAR(255), user_id INT)
/

delimiter ;
