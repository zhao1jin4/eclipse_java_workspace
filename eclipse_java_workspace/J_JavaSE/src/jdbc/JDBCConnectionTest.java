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
		
		//MySQL JDBC Driver�ɲ�ָ��DB
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306?useUnicode=true&characterEncoding=UTF-8","zh","123");
		
		//Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=UTF-8","root","root");
		//Connection con=DriverManager.getConnection("jdbc:mysql://address=(protocol=tcp)(host=localhost)(port=3306)/mydb?useUnicode=true&amp;characterEncoding=UTF-8","user1","user1");
		
		
//		con.setTransactionIsolation(Connection.TRANSACTION_NONE);//mysql��֧��NONE
		//MySQL �� JDBC ֧�ֵ���ȫһ��
		con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
		con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);//Oracle Ĭ��
		con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);//MySQL Ĭ�� 
		con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		//Oracle ֻ֧��  READ COMMITTED  �� SERIALIZABLE; 
		
		Statement state=con.createStatement();
		state.executeUpdate("use mydb");
		con.setAutoCommit(false);
		 
		state.executeUpdate("create table student(id int,name varchar(20),grade DECIMAL(5,2))");
		con.createStatement().executeUpdate("insert into student(id,name,grade)values(1,'lisi',70.9)");
		
		PreparedStatement prepare=con.prepareStatement("select * from mydb.student");//Ҳ��ֱ�Ӽ����ݿ���
		ResultSet rs=prepare.executeQuery();
		while(rs.next())
			System.out.println("grade="+rs.getBigDecimal(3));//JDBC����ֱ��getBigDecimal

		Savepoint savePoint2=	con.setSavepoint("savePoint1");//JDBC����SavePoint,MySQLҲ��
		con.createStatement().executeUpdate("insert into student(id,name,grade)values(2,'wang',80.9)");
		
			Savepoint savePoint3=	con.setSavepoint("savePoint2");
			con.createStatement().executeUpdate("insert into student(id,name,grade)values(3,'sun',88.9)");
			con.commit();//���� commit ĳһ��savepoint,����Ƕ��ʽ����commit����rollback����
			
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
		Connection conn=DriverManager.getConnection("jdbc:h2:tcp://localhost:9092/test","sa","");//test�����ݿ���
		PreparedStatement prepare= conn.prepareStatement(" DROP TABLE IF EXISTS student");
		prepare.execute();
		prepare.close();
		
		prepare= conn.prepareStatement("create table student(id int primary key ,name varchar(20) )");
		prepare.execute();
		prepare.close();
		
		prepare= conn.prepareStatement("insert into student(id ,name ) values(?,?)");
		prepare.setInt(1,11);
		prepare.setString(2, "lisi����");
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
		Class.forName("oracle.jdbc.driver.OracleDriver");//Oracle AL32UTF8    ��   VARCHAR2ռ�����ֽڡ�, NVARCHAR2ռ����
		
		Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.18:1521/d0posb","mpmtdata","mpmtdata1234");
//		PreparedStatement p=conn.prepareStatement("insert into char_test values('����','��')");//fail
		PreparedStatement p=conn.prepareStatement("insert into char_test values('��','1234')");//OK
		
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
		PreparedStatement p =con.prepareStatement("insert into student values (1001,'����') ");
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
                        String val="AAֵ_"+i;
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
                       String val="BBֵ_"+i;
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
		ResultSet typeRS=dbMetaData.getTableTypes();//��TABLE��VIEW��SYSTEM TABLE ��SYSTEM VIEW
		while(typeRS.next())
		{
			System.out.println(typeRS.getString(1));
		}
		//MySQL8����
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
//		prepare.setInt(1, 1001);//������1��ʼ
		ResultSet rs=prepare.executeQuery();
		while(rs.next())
		{
			int id=rs.getInt(1);//������1��ʼ
			String name=rs.getString(2);
			System.out.println(id+name);
		}
		
		ResultSetMetaData tableMetaData=prepare.getMetaData();
		System.out.println("=======student�����:");
		int count=tableMetaData.getColumnCount();
		for(int i=1;i<=count;i++) 
		{
			System.out.println(tableMetaData.getColumnName(i)+":����"+tableMetaData.getColumnTypeName(i));//��1��ʼ
		}
		/*
			my_bit:����BIT
			my_blob:����BLOB
			my_binary:����BINARY
			my_varbinary:����VARBINARY
			my_enum:����CHAR
			my_set:����CHAR
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
