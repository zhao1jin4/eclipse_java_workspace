package mybatis;
 
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.DispatcherServlet;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@SpringBootApplication
@MapperScan("mybatis.dao")  //MyBatis Mapper
@ComponentScan
public class MybatisSpringBoot {
	public static void main(String[] args) {
		SpringApplication.run(MybatisSpringBoot.class, args);
	}
	
//	 @Bean  SpringBoot 2.0无用
//	    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
//	        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
//	        registration.getUrlMappings().clear();
//	        registration.addUrlMappings("*.action"); //只有*.action 的请求能通过
//	        registration.addUrlMappings("*.json");
//	        return registration;
//	    }

		/*
		加
		spring.datasource.url=jdbc:mysql://localhost:3306/mydb
		spring.datasource.username=root
		spring.datasource.password=root
		spring.datasource.driver-class-name=com.mysql.jdbc.Driver
		 就不用下面了
		 
		 //#references doc 84.1 Configure a Custom DataSource
	    @Bean(name = "dataSource")
	    @Qualifier(value = "dataSource")
	    @Primary
	    @ConfigurationProperties(prefix = "c3p0")
	    public DataSource dataSource() {
	    	 return DataSourceBuilder.create().type(com.mchange.v2.c3p0.ComboPooledDataSource.class).build();
	    }
 		*/
	
		/*
 		加 
 		 mybatis.mapper-locations=classpath:mapper/*Mapper.xml  
 		 就不用下面了
 		 
	    @Bean
	    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
	        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
	        sqlSessionFactoryBean.setDataSource(dataSource());//数据源可通过其它方式取得
	        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
	        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/*Mapper.xml"));//映射文件是resource/mybatis/目录下所有.xml文件,也可用application.properties
	        return sqlSessionFactoryBean.getObject();
	    }

	    @Bean
	    public PlatformTransactionManager transactionManager() {
	        return new DataSourceTransactionManager(dataSource());
	    }
	    */
	   
}
 
