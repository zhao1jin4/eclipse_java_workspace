package  mysql_xdevapi;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.mysql.cj.xdevapi.Client;
import com.mysql.cj.xdevapi.ClientFactory;
import com.mysql.cj.xdevapi.Row;
import com.mysql.cj.xdevapi.RowResult;
import com.mysql.cj.xdevapi.Schema;
import com.mysql.cj.xdevapi.Session;
import com.mysql.cj.xdevapi.SessionFactory;
import com.mysql.cj.xdevapi.SqlResult;
import com.mysql.cj.xdevapi.Table;
public class XDevApiTableMain {
	public static void main(String[] args)throws Exception
	{
		String baseUrl="mysqlx://localhost:33060/mydb?user=zh&password=123";
		//String baseUrl="mysqlx://sandy:mypassword@[host1:33060,host2:33061]/test";//client-side failover 
		//  			mysqlx://sandy:mypassword@[(address=host1:33060,priority=2),(address=host2:33061,priority=1)]/test
			
		//Session mySession = new SessionFactory().getSession(baseUrl);
		//连接池
		ClientFactory cf = new ClientFactory(); 
		Client cli = cf.getClient(baseUrl, "{\"pooling\":{\"enabled\":true, \"maxSize\":8,\"maxIdleTime\":30000, \"queueTimeout\":10000} }");
		Session mySession = cli.getSession();


		//mysqlProcedure(mySession);
		//showDatabases(mySession);
		mysqlTable(mySession);//乱码
		transaction(mySession);
		
		
		mySession.close();
		cli.close();
		
		
	}
	public static void mysqlProcedure(Session mySession) {
		mySession.sql("USE mydb").execute();
		mySession.sql("CREATE PROCEDURE my_add_one_procedure " + " (INOUT incr_param INT) " + "BEGIN " + "  SET incr_param = incr_param + 1;" + "END")
		        .execute();
		mySession.sql("SET @my_var = ?").bind(10).execute();
		mySession.sql("CALL my_add_one_procedure(@my_var)").execute();
		mySession.sql("DROP PROCEDURE my_add_one_procedure").execute();
		SqlResult myResult = mySession.sql("SELECT @my_var").execute();
		Row row = myResult.fetchOne();
		System.out.println(row.getInt(0));
	}
	public static void showDatabases(Session mySession)
	{
		List<Schema> schemaList = mySession.getSchemas();
		System.out.println("Available schemas in this session:");
		for (Schema schema : schemaList) {
		System.out.println(schema.getName());
		}
	}
 
	public static void mysqlTable(Session session) throws Exception
	{
		Schema  db= session.getSchema("mydb");
		
		// New method chaining used for executing an SQL SELECT statement
		// Recommended way for executing queries
		Table employees = db.getTable("employee");

		RowResult res = employees.select("username, age")
		  .where("username like :param")
		  .orderBy("username")
		  .bind("param", "李").execute(); //可以使用%通配符
		while(res.hasNext())//类似JDBC
		{
			Row row=res.next();
			System.out.println(row.getString(0) + row.getInt(1));//从0开始,和JDBC不同
			System.out.println(row.getString("username") + row.getInt("age"));
			//中文乱码
		}
		
		// Traditional SQL execution by passing an SQL string
		// It should only be used when absolutely necessary
		SqlResult result = session.sql("SELECT username, age " +
		  "FROM employee " +
		  "WHERE username like ? " +
		  "ORDER BY username").bind("王").execute(); //sql方法参数要用?
		 
		List<Row> rows1=result.fetchAll();//另一种方式
		for(Row row:rows1)
		{
			System.out.println(row.getString(0) + row.getInt(1)); 
			System.out.println(row.getString("username") + row.getInt("age")); 
		}
		 
		// execute the query asynchronously, obtain a future
		CompletableFuture<RowResult> rowsFuture = employees.select("username", "age")
		  .where("username like :name")
		  .orderBy("username")
		  .bind("name", "m%").executeAsync();
		
		// wait until it's ready
		RowResult rows = rowsFuture.get();
	} 
	public static void transaction(Session mySession) {
		
		Schema  db= mySession.getSchema("mydb");
		Table employeeTable = db.getTable("employee");
		
		mySession.startTransaction(); 

		employeeTable.insert("id", "username","age")
		  .values(2002, "张2",32)
		  .values(2003, "张3",33)
		  .execute();
		//setSavepoint嵌套事务
		mySession.setSavepoint("level1");
		
//		mySession.sql("update   employee set  age=? where id=? ").bind(25).bind(2003).execute();
		
		//https://dev.mysql.com/doc/x-devapi-userguide/en/sql-crud-functions.html
		//set用:变量不行？？？ //expr( "age + 1") 如何写表达式？？
//		employeeTable.update().set("age", ":age").set("username", ":username").where("id= :id")
//					.bind("age", 25).bind("username", "张3").bind("id",2003).execute();
		 
		//OK
		employeeTable.update().set("age", 25).set("username", "张3").where("id= :id")
		 .bind("id",2003).execute();
		
		mySession.setSavepoint("level2");
		employeeTable.delete().where("id= :id").bind("id",2003);
		
		mySession.rollbackTo("level2");
		//mySession.rollbackTo("level1");
		mySession.commit();
	}
}
