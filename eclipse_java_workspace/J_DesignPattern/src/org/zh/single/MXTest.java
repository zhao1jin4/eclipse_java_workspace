package org.zh.single;

import java.util.Hashtable;

import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.Attributes;
import javax.naming.directory.Attribute;
import javax.naming.NamingException;

public class MXTest {
	public static void main(String[] args) throws NamingException {
		Hashtable env = new Hashtable();

		env.put("java.naming.factory.initial",
				"com.sun.jndi.dns.DnsContextFactory");
		env.put("java.naming.provider.url", "dns://202.96.209.5");// DNS Server:

		DirContext dirContext = new InitialDirContext(env);

		Attributes attrs = dirContext.getAttributes("163.com",
				new String[] { "MX" });

		for (NamingEnumeration ae = attrs.getAll(); ae != null
				&& ae.hasMoreElements();) {
			Attribute attr = (Attribute) ae.next();
			NamingEnumeration e = attr.getAll();

			while (e.hasMoreElements()) {
				String element = e.nextElement().toString();

				System.out.println(element);
			}
		}
	}
}
