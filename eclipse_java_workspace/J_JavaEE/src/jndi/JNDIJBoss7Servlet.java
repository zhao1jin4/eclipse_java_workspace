package jndi;


import java.io.IOException;
import java.sql.Connection;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
@WebServlet(urlPatterns = "/jboss7")
public class JNDIJBoss7Servlet extends HttpServlet 
{
	
	public  void testJMS()throws Exception//.class在容器内OK
	{
		//standalone.xml	<subsystem xmlns="urn:jboss:domain:messaging:1.1">,从standalone-full.xml中复制的
		Context context=new InitialContext();//.class在容器内,不加参数
		ConnectionFactory factory = (ConnectionFactory)context.lookup("ConnectionFactory");//.class在容器内OK,ConnectionFactory对应的配置是java:/ConnectionFactory
		Queue queue = (Queue)  context.lookup("queue/test");//.class在容器内OK,queue/test对应的配置是 <entry name="queue/test"/>
        System.out.println("----------queue:"+queue);
	}
	public  void testDataSource()throws Exception
	{
		Context context=new InitialContext();
		Object str=context.lookup("java:global/mystring");//.class容器中OK,对应web.xml
		
// web.xml和jboss-web.xml  可有可无
//			web.xml
//				<resource-ref>
//				    <description>Example DS</description>
//				    <res-ref-name>datasources/ExampleDS</res-ref-name>
//				    <res-type>javax.sql.DataSource</res-type>
//				    <res-auth>Container</res-auth>
//				</resource-ref>
//			jboss-web.xml
//				<jboss-web>
//				    <resource-ref>
//				        <res-ref-name>datasources/ExampleDS</res-ref-name>
//				        <res-type>javax.sql.DataSource</res-type>
//				        <jndi-name>java:jboss/datasources/ExampleDS</jndi-name>
//				    </resource-ref>
//				</jboss-web>		
		//从standalone-full.xml中复制过来修改了java:/jboss 为java:/.
		DataSource ds=(DataSource)context.lookup("datasources/ExampleDS");//.class容器中OK,配置是<datasource jndi-name="java:/datasources/ExampleDS" pool-name="ExampleDS">
		Connection conn=ds.getConnection();
		System.out.println("H2 DataSource OK");
		conn.close();
		context.close();
	}
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException 
	{
		 
		try {
			//testJMS();
			testDataSource();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	}
}
