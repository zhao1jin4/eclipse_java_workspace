
package dynamic_sql.join;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.mapping.FetchType; 
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

import mybatis_generator.model.Employee;
import mybatis_generator.model.DepartmentEntity;

//@Mapper
public interface JoinMapper {
  //@Many时column 为(父表的)主键,javaType=List.class
    @Results (value={
        @Result(column="id", property="id"),
        @Result(column="dep_name", property="depName"),
        @Result( many = @Many(select = "queryEmployeeByDeptId"),javaType = List.class,column = "id", property="employees")
    })   
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    List<DepartmentEntity> fetchMany(SelectStatementProvider selectStatement);
    
    
   @Select("select * from employee where department_id=#{value}")
   @Results(value = {
    		@Result(property="userName", column="username"),
    		@Result(property="age", column="age")
       })
   public List<Employee> queryEmployeeByDeptId(@Param("value")String deptId);
    
   
  //@One 时column 是子表的外键
   @Results(value = {
    		@Result(property="userName", column="username"),
    		@Result(property="age", column="age"),
    		@Result( one = @One(select = "queryDepartmentById"),javaType = DepartmentEntity.class,column = "department_id", property="department")
       })
   @SelectProvider(type=SqlProviderAdapter.class, method="select")
   public Employee  fetchOne( SelectStatementProvider selectStatement); 
   
   
   @Select("select * from department where id=#{value}")
   @Results (id="MyDepartment",value={
	        @Result(column="id", property="id"),
	        @Result(column="dep_name", property="depName"),
	    })  
   public DepartmentEntity  queryDepartmentById(int id);
   
   
   //如何做到 ？？？  <collection>多行记录映射成一条记录
   @Results (value={
	        @Result(column="id", property="id"),
	        @Result(column="dep_name", property="depName"),
	    })  
   //@ResultMap("MyDepartment")//是引用已经存在的@Results (id="MyDepartment")
   @SelectProvider(type=SqlProviderAdapter.class, method="select")
   List<DepartmentEntity> selectMany(SelectStatementProvider selectStatement);
   
}
