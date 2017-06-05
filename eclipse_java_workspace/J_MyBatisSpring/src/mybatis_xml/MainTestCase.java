package mybatis_xml;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class MainTestCase extends TestCase 
{
	SqlSession session;
	
	protected void setUp() throws Exception 
	{
		String resource = "mybatis_xml/Configuration.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);//Properties,Enviroment
		session = sessionFactory.openSession();
	}
	protected void tearDown() throws Exception {
		session.close();
	}
	
	public static void testManualMybatis() throws Exception {
		
		ComboPooledDataSource dataSource= new  ComboPooledDataSource();
//		dataSource.setDriverClass("org.h2.Driver");
//		dataSource.setJdbcUrl("jdbc:h2:tcp://localhost/~/test");
//		dataSource.setUser("sa");
//		dataSource.setPassword("");
		
		dataSource.setDriverClass("oracle.jdbc.driver.OracleDriver");
		dataSource.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:XE");
		dataSource.setUser("myhr");
		dataSource.setPassword("myhr");
		
		dataSource.setInitialPoolSize(5);
		dataSource.setMinPoolSize(5);
		dataSource.setMaxPoolSize(20);
		dataSource.setMaxStatements(100);
		dataSource.setMaxIdleTime(3600);
		dataSource.setAcquireIncrement(2);
		dataSource.setAcquireRetryAttempts(10);
		dataSource.setAcquireRetryDelay(600);
		dataSource.setTestConnectionOnCheckin(true);
		dataSource.setIdleConnectionTestPeriod(1200);
		dataSource.setCheckoutTimeout(10000);
		
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Environment environment =new Environment("development", transactionFactory, dataSource);
		Configuration configuration = new Configuration(environment);
		configuration.addMapper(EmployeeMapperAnno.class);
		configuration.setLazyLoadingEnabled(true);
		configuration.setCacheEnabled(true);
		configuration.setDefaultStatementTimeout(3000);
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
		SqlSession session=sqlSessionFactory.openSession();
		
		
		session.close();
	}
	
	
	
	public  void testInsertEmpoloyee() throws Exception
	{
		for(int i =0;i<30;i++)
		{
			Employee emp=new Employee();
			//emp.setId(101+i);
			emp.setUsername("Java");
			emp.setBirthday(Calendar.getInstance().getTime());
			emp.setPassword(null);
			emp.setDepartment_id(10);
			session.insert("org.zhaojin.mybatis.ns.insertEmployee", emp);
			session.commit();
			System.out.println("insert new Employee:java");
		}
		
	}

	
	public  void testDeletePartEmployeeOfDept() throws Exception
	{
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("department_id", 10);
		List<Integer> ids=new ArrayList<Integer>();
		ids.add(101);
		ids.add(102);
		param.put("employee_ids", ids);
		
		session.delete("org.zhaojin.mybatis.ns.deletePartEmployeeOfDept", param);
		session.commit();
		
	}
	public  void testUpdateEmpoloyee() throws Exception
	{
		Employee emp=new Employee();
		emp.setId(102);
		emp.setUsername("Java");
		emp.setBirthday(Calendar.getInstance().getTime());
		emp.setPassword("145623");
		emp.setDepartment_id(10);
		session.update("org.zhaojin.mybatis.ns.updateEmployee", emp);
		session.commit();
		System.out.println("updateEmployee:java");
	}
	public  void testQueryEmpoloyee() throws Exception
	{
		//--RowBounds 内存分页,H2支持的,必须要用<where>才行
		int current=2;
		int pageSize=5;
		Map pageParam=new HashMap();
		pageParam.put("username", "Java");
		List<Employee>  employees =  session.selectList("org.zhaojin.mybatis.ns.queryAllEmployeeByPage",pageParam, new RowBounds( (current-1)*pageSize , pageSize));
		System.out.println("employees size="+employees.size());
		
		//--test for ehcache
		employees =  session.selectList("org.zhaojin.mybatis.ns.queryAllEmployee");
		System.out.println("employees size="+employees.size());
		
		employees =  session.selectList("org.zhaojin.mybatis.ns.queryAllEmployee");
		System.out.println("employees size="+employees.size());
	
		Employee employee = (Employee) session.selectOne("org.zhaojin.mybatis.ns.selectEmployee", 101);
		System.out.println("username:="+employee.getUsername());
		System.out.println("birthday:="+employee.getBirthday().toString());
		
		EmployeeDept userdept = (EmployeeDept) session.selectOne("org.zhaojin.mybatis.ns.selectEmpDept", 101);
		System.out.println("userdept:="+userdept.getDepartment());
		
		HashMap usermap = (HashMap) session.selectOne("org.zhaojin.mybatis.ns.selectEmpDeptHashMap", 101);
		System.out.println("userdept hash:dept="+usermap.get("DEPT"));
		Set<Map.Entry<String, Object>> set=usermap.entrySet();
		Iterator<Map.Entry<String, Object>> i=set.iterator();
		while(i.hasNext())
		{
			Map.Entry<String, Object> entry=i.next();
			System.out.println(entry.getKey()+"="+entry.getValue());
		}
		
		
		List<Employee> emps =  session.selectList("org.zhaojin.mybatis.ns.selectEmployeeByDept", 10);
		for (Iterator iterator = emps.iterator(); iterator.hasNext();) {
			Employee employee2 = (Employee) iterator.next();
			System.out.println("List:"+employee2.getUsername());
			
		}
		
		Employee param=new Employee();
		param.setId(105);
		//param.setUsername("Java");
		param.setBirthday(Calendar.getInstance().getTime());
		param.setPassword("123");
		param.setDepartment_id(10);
		List<Employee> emps2 =  session.selectList("org.zhaojin.mybatis.ns.dynSelectEmployee", param);
		for (Iterator iterator = emps2.iterator(); iterator.hasNext();) {
			Employee employee2 = (Employee) iterator.next();
			System.out.println("Dyna:"+employee2.getUsername());
			
		}
	}
	public  void testObjectRelationShip() throws Exception
	{
		
		List<Employee>  emps =  session.selectList("org.zhaojin.mybatis.ns.selectAllEmloyeeWithDept");
		for (Iterator iterator = emps.iterator(); iterator.hasNext();) {
			Employee employee = (Employee) iterator.next();
			System.out.println("association:"+employee.getUsername());
		}
		//---
		Employee  emp =  session.selectOne("org.zhaojin.mybatis.ns.selectEmloyeeWithDept", 101);
		System.out.println(emp.getUsername()+","+emp.getDepartment().getName());
		
		//=====
		List<Department>  depts =  session.selectList("org.zhaojin.mybatis.ns.selectAllDepartmentWithEmps", 10);
		for (Iterator iterator = depts.iterator(); iterator.hasNext();) {
			Department dept = (Department) iterator.next();
			System.out.println("Collection:"+dept.getName() +" have -----");
			System.out.println(dept.getName());
			List<Employee> emps2=dept.getEmps();
			for (Iterator iterator2 = emps2.iterator(); iterator2.hasNext();) {
				Employee employee2 = (Employee) iterator2.next();
				System.out.println("Collection:"+employee2.getUsername());
			}
		}
		//---
		Department dept =  (Department)session.selectOne("org.zhaojin.mybatis.ns.selectDepartmentWithEmps", 10);
		System.out.println(dept.getName());
		List<Employee> emps2=dept.getEmps();
		for (Iterator iterator = emps2.iterator(); iterator.hasNext();) {
			Employee employee2 = (Employee) iterator.next();
			System.out.println("Collection:"+employee2.getUsername());
		}
	 
		//===== discriminator
		
		List<Employee>  goodEmps =  session.selectList("org.zhaojin.mybatis.ns.selectGoodEmps");
		for (Iterator iterator = goodEmps.iterator(); iterator.hasNext();) {
			GoodEmployee employee = (GoodEmployee) iterator.next();
			System.out.println("good:"+employee.getUsername()+","+employee.getRaiseSalary());
		}
		List<Employee>  badEmps =  session.selectList("org.zhaojin.mybatis.ns.selectBadEmps");
		for (Iterator iterator = badEmps.iterator(); iterator.hasNext();) {
			BadEmployee employee = (BadEmployee) iterator.next();
			System.out.println("bad:"+employee.getUsername()+","+employee.getDeductSalary());
		}
		
	}
}
