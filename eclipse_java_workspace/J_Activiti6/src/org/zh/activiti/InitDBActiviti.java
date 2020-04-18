package org.zh.activiti;
import org.junit.jupiter.api.Test; //Junit 5 

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
public class InitDBActiviti {
	
	/*
//mysql 
drop database  if exists activiti;
create database activiti default character set utf8;
CREATE USER bpmn@'%'  IDENTIFIED BY 'bpmn';
CREATE USER bpmn@localhost  IDENTIFIED BY 'bpmn';
grant all on activiti.* to 'bpmn'@'%'  ;
grant all on activiti.* to bpmn@localhost ;
	 */
	@Test  //Junit 5 ,单独建立项目引入.jar包可以执行
	public void createTable()
	{
		org.activiti.image.ProcessDiagramGenerator depsClass;
		de.odysseus.el.ExpressionFactoryImpl x;//juel-impl-2.2.7.jar
		//commons lang3
		org.joda.time.ReadablePeriod y; //joda-time-2.9.9.jar
		
		
		ProcessEngineConfiguration config =ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		config.setDataSource(dataSource());//也可指定数据源
		/*
		config.setJdbcDriver("com.mysql.cj.jdbc.Driver");//mysql 8
		config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/activiti?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
		config.setJdbcUsername("bpmn");
		config.setJdbcPassword("bpmn");
		*/
//		String schemaType=ProcessEngineConfiguration.DB_SCHEMA_UPDATE_CREATE_DROP;//先删表再建,但报索引已存在
		String schemaType=ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE;//如不存在表，就建
		config.setDatabaseSchemaUpdate(schemaType);
//		mysql
//		create database activiti default character set utf8;
//		CREATE USER bpmn@'%'  IDENTIFIED BY 'bpmn';
//		CREATE USER bpmn@localhost  IDENTIFIED BY 'bpmn';
//		grant all on activiti.* to 'bpmn'@'%'  ;
//		grant all on activiti.* to bpmn@localhost  ;
		
		
		//activiti-6.0.0\database\create  三个文件 
		//activiti.mysql.create.engine.sql 
		//activiti.mysql.create.history.sql
		//activiti.mysql.create.identity.sql
		
		config.buildProcessEngine();
		
	}
	public DataSource dataSource()
	{
		HikariConfig config = new HikariConfig();
		config.setDriverClassName("com.mysql.cj.jdbc.Driver");
		config.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/activiti?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
		config.setUsername("bpmn");
		config.setPassword("bpmn");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
	
		HikariDataSource ds = new HikariDataSource(config);
		return ds;
	}
	
	@Test
	public void createFromSpring()
	{ 
		//wars\activiti-rest.war\WEB-INF\classes\activiti-custom-context.xml  有 spring
		// 默认找id名为  processEngineConfiguration,可第二个参数修改
		ProcessEngineConfiguration config =ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
		ProcessEngine engine=config.buildProcessEngine();//可从spring配置中直接拿到processEngine
		engine.getRepositoryService();
		engine.getRuntimeService();
		engine.getTaskService();
		engine.getHistoryService();
		engine.getIdentityService();
		engine.getFormService(); 
		engine.getManagementService();
		
		//如需spring才要 activiti-spring-6.0.0.jar
		// activiti-process-validation-6.0.0.jar
		//activiti-dmn-api-6.0.0.jar
		// activiti-form-api-6.0.0.jar
		// activiti-bpmn-model-6.0.0.jar
		
		org.activiti.spring.SpringProcessEngineConfiguration spring;
	  
	}
	 
}
