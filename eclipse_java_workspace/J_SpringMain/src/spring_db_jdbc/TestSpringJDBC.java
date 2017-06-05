package spring_db_jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public class TestSpringJDBC extends TestCase
{
	ApplicationContext context ;
	JdbcTemplate jdbcTemplate;
	protected void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("spring_db_jdbc/class_jdbc_beans.xml");	
		jdbcTemplate = (JdbcTemplate) context.getBean("jdbcTemplate");
	}
	protected void tearDown() throws Exception {
	}

	public void testQuery()
	{
		Object obj = jdbcTemplate.query("select * from student where id>?",new Object[]{ new Integer(3) }, 
					new ResultSetExtractor()
					{
						public Object extractData(ResultSet rs) throws SQLException, DataAccessException
						{
							List l = new ArrayList();
							while (rs.next())
							{
								Student stu = new Student();
								stu.setAge(rs.getInt("age"));
								stu.setName(rs.getString("name"));
								stu.setBirthday(rs.getDate("birthday"));
								l.add(stu);
							}
							return l;
						}
					});
		// Student s=(Student)obj;
		// System.out.println(s.getName()+":"+s.getAge());

		List s = (List) obj;
		for (Object o : s)
		{
			Student stu = (Student) o;
			System.out.println(stu.getName() + ":" + stu.getBirthday());
		}
	}

	public void testRowMapper()
	{
		List s=jdbcTemplate.query("select * from student where id>?", new Object[]	{ new Integer(3) }, new RowMapper()
				{
					public Object mapRow(ResultSet rs, int rowNumber) throws SQLException
					{
						Student stu = new Student();
						stu.setAge(rs.getInt("age"));
						stu.setName(rs.getString("name"));
						stu.setBirthday(rs.getDate("birthday"));
						return stu;
					}
				});
		
		for (Object o : s)
		{
			Student stu = (Student) o;
			System.out.println(stu.getName() + ":" + stu.getBirthday());
		}
	}
	public void testTransaction()
	{
		OperaterStudentDao dao=(OperaterStudentDao)context.getBean("proxy");
		Student stu=new Student();
		stu.setId(20);
		stu.setAge(25);
		stu.setName("wang");
		dao.insertStudent(stu);
	}

}
