package spring_db_jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class OperaterStudentDao  extends JdbcDaoSupport
{
	//如继承自 JdbcDaoSupport 就不用写setJdbcTemplate
	/*
	private JdbcTemplate template;  
	public void setJdbcTemplate(JdbcTemplate template )
	{
		this.template=template;
	}*/
	
	public void insertStudent(Student stu)
	{
		//template.update("insert into student(id,name,age) values(?,?,?)", new Object[]{new Integer(stu.getId()),stu.getName(),stu.getAge()});
		//template.update("insert into student(id,name,age) values(?,?,?)", new Object[]{new Integer(stu.getId()),stu.getName(),stu.getAge()});
		this.getJdbcTemplate().update("insert into student(id,name,age) values(?,?,?)", new Object[]{new Integer(stu.getId()),stu.getName(),stu.getAge()});
		//对于 extends JdbcDaoSupport,要注入DataSource,就可以使用this.getJdbcTemplate()
	}
}
