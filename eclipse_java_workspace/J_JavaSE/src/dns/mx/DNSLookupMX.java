package dns.mx;
import java.util.Hashtable;

import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.Attributes;
import javax.naming.directory.Attribute;
import javax.naming.NamingException;

public class DNSLookupMX 
{
	public static void main(String[] args) throws NamingException 
	{
		Hashtable<String,String> env = new Hashtable<>();
		env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
		env.put("java.naming.provider.url", "dns://210.22.70.225");// DNS Server IP
		DirContext dirContext = new InitialDirContext(env);
		Attributes attrs = dirContext.getAttributes("163.com",new String[]{"MX"});//null全部的，new String[]{"MX"} 在DSN中只找MX记录
		NamingEnumeration allAttr = attrs.getAll(); 
		while (allAttr != null && allAttr.hasMoreElements()) //这只有一个MX记录，如果是传null,这里是多个
		{
			System.out.println("----");
			Attribute attr = (Attribute) allAttr.next();
			NamingEnumeration e = attr.getAll();
			while (e.hasMoreElements())
			{
				String element = e.nextElement().toString();
				System.out.println(element);
			}
		}
	}
	/*
10 163mx01.mxmail.netease.com.
10 163mx02.mxmail.netease.com.
10 163mx03.mxmail.netease.com.
50 163mx00.mxmail.netease.com.
	 */
}
