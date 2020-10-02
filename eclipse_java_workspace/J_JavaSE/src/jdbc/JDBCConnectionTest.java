package jdbc;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;


public class JDBCConnectionTest
{
	
	public static void  testConnectDB2()throws Exception
	{

		Class.forName("com.ibm.db2.jcc.DB2Driver"); 
		String url="jdbc:db2://localhost:5000/sample"; 
		String remoteDB2Url ="jdbc:db2://10.1.5.226:8000/sample";
		Connection con=DriverManager.getConnection(remoteDB2Url,"db2instl","123");
		Statement state=con.createStatement();
		ResultSet result =state.executeQuery("select * from employee");
		while(result.next())
		{
			System.out.println(result.getString(1)+":"+result.getString("lastname"));
			
		}
		result.close();
		state.close();
		con.close();
	}
	public static void testConnectSQLServer()throws Exception
	{
		Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
		Connection con=DriverManager.getConnection("jdbc:microsoft:sqlserver://localhost:1433;DataBaseName=northwind","sa","");
		Statement state=con.createStatement();
		ResultSet result =state.executeQuery("select * from games");
		while(result.next())
		{
			System.out.println(result.getInt(1)+":"+result.getString("name"));
			
		}
		//sate.executeUpdate("delete  from games where id=8 ");
		result.close();
		state.close();
		con.close();
	}public static void testConnectMySQL()throws Exception
	{
		
		//Class.forName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
		//Class.forName("com.mysql.jdbc.Driver");
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		//MySQL JDBC Driver可不指定DB
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306?useUnicode=true&characterEncoding=UTF-8","zh","123");
		
		//Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=UTF-8","root","root");
		//Connection con=DriverManager.getConnection("jdbc:mysql://address=(protocol=tcp)(host=localhost)(port=3306)/mydb?useUnicode=true&amp;characterEncoding=UTF-8","user1","user1");
		
		
//		con.setTransactionIsolation(Connection.TRANSACTION_NONE);//mysql不支持NONE
		//MySQL 与 JDBC 支持的完全一致
		con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
		con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);//Oracle 默认
		con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);//MySQL 默认 
		con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		//Oracle 只支持  READ COMMITTED  和 SERIALIZABLE; 
		
		Statement state=con.createStatement();
		state.executeUpdate("use mydb");
		con.setAutoCommit(false);
		 
		state.executeUpdate("create table student(id int,name varchar(20),grade DECIMAL(5,2))");
		con.createStatement().executeUpdate("insert into student(id,name,grade)values(1,'lisi',70.9)");
		
		PreparedStatement prepare=con.prepareStatement("select * from mydb.student");//也可直接加数据库名
		ResultSet rs=prepare.executeQuery();
		while(rs.next())
			System.out.println("grade="+rs.getBigDecimal(3));//JDBC可以直接getBigDecimal

		Savepoint savePoint2=	con.setSavepoint("savePoint1");//JDBC可以SavePoint,MySQL也可
		con.createStatement().executeUpdate("insert into student(id,name,grade)values(2,'wang',80.9)");
		
			Savepoint savePoint3=	con.setSavepoint("savePoint2");
			con.createStatement().executeUpdate("insert into student(id,name,grade)values(3,'sun',88.9)");
			con.commit();//不能 commit 某一个savepoint,不能嵌入式事务，commit后再rollback报错
			
		con.rollback(savePoint2);
		 
		//state.close();
		con.close();
	
	}
	public static void testConnectOracle()throws Exception
	{
		Class.forName("oracle.jdbc.xa.client.OracleXADataSource");
		//Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe","hr","hr");
		conn.close();
	}
	public static void testConnectH2()throws Exception
	{
		Class.forName("org.h2.jdbcx.JdbcDataSource");
		//Class.forName("org.h2.Driver");
		//jdbc:h2:tcp://localhost:9092/test
		//jdbc:h2:tcp://localhost/~/test
		//jdbc:h2:mem
		Connection conn=DriverManager.getConnection("jdbc:h2:tcp://localhost:9092/test","sa","");//test是数据库名
		PreparedStatement prepare= conn.prepareStatement(" DROP TABLE IF EXISTS student");
		prepare.execute();
		prepare.close();
		
		prepare= conn.prepareStatement("create table student(id int primary key ,name varchar(20) )");
		prepare.execute();
		prepare.close();
		
		prepare= conn.prepareStatement("insert into student(id ,name ) values(?,?)");
		prepare.setInt(1,11);
		prepare.setString(2, "lisi中文");
		prepare.execute();
		
		
		prepare.close();
		conn.close();
	}
	
	public static void testHSQL() throws Exception 
	{
		String url="jdbc:hsqldb:hsql://localhost:9002/test";
		Class.forName("org.hsqldb.jdbcDriver");
		Connection conn=DriverManager.getConnection(url,"sa","");
//		 D:\program>java -cp hsqldb.jar org.hsqldb.Server -database.0 D:\Program\hsqldb_1_8_1_2\hsqldb\lib\test -dbname.0 test -port 9002
//
//		 java -classpath hsqldb.jar org.hsqldb.util.DatabaseManagerSwing
//		 java -cp hsqldb.jar org.hsqldb.util.DatabaseManager
	}
	
	
	public static void testOracleNvarchar2() throws Exception
	{
		// CREATE TABLE char_test (f_var VARCHAR2(4),n_var NVARCHAR2(4));
		//select userenv('language') from dual;
		Class.forName("oracle.jdbc.driver.OracleDriver");//Oracle AL32UTF8    中   VARCHAR2占三个字节　, NVARCHAR2占两个
		
		Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.18:1521/d0posb","mpmtdata","mpmtdata1234");
//		PreparedStatement p=conn.prepareStatement("insert into char_test values('李四','四')");//fail
		PreparedStatement p=conn.prepareStatement("insert into char_test values('李','1234')");//OK
		
		p.executeUpdate();
		conn.close();
	}
	
	public static void testMySQLutf8mb4() throws Exception
	{
		// create table student(id int,name varchar(4));
		//create database mydbutf8mb4 default character set utf8mb4;
		Class.forName("com.mysql.jdbc.Driver");
		//Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydbutf8mb4?useUnicode=true&amp;characterEncoding=UTF-8","user1","user1");
		Connection con=DriverManager.getConnection("jdbc:mysql://address=(protocol=tcp)(host=localhost)(port=3306)/test?useUnicode=true&amp;characterEncoding=UTF-8","user1","user1");
		PreparedStatement p =con.prepareStatement("insert into student values (1001,'别墅') ");
		p.executeUpdate();
		
	}
	public static void testMySQLAutoIncrement() throws Exception
    {
	    // create table student(id int not null auto_increment primary key  ,name varchar(10));
	    
        Class.forName("com.mysql.jdbc.Driver");
       final CyclicBarrier cyclic =new CyclicBarrier(2);
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        
          new Thread()
          {
            @Override
            public void run()
            {
                try
                {
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=UTF-8","user1","user1");
                    PreparedStatement p =con.prepareStatement("insert into student(name) values (?) ");
                    cyclic.await();
                    for(int i=0;i<50;i++)
                    {
                        String val="AA值_"+i;
                        System.out.println(val);
                        p.setString(1,val );
                        p.executeUpdate();
                    }
                    countDownLatch.countDown();  
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
               
            }
         }.start();
      
         new Thread()
         {
           @Override
           public void run()
           {
               try
               {
                   Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=UTF-8","user1","user1");
                   PreparedStatement p =con.prepareStatement("insert into student(name) values (?) ");
                   cyclic.await();
                   for(int i=0;i<50;i++)
                   {
                       String val="BB值_"+i;
                       System.out.println(val);
                       p.setString(1, val);
                       p.executeUpdate();
                   }
                   countDownLatch.countDown();  
               } catch (Exception e)
               {
                   e.printStackTrace();
               }
           }
        }.start();
        
        countDownLatch.await();
    }
	
	
	public static void  testMySQLMetaData()throws Exception
	{
		// create table student(id int,name varchar(4));
		com.mysql.cj.jdbc.Driver d;
		//Class.forName("com.mysql.jdbc.Driver");
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		//&useSSL=true
		Connection conn=DriverManager.getConnection("jdbc:mysql://address=(protocol=tcp)(host=localhost)(port=3306)/mydb?useUnicode=true&characterEncoding=UTF-8","user1","user1");
		DatabaseMetaData dbMetaData= conn.getMetaData();
		ResultSet typeRS=dbMetaData.getTableTypes();//有TABLE，VIEW，SYSTEM TABLE ，SYSTEM VIEW
		while(typeRS.next())
		{
			System.out.println(typeRS.getString(1));
		}
		//MySQL8报错
		//ResultSet tablesRS=dbMetaData.getTables(null, null, null, new String[]{"TABLE"});
//		ResultSet tablesRS=dbMetaData.getTables(null, null, null, null);
//		while(tablesRS.next())
//		{
//			System.out.println(tablesRS.getString("TABLE_NAME"));
//		}
		
		String dbName=dbMetaData.getDatabaseProductName();//Oracle   -----   MySQL
		int dbMajor=dbMetaData.getDatabaseMajorVersion();//11       -------- 8
		System.out.println("dbName="+dbName+",dbMajor="+dbMajor);//
		
		PreparedStatement prepare= conn.prepareStatement("select * from data_type  ");
//		prepare.setInt(1, 1001);//索引以1开始
		ResultSet rs=prepare.executeQuery();
		while(rs.next())
		{
			int id=rs.getInt(1);//索引以1开始
			String name=rs.getString(2);
			System.out.println(id+name);
		}
		
		ResultSetMetaData tableMetaData=prepare.getMetaData();
		System.out.println("=======student表的列:");
		int count=tableMetaData.getColumnCount();
		for(int i=1;i<=count;i++) 
		{
			System.out.println(tableMetaData.getColumnName(i)+":类型"+tableMetaData.getColumnTypeName(i));//从1开始
		}
		/*
			my_bit:类型BIT
			my_blob:类型BLOB
			my_binary:类型BINARY
			my_varbinary:类型VARBINARY
			my_enum:类型CHAR
			my_set:类型CHAR
		 */
		conn.close();
	}
	public static void main(String[] args) throws Exception
	{
		//testConnectOracle();
		//testConnectH2();
		//testConnectDB2();
		//testOracleNvarchar2();
		
		testConnectMySQL();
		//testMySQLutf8mb4();
		//testMySQLMetaData();
		//testMySQLAutoIncrement();
	}

}
