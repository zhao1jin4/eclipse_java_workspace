package mybatis_spring;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.batch.item.ItemWriter;


public class MyBatisSpringMain 
{ 
	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = new  ClassPathXmlApplicationContext("mybatis_spring/applicationContext.xml");
		
//		testTransaction(ctx);  
		
//		testBatchUseSpringTransaction(ctx);// 用时4秒
//		 testBatchUseSpringSession(ctx);// values (),()形式 用2秒
//		 testBatchUseMybatisSession(ctx);//BATCH 用8秒
		testWithSpringBatch(ctx); //看源码使用 BATCH ,超过2分,像是一条条的插入
		testMySQLBatchInsert(ctx);
		//testNull(ctx);
	}
	public  static void testMySQLBatchInsert(ApplicationContext ctx) throws SQLException
	{
		SqlSessionTemplate sqlSessionTemplate=ctx.getBean("sqlSessionTemplate",SqlSessionTemplate.class);
		List<Map<String,Object>> allData=new ArrayList<Map<String,Object>>();
		for(int i=0;i<10000;i++)
		{
			Map<String,Object> emp=new HashMap<String,Object>();
			emp.put("id", i);
			emp.put("username", "username"+i);
			allData.add(emp);
		}
		Date before=new Date();
		String tableName="employee";
		String fieldOrderName="id,username";
		
		//--------manual gen sql,速度最快约1秒
		SqlSessionTemplate batchSqlSessionTemplate=ctx.getBean("batchSqlSessionTemplate",SqlSessionTemplate.class);
        Connection conn=batchSqlSessionTemplate.getSqlSessionFactory().openSession().getConnection();
       
        
        StringBuilder sql=new StringBuilder(20000);
        sql.append("insert into ").append(tableName).append("(").append(fieldOrderName).append(") values \n");
        int size=allData.size();
        String[] fields=fieldOrderName.split(",");
         for (int o=0;o<size;o++) 
         {
        	 sql.append("(");
        	 Map<String, Object> row=allData.get(o);
        	 for(int i=0;i<fields.length;i++ )
        	 {
        		 String field= fields[i];
        		 Object val=row.get(field);
        		 if(val instanceof String)
        			 sql.append("'").append(val.toString().replace("'", "")).append("'");
        		 else if (val instanceof Date)
        			 sql.append("'").append(DateUtils.formatWithISO((Date)row.get(field))).append("'");
        		  else
        			 sql.append(row.get(field));
        		 if(i != fields.length-1)
        			 sql.append(",");
        	 }
        	 sql.append(")\n");
        	 if(o != size-1)
        		 sql.append(",");
         }
         Statement stmt=conn.createStatement();
         stmt.execute(sql.toString());
        
         conn.close();
     	java.util.Date after=new java.util.Date();
		System.out.println("insert into values传List方式  批处理1万条耗时毫秒数 " + (after.getTime()-before.getTime()) );
	
	}
	public  static void testBatchUseSpringSession(ApplicationContext ctx)
	{
		SqlSessionTemplate batchSqlSessionTemplate=ctx.getBean("batchSqlSessionTemplate",SqlSessionTemplate.class);
		for(int i=0;i<10;i++)
		{
			
//			Map<String,Object> param=new HashMap<String,Object>();
//			param.put("id", i);
//			param.put("username", "username"+i);
//			session.insert("EmployeeMapper.insert",param);
			
			AnnoationDao dao=batchSqlSessionTemplate.getMapper(AnnoationDao.class);
			Employee emp = new Employee();
			emp.setAge(i);
			emp.setId(i);
			emp.setUsername("lisi"+i);
			dao.insert(emp); //立即入库,也不算是Batch了
			
			if(i%1000==0)
			{	
				//batchSqlSessionTemplate.commit();  //如使用Spring的类，不允许手工commit
				batchSqlSessionTemplate.clearCache();  
			}
		}
		/* 	*/
		//--------------------------
		SqlSessionTemplate sqlSessionTemplate=ctx.getBean("sqlSessionTemplate",SqlSessionTemplate.class);
		List<Employee> employeeList=new ArrayList<Employee>();
		for(int i=0;i<10000;i++)
		{
			Employee emp = new Employee();
			emp.setAge(i);
			emp.setId(i);
			emp.setUsername("wang"+i);
			employeeList.add(emp);
		}
		System.out.println("开始插入1万条,时间："+new Date());
		
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("employeeList", employeeList);
		
		java.util.Date before=new java.util.Date();
		sqlSessionTemplate.insert("EmployeeMapper.insertEmployeeBatch",param); //用2秒 比 mybatis的session 用BATCH 快6秒
		// 受mysql参数bulk_insert_buffer_size影响,如果values使用#{} 绑定形式,可能会比事务中还要慢 
		//---
		AnnoationDao dao=sqlSessionTemplate.getMapper(AnnoationDao.class); 
		dao.insertEmployeeBatch(employeeList);
		
		java.util.Date after=new java.util.Date();
		System.out.println("insert into values传List方式  批处理1万条耗时毫秒数 " + (after.getTime()-before.getTime()) );
		
	}
	
	public  static void testBatchUseSpringTransaction(ApplicationContext ctx)
	{
		final  List<Employee> employees=new ArrayList<Employee>();
	    for(int i=0;i<10000;i++)
	    {
	        Employee e =new Employee();
	        e.setId(9000+i);
	        e.setUsername("batch"+i);
	        e.setPassword("password"+i);
	        e.setAge(i+20);
	        employees.add(e);
	    }
		final SqlSessionTemplate sqlSessionTemplate=ctx.getBean("sqlSessionTemplate",SqlSessionTemplate.class);
		TransactionTemplate transactionTemplate=ctx.getBean("transactionTemplate",TransactionTemplate.class);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				//如spring 的 MapperFactoryBean 或 MapperScannerConfigurer，直接得到Dao
				AnnoationDao dao=sqlSessionTemplate.getSqlSessionFactory().openSession().getMapper(AnnoationDao.class);
				java.util.Date before=new java.util.Date();
				for(Employee emp:employees)
				{
					dao.insert(emp);
				}
				java.util.Date after=new java.util.Date();
				System.out.println("transactionTemplate 批处理1万条耗时毫秒数 " + (after.getTime()-before.getTime()) );
			}
		});  
	}
	public  static void testBatchUseMybatisSession(ApplicationContext ctx)
	{
		SqlSessionTemplate sqlSessionTemplate=ctx.getBean("sqlSessionTemplate",SqlSessionTemplate.class);
	    SqlSession batchSqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(  ExecutorType.BATCH, false);
	    
		java.util.Date before=new java.util.Date();
		for(int i=0;i<10000;i++)
		{
			
//			Map<String,Object> param=new HashMap<String,Object>();
//			param.put("id", i);
//			param.put("username", "username"+i);
//			session.insert("EmployeeMapper.insert",param);
			
			AnnoationDao dao=batchSqlSession.getMapper(AnnoationDao.class);
			Employee emp = new Employee();
			emp.setAge(i);
			emp.setId(i);
			emp.setUsername("lisi"+i);
			dao.insert(emp);
			//---
//			batchSqlSession.insert("mybatis_spring.AnnoationDao.insert", emp);
			
			if(i%1000==0)
			{	
				batchSqlSession.commit();  //这种方式 不如 开启一个事务速度快 ， MySQL 用 insert into myTable('') values (''),(''),('')应该也不错
				 //用mysql 的jdbc Statement.executeBatch()很慢
				batchSqlSession.clearCache();  
			}
		}
		 java.util.Date after=new java.util.Date();
		 System.out.println("Mybatis BATCH session 批处理1万条耗时毫秒数 " + (after.getTime()-before.getTime()) );
		 //一次提交 这种要用8秒比insert into myTable('') values (''),('')慢6秒
	}
	
	
	public static void testWithSpringBatch(ApplicationContext ctx)
    {
	  //--分页读
	    
	  /*  
	    {//student
            MyBatisPagingItemReader<Student> pagingItemReaderStudent=ctx.getBean("pagingItemReader",MyBatisPagingItemReader.class);
            try
            {
               int pageSize=pagingItemReaderStudent.getPageSize();
               //pagingItemReader.setPageSize(5);
               
//               Map<String,Object> params=new HashMap<>();
//               params.put("idGt",9000);
//               pagingItemReaderStudent.setParameterValues(params);
               int initPageNo=pagingItemReaderStudent.getPage();//开始是0,第一次read()后变以1 ,如何指定从第几页开始读 ？？？
               
               Student  stu=null; 
              for (int i=0;i<pageSize  &&(stu=pagingItemReaderStudent.read())!=null  ;i++)  //只要第一页
              {
                   System.out.println(stu.getId()+"-" + stu.getName());
               }
               
            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
	    */
	    /*
	    { //emplyee 第二次再read返回就是null
    	    MyBatisPagingItemReader<Employee> pagingItemReader=ctx.getBean("pagingItemReader",MyBatisPagingItemReader.class);
    	    try
            {
    	        pagingItemReader.setQueryId("EmployeeMapper.getAll");
    	       int pageSize=pagingItemReader.getPageSize();
               //pagingItemReader.setPageSize(5);
               
    	       Map<String,Object> params=new HashMap<>();
               params.put("idGt",9000);
               pagingItemReader.setParameterValues(params);
               int initPageNo=pagingItemReader.getPage();//开始是0,第一次read()后变以1 ,如何指定从弟几页开始读 ？？？
               
    	       Employee  data=null; 
              for (int i=0;i<pageSize  &&(data=pagingItemReader.read())!=null  ;i++)  //只要第一页
              {
                   System.out.println(data.getId()+"-" + data.getUsername());
               }
               
            } catch (Exception e)
            {
                e.printStackTrace();
            }
	    }
	    */
	    
	    //--批量写 使用 BATCH的方式  ,没有transactionTemplate做事务速度快 
	    MyBatisBatchItemWriter<Employee> batchItemWriter=ctx.getBean("batchItemWriter",MyBatisBatchItemWriter.class);
	    List<Employee> items=new ArrayList<Employee>();
	    for(int i=0;i<10000;i++)
	    {
	        Employee e =new Employee();
	        e.setId(9000+i);
	        e.setUsername("batch"+i);
	        e.setPassword("password"+i);
	        e.setAge(i+20);
	        items.add(e);
	    }
	   
	    java.util.Date before=new java.util.Date();
	    
	    batchItemWriter.setAssertUpdates(false);//如是insert设置为false
	    batchItemWriter.write(items);//看源码是使用  ExecutorType.BATCH的SqlSessionTemplate
	    
	    java.util.Date after=new java.util.Date();
	    System.out.println("SpringBatch 批处理1万条耗时毫秒数 " + (after.getTime()-before.getTime()) );
	    
    }
	public static void testTransaction(ApplicationContext ctx)
	{
		
		Service service=(Service)ctx.getBean("servcie");
		service.doInTransactionUseTemplate();
//		service.doInTransactionUseAnnotation();
//		service.doInTransactionUseBatchSesssion();
	}
	public static void testNull(ApplicationContext ctx)
    {
	    Dao dao=(Dao)ctx.getBean("dao");
	    
        Employee e2=dao.getEmployeeByName("王");
        System.out.println(e2.getUsername());
        
        Employee e=dao.getEmployeeById(102);
        System.out.println(e.getUsername());
        
        //----
	    Service service=(Service)ctx.getBean("servcie");
        Employee emp = new Employee();
        emp.setAge(25);
        emp.setId(23232);
        emp.setUsername("lisi");
        //emp.setPassword("123");//配置<setting name="jdbcTypeForNull" value="NULL"/> 才可为null
        service.getAnnoationDao().insert(emp);
    }
	
	
}
