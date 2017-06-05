package jndi;

import java.sql.Connection;
import java.util.Properties;

import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JNDIWebSphere 
{
	public static void jndi() throws Exception 
	{
		//C:\Program Files (x86)\IBM\WebSphere\AppServer\runtimes\com.ibm.ws.admin.client_8.5.0.jar  大小50MB ,com.ibm.ws.orb_8.5.0.jar 大小2M 
		//要用IBM的JAVA_HOME=C:\Program Files (x86)\IBM\WebSphere\AppServer\java,是1.6.0版本
		//MySQL的实现类名com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource
		Properties properties = new Properties();
		properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.ibm.websphere.naming.WsnInitialContextFactory");
		properties.put(Context.PROVIDER_URL, "iiop://localhost:2809/");
		InitialContext context = new InitialContext(properties);
		DataSource ds=(DataSource)context.lookup("jndi_mysql");//不要有/字符,不能加java:xx
		Connection conn=ds.getConnection();
	}
	public static void jms() throws Exception 
	{
		
		Properties properties = new Properties();
		properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.ibm.websphere.naming.WsnInitialContextFactory");
		properties.put(Context.PROVIDER_URL, "iiop://localhost:2809/");
		InitialContext context = new InitialContext(properties);
//		Queue queue=(Queue)context.lookup("jms/globalLog.Test");
//		QueueConnectionFactory queueFactory=(QueueConnectionFactory)context.lookup("jms/cepQCF.Test");
		Object obj=context.lookup("jdbc/auditDataSource"); 
		DataSource ds=(DataSource)context.lookup("jdbc/auditDataSource"); 
	}
	public static void main(String[] args) throws Exception 
	{
		//jndi();
		 jms();//未成功??
	}
}
