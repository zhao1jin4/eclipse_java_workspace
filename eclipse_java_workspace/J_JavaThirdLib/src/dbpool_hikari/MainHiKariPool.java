package dbpool_hikari;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

import com.mysql.cj.jdbc.MysqlXADataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MainHiKariPool {
	public static void rowSetFactory() throws Exception
	{
		//com.mysql.cj.jdbc.Driver x;
		// Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();//rowSet也要driver
		//rowSetFactory也不需要Driver
		RowSetFactory  rowSetFactory = RowSetProvider.newFactory();//用缺省的RowSetFactory 实现 
		JdbcRowSet rowSet = rowSetFactory.createJdbcRowSet();  
		String url="jdbc:mysql://localhost:3306/mydb";
		rowSet.setUrl(url); 
		rowSet.setUsername("user1"); 
		rowSet.setPassword("user1"); 
		rowSet.setCommand("select current_date"); 
		rowSet.execute(); 
		while(rowSet.next())
		{
		  System.out.println(rowSet.getDate(1));
		}
		rowSet.close();
	}
	
	public static DataSource  getDataSourceWithConfig() 
	{ 
		//不需要driver,rowSetFactory也不需要Driver
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://localhost:3306/mydb");
		config.setUsername("user1");
		config.setPassword("user1");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

		HikariDataSource ds = new HikariDataSource(config);
		return ds;
	}
	public static  DataSource  getDataSource() 
	{  //或者不需要Config
		HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl("jdbc:mysql://localhost:3306/mydb");
		ds.setUsername("user1");
		ds.setPassword("user1");
		return ds;
	}
	public static  DataSource  getDataSourcePropFile() 
	{
		com.mysql.cj.jdbc.MysqlXADataSource x;
		HikariConfig config = new HikariConfig("/dbpool_hikari/hikari.properties");
		HikariDataSource ds = new HikariDataSource(config);
		return ds; 
	}
	public static void main(String[] args) throws Exception { 
		rowSetFactory();
		
		//DataSource ds=getDataSourceWithConfig() ;
		//DataSource ds=getDataSource() ;
		DataSource ds=getDataSourcePropFile() ;
		
		Connection conn=ds.getConnection();
		
		PreparedStatement prepare=conn.prepareStatement("select current_date");
		ResultSet rs=prepare.executeQuery();
		rs.next();
		System.out.println(rs.getDate(1));
		conn.close();
	}

}
