package dynamic_sql.join;

 
import static mybatis_generator.mapper.DepartmentEntityDynamicSqlSupport.departmentEntity;
import static mybatis_generator.mapper.EmployeeDynamicSqlSupport.employee;
import static org.mybatis.dynamic.sql.SqlBuilder.and;
import static org.mybatis.dynamic.sql.SqlBuilder.count;
import static org.mybatis.dynamic.sql.SqlBuilder.deleteFrom;
import static org.mybatis.dynamic.sql.SqlBuilder.equalTo;
import static org.mybatis.dynamic.sql.SqlBuilder.insert;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isIn;
import static org.mybatis.dynamic.sql.SqlBuilder.isLessThan;
import static org.mybatis.dynamic.sql.SqlBuilder.isLikeCaseInsensitive;
import static org.mybatis.dynamic.sql.SqlBuilder.select;
import static org.mybatis.dynamic.sql.SqlBuilder.update;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import mybatis_generator.mapper.EmployeeMapper;
import mybatis_generator.model.DepartmentEntity;
import mybatis_generator.model.Employee;
public class DynamicJoinDaoMain
{
	public static void main(String... args) throws Exception
	{
		selectJoinDao();
    }
	
	public static void selectJoinDao( ) throws Exception
	{
		UnpooledDataSource dsUnPool = new UnpooledDataSource("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/mydb", "zh", "123");
		PooledDataSource dsPool = new PooledDataSource("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/mydb", "zh", "123");
		
		DataSource ds=genHikariDataSource();
		
		//不使用Config.xml
        Environment environment = new Environment("test", new JdbcTransactionFactory(), ds);
        Configuration config = new Configuration(environment);
        config.addMapper(JoinMapper.class);//加Mapper ,不能读XML的
        //config.addMappers("dynamic_sql.join.mapper"); //包名，日志提示可找jar包 
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(config);
		sqlSessionFactory.getConfiguration().addMapper(EmployeeMapper.class); ;//加Mapper
		//---
        try (SqlSession session = sqlSessionFactory.openSession()) {
        	JoinMapper mapper = session.getMapper(JoinMapper.class);
        	EmployeeMapper employeeMapper = session.getMapper(EmployeeMapper.class);
            SelectStatementProvider selectStatement = select(departmentEntity.id, departmentEntity.depName)
                    .from(departmentEntity, "d")
                    .build()
                    .render(RenderingStrategies.MYBATIS3);
        	List<DepartmentEntity> depts= mapper.fetchMany(selectStatement);
        	System.out.println(depts);
        	   
        	 
        	 selectStatement = select(employee.id, employee.userName,employee.brithDay,employee.department_id)
                       .from(employee, "e")
                       .where(employee.id, isEqualTo(101))
                       .build()
                       .render(RenderingStrategies.MYBATIS3);
        	   
        	 Employee emp= mapper.fetchOne(selectStatement);
        	 System.out.println(emp);  
        	  
        
             
            
            Employee emp1=new Employee();
            emp1.setId(8008);
            emp1.setAge(20);
            emp1.setBrithDay(new Date());
            emp1.setUserName("kotlin");
            emp1.setEmployee_type(2);
            //int effectRow=employeeMapper.insert(emp1);//代码生成的
            //employeeMapper.updateByPrimaryKey(record);
            //employeeMapper.deleteByPrimaryKey(id);
           UpdateDSLCompleter u; 
           
            InsertStatementProvider<Employee> insertStatement = insert(emp1)//值
                    .into(employee)
                    .map(employee.id).toProperty("id")
                    .map(employee.userName).toProperty("userName")//只有写的字段才要
                    .map(employee.employee_type).toProperty("employee_type")
                    .build()
                    .render(RenderingStrategies.MYBATIS3);
            System.out.println(insertStatement.getInsertStatement()); 
            //insert into employee (id, username, employee_type) values (#{record.id,jdbcType=INTEGER}, #{record.userName,jdbcType=VARCHAR}, #{record.employee_type,jdbcType=INTEGER})
            int effectRow=employeeMapper.insert(insertStatement);
            session.commit();//默认不是自动提交的
            
            UpdateStatementProvider updateStatement = update(employee)
                    .set(employee.userName).equalTo("kotlin2") 
                    .set(employee.brithDay).equalToNull()
                    .where(employee.id, isEqualTo(8008))
                    .build()
                    .render(RenderingStrategies.MYBATIS3);
            System.out.println(updateStatement.getUpdateStatement()); 
            effectRow=employeeMapper.update(updateStatement);
            
         
            employeeMapper.updateByPrimaryKey(emp1);//里面用到了 UpdateDSLCompleter
            //低版本的mybatis不会生成XxxByPrimaryKey,也用不了
            //employeeMapper.updateByPrimaryKeySelective(record)
            session.commit();
            
           //动态条件
            String fName="kotlinX";
            //String fName=null;
            UpdateDSL<UpdateModel>.UpdateWhereBuilder builder = update(employee)
                    .set(employee.userName).equalTo("kotlin3")
                    .where(employee.id, isEqualTo(8008));
            builder.and(employee.userName, isEqualTo(fName).when(Objects::nonNull));
            updateStatement = builder.build().render(RenderingStrategies.MYBATIS3);
            effectRow=employeeMapper.update(updateStatement);
            session.commit();
            
            
            DeleteStatementProvider deleteStatement = deleteFrom(employee)
                    .where(employee.id, isEqualTo(8008), and(employee.employee_type, isEqualTo(2)))
                    .or(employee.userName, isLikeCaseInsensitive("kot%"))
                    .build()
                    .render(RenderingStrategies.MYBATIS3);
            System.out.println(deleteStatement.getDeleteStatement());  
            //delete from employee where (id = #{parameters.p1,jdbcType=INTEGER} and employee_type = #{parameters.p2,jdbcType=INTEGER}) or upper(username) like #{parameters.p3,jdbcType=VARCHAR}
            effectRow=employeeMapper.delete(deleteStatement);
            session.commit();
            
            
            //isIn子查询 ，isLessThan
           selectStatement = select(employee.userName,employee.age)
                    .from(employee, "a")
                    .where(employee.age, isIn(select(employee.age).from(employee).where(employee.department_id, isEqualTo(20))))
                    .and(employee.age, isLessThan(100))
                    .build()
                    .render(RenderingStrategies.MYBATIS3);
           System.out.println(selectStatement.getSelectStatement());
           List<Employee> res=  employeeMapper.selectMany(selectStatement);
           List<Employee> records = employeeMapper.select(SelectDSLCompleter.allRows());
           
         // group by, count(*) as count 没有having??
       	 selectStatement = select( departmentEntity.depName, count().as("count"),
       			 	SqlBuilder.max(employee.age).as("max_age"),
       			 	SqlBuilder.avg(employee.age).as("avg_age"),
       			 	SqlBuilder.min(employee.age).as("min_age")
       			 	)
                    .from(departmentEntity, "p").join(employee, "e").on(employee.department_id, equalTo(departmentEntity.id))
                    .groupBy(departmentEntity.depName)
                    .build()
                    .render(RenderingStrategies.MYBATIS3);
           System.out.println(selectStatement.getSelectStatement()); 
            
          //如何做到 ？？？  <collection>多行记录映射成一条记录
          selectStatement = select(departmentEntity.id, departmentEntity.depName,employee.userName, employee.age, employee.brithDay)
                    .from(departmentEntity, "d")
                    .join(employee, "e").on(employee.department_id, equalTo(departmentEntity.id))
                    .build()
                    .render(RenderingStrategies.MYBATIS3);
           System.out.println(selectStatement.getSelectStatement()); 
       	   depts= mapper.selectMany(selectStatement);  
           System.out.println(depts);
         
            
        }catch(Exception e)
        {
        	e.printStackTrace();
        }
    } 
	public static  DataSource genHikariDataSource()
	{
		
		HikariConfig config = new HikariConfig();
		config.setDriverClassName("com.mysql.cj.jdbc.Driver"); //MySQL 8
		config.setJdbcUrl("jdbc:mysql://localhost:3306/mydb");
		config.setUsername("zh");
		config.setPassword("123");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		HikariDataSource ds = new HikariDataSource(config);
		return ds;
	}
}
