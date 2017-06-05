package spring_db_jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class OperaterStudentDao  extends JdbcDaoSupport
{
	//��̳��� JdbcDaoSupport �Ͳ���дsetJdbcTemplate
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
		//���� extends JdbcDaoSupport,Ҫע��DataSource,�Ϳ���ʹ��this.getJdbcTemplate()
	}
}
