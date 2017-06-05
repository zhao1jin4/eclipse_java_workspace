package spring_db_transaction;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class StudentDaoAnnotation implements StudentDao //@Transactional在接口上写
{
	private JdbcTemplate template;
	
	public void setJdbcTemplate(JdbcTemplate template )
	{
		this.template=template;
	}
	
	 //spring的@Transactional不是javax的
	@Transactional(rollbackFor=Exception.class,noRollbackFor=RuntimeException.class,
			propagation=Propagation.REQUIRED,isolation=Isolation.REPEATABLE_READ) //做事务,isolation默认依赖于数据, propagation默认是Propagation.REQUIRED.
	public void updateStudentAge() throws Exception
	{
		template.update("update student set age=25  where name='lisi李'");
		System.out.println("end updateStudentAge");//代码在这里时,不能执行select * from student;锁
		//throw new Exception("checked 业务异常");//checked异常默认不会回滚,可加rollbackFor=Exception.class
		//throw new RuntimeException("unchecked 业务异常");//unchecked(RuntimeException)异常默认会事务回滚
	}
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true,timeout=20)//不需要事务,对类一级使用@Transactional
	public Student getStudent()
	{
		return template.queryForObject("select * from student where name='listi李'", Student.class);
	}
	
}
