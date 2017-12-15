package jta_weblogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import javax.transaction.Status;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import javax.transaction.xa.XAResource;

import org.h2.jdbcx.JdbcDataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

import oracle.jdbc.xa.client.OracleXADataSource;


public class JTA_Main 
{
	public static void testJDBCCloseTransaction() throws Exception//OK
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521/XE","hr","hr");
		conn.setAutoCommit(false);
		PreparedStatement pstmt = conn.prepareStatement("update oracle_score set score=score-1 where id=1"); 
        //PreparedStatement pstmt = conn.prepareStatement("insert into mysql_score(id,score)values(2,66)");
        pstmt.executeUpdate();//如不调conn.setAutoCommit(false), 事物提交
        pstmt.close();//加conn.setAutoCommit(false)这里事物不提交
        conn.close();//加conn.setAutoCommit(false)这里事物才提交
	}
	public static XADataSource newMySQLXADataSource()//OK
	{
		MysqlXADataSource mysqlXADS=new MysqlXADataSource(); //com.mysql.jdbc.jdbc2.optional.
		mysqlXADS.setUser("root");
		mysqlXADS.setPassword("root");
		String url="jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=UTF-8";
		mysqlXADS.setUrl(url);
		mysqlXADS.setURL(url);
		int port=3306;
		mysqlXADS.setPort(port);
		mysqlXADS.setPortNumber(port);
		mysqlXADS.setServerName("127.0.0.1");
		mysqlXADS.setDatabaseName("test");
		
		return mysqlXADS;
	}
	public static XADataSource newOracleXADataSource() throws Exception//OK
	{
		OracleXADataSource oracleXADS=new OracleXADataSource();//oracle.jdbc.xa.client.
		oracleXADS.setURL("jdbc:oracle:thin:@127.0.0.1:1521/XE");
		oracleXADS.setUser("hr");
		oracleXADS.setPassword("hr");
		oracleXADS.setPortNumber(1521);
		oracleXADS.setDatabaseName("XE");
		oracleXADS.setServerName("127.0.0.1");
		return oracleXADS;
	}
	public static XADataSource newH2XADataSource()//OK
	{
		JdbcDataSource h2XADS=new JdbcDataSource();//org.h2.jdbcx.
		h2XADS.setURL("jdbc:h2:tcp://localhost:9092/test");
		h2XADS.setUser("sa");
		h2XADS.setPassword("sa");
		return h2XADS;
	}
	
	public static void testConnection() throws Exception//测试OK
	{
		XADataSource mysqlXADS=newMySQLXADataSource();
		XAConnection mysqlXAConn=mysqlXADS.getXAConnection();
		
		XADataSource oracleXADS=newOracleXADataSource();
		XAConnection oracleXAConn=oracleXADS.getXAConnection();
		
		XADataSource h2XADS=newH2XADataSource();
		XAConnection h2XAConn=h2XADS.getXAConnection();
	}
	
	public static void main(String[] args)throws Exception
	{
		//testConnection();//测试OK
		testJDBCCloseTransaction();
		
		//以下未测试
		Properties properties = new Properties();
		properties.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
		properties.put(Context.PROVIDER_URL, "t3://127.0.0.1:7001");
		Context context;
		//weblogic.transaction.internal.ClientTransactionImpl trans;
		TransactionManager trans;
		//UserTransaction trans;
		try
		{
			context = new InitialContext(properties);
			trans=(TransactionManager)context.lookup("javax.transaction.UserTransaction");//是TransactionManager,也是UserTransaction,
			//trans=(UserTransaction)context.lookup("javax.transaction.UserTransaction");
			trans.setTransactionTimeout(5000);
			//trans.setRollbackOnly();
			int s=trans.getStatus();
			if(s==Status.STATUS_COMMITTED)
				System.out.println("Commited");
			
			//weblogic 选择XA JDBC driver后driver配置是oracle.jdbc.xa.client.OracleXADataSource
			Object obj= context.lookup("jdbc/oracleXA");
			 DataSource  oracleDs=(DataSource)obj;//不可强转到XADataSource
		
			 XADataSource oracleXADS=newOracleXADataSource();
			XAConnection oracleXAConn=oracleXADS.getXAConnection();
			XAResource oracleXAResource = oracleXAConn.getXAResource();
			trans.begin();
//			trans.getTransaction().enlistResource(oracleXAResource);//You may enlist a resource only on a server
			Connection oracleConn=oracleXAConn.getConnection();
			oracleConn.setAutoCommit(false);
			

			PreparedStatement oraclePrepare= oracleConn.prepareStatement("update employees set salary=salary-1 where employee_id=100");
			oraclePrepare.executeUpdate();
			
			
			XADataSource h2ADS=newH2XADataSource();
			XADataSource h2Ds=(XADataSource) context.lookup("jdbc/other");
			XAConnection h2XAConn=h2Ds.getXAConnection();
			Connection h2Conn=h2XAConn.getConnection();
			h2Conn.setAutoCommit(false);
			XAResource h2Resource=h2XAConn.getXAResource();
			
			PreparedStatement h2Prepare =h2Conn.prepareStatement("update stu_score set score=score+1 where id=1");
			h2Prepare.executeUpdate();
			
			
			
			//trans.commit();
			trans.rollback();//================
			
			h2Prepare.close();
			h2Conn.close();
			h2XAConn.close();
			
			oraclePrepare.close();//关闭必须在事务处理完成后
			oracleConn.close();
			oracleXAConn.close();
			
			
		} catch (Exception e)
		{
			e.printStackTrace();
			
		}
	}
}
