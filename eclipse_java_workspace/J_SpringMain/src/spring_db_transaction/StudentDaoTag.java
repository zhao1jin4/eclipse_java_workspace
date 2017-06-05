package spring_db_transaction;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class StudentDaoTag
{
	private JdbcTemplate template;
	
	public void setJdbcTemplate(JdbcTemplate template )
	{
		this.template=template;
	}
	
	public void updateStudentAge() throws Exception
	{
		template.update("update student set age=28  where name='lisi李'");
		System.out.println("end updateStudentAge");//代码在这里时,不能执行select * from student;锁
		//throw new Exception("checked 业务异常"); 
		throw new RuntimeException("unchecked 业务异常"); 
	}
	
	public Student getStudent()
	{
		return template.queryForObject("select * from student where name='listi李'", Student.class);
	}
	
}
