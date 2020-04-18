package datasource.hikari;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import init.ConfigurationProperties;
public class MainHikariCP {
/*
Spring Boot 优先使用
https://github.com/brettwooldridge/HikariCP
    <dependency>
        <groupId>com.zaxxer</groupId>
        <artifactId>HikariCP</artifactId>
        <version>3.4.1</version>
    </dependency>
 */
	public static void main(String[] args) throws Exception {
		
		HikariConfig config = new HikariConfig();
//		com.mysql.jdbc.Driver  //MySQL 5.x
		config.setDriverClassName("com.mysql.cj.jdbc.Driver"); //MySQL 8
		config.setJdbcUrl("jdbc:mysql://localhost:3306/mydb");
		config.setUsername("zh");
		config.setPassword("123");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
 
		//源码是 new File的形式,不支持MySQL
		//HikariConfig config = new HikariConfig("/mnt/vfat/NEW_/eclipse_java_workspace/J_MyBatisSpring/src/datasource/hikari/db.properties");
		
		HikariDataSource ds = new HikariDataSource(config);
		Connection conn=ds.getConnection();
		PreparedStatement prepare=conn.prepareStatement("select now() where 1=?");
		prepare.setInt(1, 1);
		ResultSet rs=prepare.executeQuery();
		while(rs.next())
			System.out.println(rs.getTimestamp(1));
		rs.close();
		prepare.close();
		conn.close();
		ds.close();
	}

}
