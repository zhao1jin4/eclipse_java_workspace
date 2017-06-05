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
		template.update("update student set age=28  where name='lisi��'");
		System.out.println("end updateStudentAge");//����������ʱ,����ִ��select * from student;��
		//throw new Exception("checked ҵ���쳣"); 
		throw new RuntimeException("unchecked ҵ���쳣"); 
	}
	
	public Student getStudent()
	{
		return template.queryForObject("select * from student where name='listi��'", Student.class);
	}
	
}
