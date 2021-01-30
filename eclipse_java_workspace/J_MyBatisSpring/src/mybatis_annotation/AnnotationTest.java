package mybatis_annotation;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class AnnotationTest
{ 
	static SqlSession session;
	public static void main(String[] args) {
		String resource = "mybatis_annotation/Configuration.xml";
		Reader reader=null;
		try {
			reader = Resources.getResourceAsReader(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);//Properties,Enviroment
		sessionFactory.getConfiguration().addMapper(UserDao.class);//加带Annotation的类
		sessionFactory.getConfiguration().addMapper(JobDao.class);
		//sessionFactory.getConfiguration().addMapper(JoinData.class);
		//sessionFactory.getConfiguration().addResultMap(rm);
		
		
		
		session = sessionFactory.openSession();
		//session=sessionFactory.openSession(ExecutorType.BATCH, false);//像是开新的连接，在连接池外的，像是
	 
	}
	@BeforeClass
	public static void init()//必须是static
	{
		String resource = "mybatis_annotation/Configuration.xml";
		Reader reader=null;
		try {
			reader = Resources.getResourceAsReader(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);//Properties,Enviroment
		sessionFactory.getConfiguration().addMapper(UserDao.class);//加带Annotation的类
		sessionFactory.getConfiguration().addMapper(JobDao.class);
		sessionFactory.getConfiguration().addMapper(JoinData.class);
		//sessionFactory.getConfiguration().addResultMap(rm);
 
//		sessionFactory.getConfiguration().getTypeHandlerRegistry().register(BooleanTypeHandler.class);//  调用不到BooleanTypeHandler ??
		

		session = sessionFactory.openSession();
		//session=sessionFactory.openSession(ExecutorType.BATCH, false);//像是开新的连接，在连接池外的，像是
		
	}
	@AfterClass
	public static void destory()
	{
		
	}
	
	@Test
	public void  addDataTest()
	{
		UserDao userDao = session.getMapper(UserDao.class);
		
		User user = new User();
		//没有ID
		user.setUserName("hongye");
		user.setPassword("123456");
		user.setComment("备注");
		
		user.setLikeColor(Color.Black);
//		user.setGender(true);
//		user.setManager(true);
		
		user.setGender(false);
		user.setManager(false);
		
		userDao.insert(user);
		
		System.out.println("记录条数："+userDao.countAll());
		List<User> users = userDao.selectAll();
		Iterator<User> iter = users.iterator();
		while(iter.hasNext()){
		    User u = iter.next();
		    System.out.println("用户名："+u.getUserName()+"密码："+u.getPassword());
		}
		
		User user1 = new User();
		user1.setUserName("zh");
		user1.setPassword("test");
		user1.setComment("备注123");
		userDao.providerAddData(user1);
		
		System.out.println("---provider");
		List<User> users1 =userDao.providerGetAll();
		Iterator<User> iter1 = users1.iterator();
		while(iter1.hasNext()){
		    User u = iter1.next();
		    System.out.println("用户名："+u.getUserName()+"密码："+u.getPassword());
		}
		
		User user2 =userDao.providerGetByUserName("zh");
		System.out.println("用户名："+user2.getUserName()+"密码："+user2.getPassword());
 
		int count=userDao.providerGetCount();
		System.out.println("count="+count);
		
		session.commit();
		session.close();
	 }
	@Test
	public void  updateDataTest()
	{
		UserDao userDao = session.getMapper(UserDao.class);
		 
		User u = userDao.findByUserName("hongye"); //要只返回一行
		u.setComment("comment");
		userDao.update(u);
		
		User user1 = new User();
		user1.setUserName("zh");
		user1.setPassword("test789");
		user1.setComment("备注123789");
		
		userDao.providerUpadteData(user1);
		session.commit();
		session.close();
		System.out.println(u.getComment());
	}
	@Test
	public void  deleteDataTest()
	{
		UserDao userDao = session.getMapper(UserDao.class);
		//userDao.delete("hongye");
		userDao.deleteByTwo("hongye","123456");//不能使用同名重载方法的方式
 
		//userDao.providerDelete("zh");
		session.commit();
		session.close();
	}
	@Test
	public void typeTest()
	{
 		JobDao jobDao = session.getMapper(JobDao.class);
		List<Job> jobs=jobDao.getJobsByUserId(1);  // 有时 Job implements Serializable
		for (int i=0;i<jobs.size();i++)
		{
			System.out.println(jobs.get(i).getJobTitle());
			System.out.println(jobs.get(i).getRequirement().toString());
		}
		
		Job job=new Job();
		job.setStartDate(new Date());
		job.setEndDate(new Date());
		job.setJobTitle("Tester ");
		List<String> req=new ArrayList<>();
		req.add("Auto Test");
		req.add("LoadRunner");
			
		job.setRequirement(req);
		job.setLevel(LevelEnum.ONE); //默认支持enum类型，是以名字name()返回
		jobDao.saveJob(job);
		
		session.commit();
		session.close();
	}
	
	@Test
	public void pageMapTest()
	{
		UserDao userDao = session.getMapper(UserDao.class);
		Map<String,String> params=new HashMap<String,String>();
		params.put("userName", "lisi");
		params.put("password", "123");
		long recouds=userDao.getRecordCountByMap(params);
		System.out.println(recouds);
		
		//默认一级缓存是开启的，下面同一个会话，相同的查询，不会再输入SQL日志
		long recouds2=userDao.getRecordCountByMap(params);
		System.out.println(recouds2);
		
//		params.put("start","1");//Oracle ,H2
//		params.put("end","20");
		
		Map<String,Object> params2=new HashMap<String,Object>();
		params2.put("start",0); //MySQL
		params2.put("len",1);
		List<User> user=userDao.queryByPage(params2);
		System.out.println(user);
		
	}
	@Test
	public void cacheTest() 
	{

		/*
		//test JDBC cache
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useUnicode=true&amp;characterEncoding=UTF-8", "user1", "user1");
			
			PreparedStatement prepare=conn.prepareStatement("select * from user");
			ResultSet rs=prepare.executeQuery();
			while(rs.next())
			{
				System.out.println(rs.getString(1));
			}
			rs.close();
			prepare.close();
			
			//JDBC no cache
			  prepare=conn.prepareStatement("select * from user");
			  rs=prepare.executeQuery();
			while(rs.next())
			{
				System.out.println(rs.getString(1));
			}
			rs.close();
			prepare.close();
			
			conn.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		*/
		
		UserDao userDao = session.getMapper(UserDao.class);
		List<User> users = userDao.selectAll();
		Iterator<User> iter = users.iterator();
		while(iter.hasNext()){
		    User u = iter.next();
		    System.out.println("用户名："+u.getUserName()+"密码："+u.getPassword());
		}
		
		//Cache 测试
		 users = userDao.selectAll();
		  iter = users.iterator();
		while(iter.hasNext()){
		    User u = iter.next();
		    System.out.println("用户名："+u.getUserName()+"密码："+u.getPassword());
		}
		
		
	}
	/*
	 当java.util.Date传入mybatis 再到Oracle的date 类型转  会导致无法使用索引,使用java.sql.Date就会使用索引
	 如是oracle是 timestamp 类型无论是util.Date还是sql.Date都使用索引
	 
	 而MySQL可以直接传字串就OK
	 
 select status, sql_id, sql_child_number from v$session where status='ACTIVE' and ....

select sql_id, child_number, sql_text from v$sql where  sql_text like '%关键字%';
 
为了获取缓存库中的执行计划， v$sql_plan    ,  v$sql_plan_statistics_all 
也可 select * from table(dbms_xplan.display_cursor('sql_id',child_number)); 

	 */
	@Test
	public void oracleDatePerformance() 
	{
		
		JobDao jobDao = session.getMapper(JobDao.class);
		
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.YEAR,2012);
		calendar.set(Calendar.MONTH,00);//0开始
		calendar.set(Calendar.DAY_OF_MONTH,02);
		calendar.set(Calendar.HOUR_OF_DAY,22);
		calendar.set(Calendar.MINUTE,14);
		calendar.set(Calendar.SECOND,23);
		
		java.util.Date startDate=calendar.getTime();//2012-01-02 22:14:23
		List<Job> jobs=jobDao.getJobByStartDate(new java.sql.Date(startDate.getTime()));//oracle的date类型 ,使用java.sq.Date 会使用索引
		//List<Job> jobs=jobDao.getJobByStartDate( startDate);//oracle的date类型 ,使用java.util.Date 不会使用索引
		System.out.println(jobs);
		
		
//		List<Job> jobs = jobDao.getJobsByPK(2);
//		System.out.println(jobs);
	}
	@Test
	public void resultMapTest() 
	{
		UserDao userDao = session.getMapper(UserDao.class);
		JoinData data2=userDao.getWorksResultMap("lisi");
		System.out.println(data2.getUsername()+":"+data2.getWorks());
	}
	
	
	@Test //@Many
	public void collectionManyTest() 
	{
		UserDao userDao = session.getMapper(UserDao.class);
		User u1=userDao.getUserCollectionJobs();
		System.out.println(u1.getJobs());
	}
	@Test // @One 
	public void associationOneTest() 
	{
		JobDao jobDao = session.getMapper(JobDao.class);
		Job j1=jobDao.getJobAssociationUser();
		System.out.println(j1.getUser());
	}
	
	//-----------测试中
	@Test
	public void aliseTest() 
	{
		//@Alias("joinData")//何用？？？
		UserDao userDao = session.getMapper(UserDao.class);
		JoinData data=userDao.getWorks("lisi");
		System.out.println(data.getUsername()+":"+data.getWorks());
	}

	
	//-----------测试中 
	 
}
