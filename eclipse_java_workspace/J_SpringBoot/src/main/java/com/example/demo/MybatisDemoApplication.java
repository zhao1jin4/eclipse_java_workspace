package com.example.demo;
/*
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.DispatcherServlet;

//@SpringBootApplication
@EnableCaching//Redis
@MapperScan("com.example.demo.dao")  //MyBatis
@ServletComponentScan //使用注解的方式注册servlet需要在SpringbootHelloApplication.java中添加@ServletComponentScan注解
@ComponentScan
public class MybatisDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisDemoApplication.class, args);
	}
	
	 @Bean
	    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
	        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
	        registration.getUrlMappings().clear();
	        registration.addUrlMappings("*.action"); //只有*.action 的请求能通过
	        registration.addUrlMappings("*.json");
	        return registration;
	    }

	    //DataSource配置
	    @Bean
	    @ConfigurationProperties(prefix="spring.datasource")
	    public DataSource dataSource() {
	        return new org.apache.tomcat.jdbc.pool.DataSource();
	    }

	    //提供SqlSeesion
	    @Bean
	    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {

	        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
	        sqlSessionFactoryBean.setDataSource(dataSource());

	        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

	        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/*Mapper.xml"));//映射文件是resource/mybatis/目录下所有.xml文件

	        return sqlSessionFactoryBean.getObject();
	    }

	    @Bean
	    public PlatformTransactionManager transactionManager() {
	        return new DataSourceTransactionManager(dataSource());
	    }
}
*/
