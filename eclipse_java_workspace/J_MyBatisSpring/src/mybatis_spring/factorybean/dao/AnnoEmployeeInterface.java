package mybatis_spring.factorybean.dao;

import mybatis_spring.Employee;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

@CacheNamespace(size = 512)
public interface AnnoEmployeeInterface {
	@Delete("delete from  employee   where id =#{_id}")//如一个参数类型是int,String 名字随便叫
	//public int deleteByEmployeeId(@Param("_id")int emp_id);
	public int deleteByEmployeeId(int emp_id);
	
   @Insert("insert into  employee(id,username,password,age)values(#{_e.id},#{_e.username},#{_e.password},#{_e.age})")
    public int insert(@Param("_e")Employee emp);

}
