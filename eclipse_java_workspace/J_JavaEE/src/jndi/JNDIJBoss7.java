package jndi;

import java.sql.Connection;
import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JNDIJBoss7
{
	
	/*
	 java:comp - The namespace is scoped to the current component (i.e. EJB)
java:module - Scoped to the current module
java:app - Scoped to the current application
java:global - Scoped to the application server

java:jboss	服务器共享
java:/
java:jboss/exported 是jBoss 7.1, entries bound to this context are accessible over remote JNDI.


	 */
	
	
	public static void testDataSource()throws Exception
	{
		//jboss-as-7.1.1.Final\bin\client\jboss-client.jar
		//standalone.xml	<subsystem xmlns="urn:jboss:domain:datasources:1.0">
		//jboss-as-7.1.1.Final\modules\com\h2database\h2\main\module.xml
		final Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, org.jboss.naming.remote.client.InitialContextFactory.class.getName());
		env.put(Context.PROVIDER_URL, "remote://localhost:4447");
		env.put(Context.SECURITY_PRINCIPAL,"temp");//使用add-user.bat,Management User没用的,要Application User可以没有Role
		env.put(Context.SECURITY_CREDENTIALS,"$tata123");
		Context context=new InitialContext(env);//.class在容器外,要加参数
		
		DataSource ds=(DataSource)context.lookup("datasources/ExampleDS");//如配置java:jboss/exported/datasources/ExampleDS,不是找不到,而是其它错误
		//界面是正常启用,报java.io.NotSerializableException:?????? 
		Connection conn=ds.getConnection();
		
		System.out.println("Remote DataSource OK");
		conn.close();
		context.close();
	}
	public static void testJMS()throws Exception//.class在JBoss7容器外是OK
	{
		//jboss-as-7.1.1.Final\bin\client\jboss-client.jar
		//standalone.xml	<subsystem xmlns="urn:jboss:domain:messaging:1.1">,从standalone-full.xml中复制的
		//jboss-as-7.1.1.Final\modules\com\h2database\h2\main\module.xml
		final Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, org.jboss.naming.remote.client.InitialContextFactory.class.getName());
		env.put(Context.PROVIDER_URL, "remote://localhost:4447");
		env.put(Context.SECURITY_PRINCIPAL,"temp");//使用add-user.bat,Management User没用的,要Application User可以没有Role
		env.put(Context.SECURITY_CREDENTIALS,"$tata123");
		Context context=new InitialContext(env);//.class在容器外,要加参数
		
		ConnectionFactory factory = (ConnectionFactory)context.lookup("jms/RemoteConnectionFactory");//OK，对应配置java:jboss/exported/jms/RemoteConnectionFactory
        Queue queue = (Queue)  context.lookup("jms/queue/test");//OK，对应配置java:jboss/exported/jms/queue/test
        
	}
	public static void main(String[] args) throws Exception
	{
		//testJMS();//OK
		testDataSource();
	}

}
