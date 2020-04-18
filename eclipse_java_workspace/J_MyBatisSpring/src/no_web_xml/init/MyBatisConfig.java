package no_web_xml.init;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource( value = "classpath:/mybatis-jdbc.properties")
@MapperScan(basePackages = "mybatis_generator.mapper")
public class MyBatisConfig
{
	@Autowired 
	Environment env;
	
	@Value("${jdbc.url}")
	private String jdbcUrl;
	
	@Value("${jdbc.driver}")
	private String driver;
	
	@Value("${jdbc.username}")
	//@Value("${username}")取值为系统用户名,属性文件中不能为username
	private String username;
	
	@Value("${jdbc.password}")
	private String password;
	
	@Bean
	public DataSource dataSource()
	{
//		String driver=env.getProperty("driver");
//		String jdbcUrl=env.getProperty("url");
//		String username=env.getProperty("username");//取值为系统用户名??
//		String password=env.getProperty("password");
		
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(driver);
		config.setJdbcUrl(jdbcUrl);
		config.setUsername(username);
		config.setPassword(password);
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
	
		HikariDataSource ds = new HikariDataSource(config);
		return ds;
	}
	@Bean  //@MapperScan中doc提示的JDBC事务
	public DataSourceTransactionManager transactionManager()
	{
		return new DataSourceTransactionManager(dataSource());
	}
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception
	{
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		return sessionFactory.getObject();
	}
}
