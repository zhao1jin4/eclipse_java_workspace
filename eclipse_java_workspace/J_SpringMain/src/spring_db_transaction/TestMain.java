package spring_db_transaction;

import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class TestMain
{
	public static void catalog()
	{
	    // org.springframework.transaction.TransactionDefinition.PROPAGATION_REQUIRED
	            
		ApplicationContext context=new ClassPathXmlApplicationContext("spring_db_transaction/class_transaction_beans.xml");
		CategoryDao dao=(CategoryDao)context.getBean("categoryDao");
		Category category1=new Category();
		category1.setId(11);
		category1.setName("wangwu1");
		
		Category category2=new Category();
		category2.setId(12);
		category2.setName("wangwu2");
        
		try
        {
            dao.addCategory(category1,category2); //spring JDBC,自动事务
        } catch (Exception e)
        {
            e.printStackTrace();
        }
	}
	
	public static void studentAnnotation()//transaction test
	{
		ApplicationContext context=new ClassPathXmlApplicationContext("spring_db_transaction/tag_transaction_beans.xml");
		StudentDaoAnnotation dao=(StudentDaoAnnotation)context.getBean("studentDaoAnnotation");
		try {
			dao.updateStudentAge();//这个事务有效
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			//这个事务无效
			//dao.updateStudentAgeSecond();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void studentTag()//transaction test
	{
		ApplicationContext context=new ClassPathXmlApplicationContext("spring_db_transaction/tag_transaction_beans.xml");
		StudentDaoTag dao=(StudentDaoTag)context.getBean("studentDaoTag");
		try {
			dao.updateStudentAge();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void programTransaction() 
	{
		ApplicationContext context=new ClassPathXmlApplicationContext("spring_db_transaction/tag_program_transaction.xml");
		TransactionTemplate transactionTemplate= context.getBean("transactionTemplate",TransactionTemplate.class);
		final JdbcTemplate jdbcTemplate= context.getBean("jdbcTemplate",JdbcTemplate.class);
		try{
			 transactionTemplate.execute(  new TransactionCallbackWithoutResult()
			 {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status)
				{
					//DuplicateKeyException
				 
					jdbcTemplate.execute("insert into student (name,age,birthday) values('lisi李_22',25,'2012-10-22')");
					if(1+1 == 2 )
						throw new RuntimeException ("erro");
					
					jdbcTemplate.execute("insert into category (name) values('fruite水果__22');");
					 
				}
			});
		}catch(Exception ex)
		{
			System.out.println("oh my god ,have error");
		}
		
	}
	public static void main(String[] args)
	{ 
		//catalog();
		studentAnnotation();
		//studentTag();
		//programTransaction();
	}
}
