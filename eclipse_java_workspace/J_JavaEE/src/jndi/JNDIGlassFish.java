package jndi;

import java.sql.Connection;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JNDIGlassFish
{

	public static void main(String[] args) throws Exception
	{
		//C:\glassfish4\glassfish\modules\glassfish-naming.jar ,eclipse引入后会自动引用其它的.jar
		//必须把编译后的class运行在glassfish中才行
		//com.sun.enterprise.naming.SerialInitContextFactory x;
		Properties props=new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.enterprise.naming.SerialInitContextFactory");
		props.put(Context.PROVIDER_URL,"localhost:3700");//可加iiop://
		InitialContext ctx=new InitialContext(props);
		DataSource ds=(DataSource)ctx.lookup("jndi_mysql");//不用加java:xx
		Connection conn=ds.getConnection();
				
		
	}

}
