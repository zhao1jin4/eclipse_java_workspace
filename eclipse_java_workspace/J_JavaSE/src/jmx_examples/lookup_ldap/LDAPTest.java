package jmx_examples.lookup_ldap;

import java.util.HashMap;
import java.util.Properties;

import javax.management.remote.rmi.RMIConnectorServer;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

public class LDAPTest {
	private static DirContext ctx = null;
	private Properties ldapProps;

	public LDAPTest(Properties ldapProp) {
		this.ldapProps = ldapProp;
	}

	public DirContext getDirContext() throws Exception {
		if (ctx == null) {
			ctx = new InitialDirContext(ldapProps);
		}
		return ctx;
	}

	// 从LDAP服务器中查询符合条件的Entry
	public void queryEntry() throws Exception {
		SearchControls sc = new SearchControls();
		// 用于设置查询范围
		// 有三种查询范围
		// SUBTREE_SCOPE：表示在以指定对象为根的子树中查找，可以返回多个元素
		// ONLEVEL_SCOPE:表示指定对象极其直接子实体
		// OBJECT_SCOPE:表示返回指定对象。
		sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
		// 该方法有单个参数，第一个参数是DN是一种相对的，因为根据配置文件，我们已经连接到根DN为dc=example，dc=com的目录树上，所以一下方法查询的Entry的DN为
		// ou=Account，dc=example，dc=com。第二个参数是过滤器，相当于SQL中的where子句。第三个参数为查询控制
		String dn = "ou=Account";
		String filter = "ou=Account";
		NamingEnumeration result = getDirContext().search(dn, filter, sc);
		while (result.hasMore()) {
			SearchResult entry = (SearchResult) result.next();
			Attributes attrs = entry.getAttributes();
			Attribute attr = attrs.get("ou");
			System.out.println("ou=" + attr.get());
		}
	}

	// 向LDAP服务器中添加Entry
	public void addEntry() throws Exception {
		// 在ou=account，dc=example，dc=com节点下添加一个子节点。其RDN为cn=liujianzhong
		Attributes attrs = new BasicAttributes();
		attrs.put("cn", "liujianzhong");//cn 和sn是inetOrgPerson必须的
		attrs.put("sn", "liu");
		attrs.put("userpassword", "123456");//不是粗体,表示可选属性,是userPassword 的可选属性
		BasicAttribute objectClassSet = new BasicAttribute("objectclass");//不区分大小写的
		objectClassSet.add("top");
		objectClassSet.add("person");
		objectClassSet.add("organizationalPerson");
		objectClassSet.add("inetOrgPerson");//inetOrgPerson 继承自organizationalPerson 继承自 person继承自top
		attrs.put(objectClassSet);
		ctx.createSubcontext("cn=lizhaojin123", attrs);//用cn=lizhaojin,或者cn=lizhaojin,ou=Account(前提是ou=Account要已存在)
	}

	// 删除LDAP目录服务器中指定的Entry，如果该节点为叶子节点则直接删除，否则要先得到该节点下的所有叶子节点，然后从最底层的叶子节点删除，直到删除所有叶子节点为止
	public void delEntry() throws Exception {
		// 删除叶子节点
		String DN = "cn=liujianzhong,ou=Account";
		getDirContext().destroySubcontext(DN);
		// 删除非叶子节点
		// String DN = "ou=Account,dc=example,dc=com";
	}

	// 删除非叶子节点
	public void delDN(String dn, DirContext ctx) throws Exception {
		String root = "dc=example,dc=com";
		SearchControls sc = new SearchControls();
		String filter = "(objectclass=*)";
		sc.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		NamingEnumeration results = ctx.search(dn, filter, sc);
		while (results.hasMore()) {
			SearchResult entry = (SearchResult) results.nextElement();
			String name = entry.getNameInNamespace();//first is :cn=liujianzhong,ou=Account,dc=example,dc=com
			int rin = name.length() - root.length() - 1;
			String rdn = name.substring(0, rin);
			delDN(rdn, ctx);
		}
		ctx.destroySubcontext(dn);//
	}

	// 修改LDAP服务器中Entry的属性
	public void modifyEntry(String dn, DirContext ctx) throws NamingException {
		BasicAttribute attr = new BasicAttribute("sn");
		attr.add("jianzhong");
		// 修改属性
		ModificationItem[] mods = new ModificationItem[1];
		mods[0] = new ModificationItem(LdapContext.REPLACE_ATTRIBUTE, attr);
		ctx.modifyAttributes(dn, mods);
		// 添加属性
		BasicAttribute psw = new BasicAttribute("userpassword");
		psw.add("123456");
		ModificationItem[] add = new ModificationItem[1];
		add[0] = new ModificationItem(LdapContext.ADD_ATTRIBUTE, psw);
		ctx.modifyAttributes(dn, add);//报错?????
		// 删除属性
		BasicAttribute psw1 = new BasicAttribute("userpassword");
		ModificationItem[] rem = new ModificationItem[1];
		rem[0] = new ModificationItem(LdapContext.ADD_ATTRIBUTE, psw1);
		ctx.modifyAttributes(dn, rem);
	}
	
	public static void main(String[] args) throws Exception 
	{
		//我自己的代码和注释
		  Properties env=new Properties();
		   env.put(RMIConnectorServer.JNDI_REBIND_ATTRIBUTE,"true");//JMX专用的,值可是"true"
	        env.put(Context.INITIAL_CONTEXT_FACTORY,		"com.sun.jndi.ldap.LdapCtxFactory");
	        env.put(Context.PROVIDER_URL,					"ldap://127.0.0.1:10389/dc=example,dc=com");//要怎么建立呢???
	        env.put(Context.SECURITY_PRINCIPAL,				"uid=admin,ou=system");
	        env.put(Context.SECURITY_CREDENTIALS,			"secret");
	        
	      //右击Root DSE->new Context Entry->选择create entry from scratch->在obejctClass中选择dcObject,organization->下拉选择已存在的dc=example,dc=com->输入o属性值->finish
	        LDAPTest a=   new LDAPTest(env);
	        DirContext context=  a.getDirContext();
	      
//lookup
//		      Object o = context.lookup("cn=liujianzhong");//cn=liujianzhong,ou=Account
//		      String name=o.getClass().getName();
//		      System.out.println(name);
//		      com.sun.jndi.ldap.LdapCtx obj=(com.sun.jndi.ldap.LdapCtx)o;
//		      
	        
	      //再右击-> dc=example,dc=com -> new Entry-> create entry from scratch ->在obejctClass中选择organizationUnit->在RDN中设置ou=Account对应下面,如不是ou下一步时也会多出ou
	      //a.queryEntry();//方法中有"ou=Account",
	      
	     a.addEntry(); //调用后,在子级加了一个用户cn=liujianzhong
	     
	      
	     // a.modifyEntry("cn=liujianzhong,ou=Account",context);//部分失败
	    //  a.delDN("ou=Account",context);//不正常
	     //   a.delEntry();
	       
	}
}
