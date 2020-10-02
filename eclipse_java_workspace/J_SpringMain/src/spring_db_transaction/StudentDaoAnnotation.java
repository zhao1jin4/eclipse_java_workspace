package spring_db_transaction;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class StudentDaoAnnotation implements StudentDao,ApplicationContextAware //@Transactional在接口上写
{
	
	ApplicationContext ctx;
	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.ctx=ctx;
	}
	private JdbcTemplate template;
	
	public void setJdbcTemplate(JdbcTemplate template )
	{
		this.template=template;
	}
	
	 //spring的@Transactional不是javax的
	@Transactional(rollbackFor=Exception.class,noRollbackFor=RuntimeException.class,
			propagation=Propagation.REQUIRED,isolation=Isolation.REPEATABLE_READ) //做事务,isolation默认依赖于数据库, propagation默认是Propagation.REQUIRED.
	public void updateStudentAge() throws Exception
	{
		template.update("update student set age=25  where name='lisi李'");
		System.out.println("end updateStudentAge");//代码在这里时,不能执行select * from student;锁
		//throw new Exception("checked 业务异常");//checked异常默认不会回滚,可加rollbackFor=Exception.class
		//throw new RuntimeException("unchecked 业务异常");//unchecked(RuntimeException)异常默认会事务回滚
	
		StudentDaoAnnotation self=ctx.getBean(StudentDaoAnnotation.class);
		
		//spring boot 可以自己注入自己，解决事务无效方式
		self.insertStudent();
		
	}
	
	@Transactional( propagation=Propagation.NESTED)//测试嵌入式事务  
	public void insertStudent() throws Exception
	{
		template.update("insert student (name,age) values(28,'wang')");
		System.out.println("end insertStudent") ;
		 
	}
	@Transactional
	public void updateStudentAgeSecond()throws Exception
	{
		updateStudentAge();//根据这里调用 事务不会起作用,即同一类中调用是不行的
	}
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true,timeout=20)//不需要事务,对类一级使用@Transactional
	public Student getStudent()
	{
		return template.queryForObject("select * from student where name='listi李'", Student.class);
	}

	
	
}
