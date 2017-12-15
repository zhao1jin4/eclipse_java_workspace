package jndi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JNDIWeblogic
{
	public static void main(String[] args)
	{
		Properties properties = new Properties();
		properties.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
		properties.put(Context.PROVIDER_URL, "t3://127.0.0.1:7001");
		Context context;
		try
		{
			context = new InitialContext(properties);
			Object obj=context.lookup("jdbc/Modeling");
			System.out.println(obj.getClass().getName());
			
			DataSource ds=(DataSource) obj;
			Connection conn=ds.getConnection();
			ResultSet rs=conn.prepareStatement("select * from tab").executeQuery();
			while(rs.next())
			{
				System.out.print(rs.getString("TNAME")+",");
				System.out.println(rs.getString("tabtype"));	
			}
			rs.close();
			conn.close();
			
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
