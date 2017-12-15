package mybatis_spring;


import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;

public interface AnnoationDao 
{
    @Insert("insert into  employee(id,username,password,age)values(#{_e.id},#{_e.username},#{_e.password},#{_e.age})")
    public int insert(@Param("_e")Employee emp);

    
    @Insert("delete from  employee where id=#{my_named_Id}")
    public int delete(int id);
    
    @InsertProvider(type = EmployeeSqlProvider.class, method = "insertSqlBatch")
    public void insertEmployeeBatch(@Param("dataList")List<Employee> employeeList); 
}
