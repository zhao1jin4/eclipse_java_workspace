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

	// ��LDAP�������в�ѯ����������Entry
	public void queryEntry() throws Exception {
		SearchControls sc = new SearchControls();
		// �������ò�ѯ��Χ
		// �����ֲ�ѯ��Χ
		// SUBTREE_SCOPE����ʾ����ָ������Ϊ���������в��ң����Է��ض��Ԫ��
		// ONLEVEL_SCOPE:��ʾָ��������ֱ����ʵ��
		// OBJECT_SCOPE:��ʾ����ָ������
		sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
		// �÷����е�����������һ��������DN��һ����Եģ���Ϊ���������ļ��������Ѿ����ӵ���DNΪdc=example��dc=com��Ŀ¼���ϣ�����һ�·�����ѯ��Entry��DNΪ
		// ou=Account��dc=example��dc=com���ڶ��������ǹ��������൱��SQL�е�where�Ӿ䡣����������Ϊ��ѯ����
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

	// ��LDAP�����������Entry
	public void addEntry() throws Exception {
		// ��ou=account��dc=example��dc=com�ڵ������һ���ӽڵ㡣��RDNΪcn=liujianzhong
		Attributes attrs = new BasicAttributes();
		attrs.put("cn", "liujianzhong");//cn ��sn��inetOrgPerson�����
		attrs.put("sn", "liu");
		attrs.put("userpassword", "123456");//���Ǵ���,��ʾ��ѡ����,��userPassword �Ŀ�ѡ����
		BasicAttribute objectClassSet = new BasicAttribute("objectclass");//�����ִ�Сд��
		objectClassSet.add("top");
		objectClassSet.add("person");
		objectClassSet.add("organizationalPerson");
		objectClassSet.add("inetOrgPerson");//inetOrgPerson �̳���organizationalPerson �̳��� person�̳���top
		attrs.put(objectClassSet);
		ctx.createSubcontext("cn=lizhaojin123", attrs);//��cn=lizhaojin,����cn=lizhaojin,ou=Account(ǰ����ou=AccountҪ�Ѵ���)
	}

	// ɾ��LDAPĿ¼��������ָ����Entry������ýڵ�ΪҶ�ӽڵ���ֱ��ɾ��������Ҫ�ȵõ��ýڵ��µ�����Ҷ�ӽڵ㣬Ȼ�����ײ��Ҷ�ӽڵ�ɾ����ֱ��ɾ������Ҷ�ӽڵ�Ϊֹ
	public void delEntry() throws Exception {
		// ɾ��Ҷ�ӽڵ�
		String DN = "cn=liujianzhong,ou=Account";
		getDirContext().destroySubcontext(DN);
		// ɾ����Ҷ�ӽڵ�
		// String DN = "ou=Account,dc=example,dc=com";
	}

	// ɾ����Ҷ�ӽڵ�
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

	// �޸�LDAP��������Entry������
	public void modifyEntry(String dn, DirContext ctx) throws NamingException {
		BasicAttribute attr = new BasicAttribute("sn");
		attr.add("jianzhong");
		// �޸�����
		ModificationItem[] mods = new ModificationItem[1];
		mods[0] = new ModificationItem(LdapContext.REPLACE_ATTRIBUTE, attr);
		ctx.modifyAttributes(dn, mods);
		// �������
		BasicAttribute psw = new BasicAttribute("userpassword");
		psw.add("123456");
		ModificationItem[] add = new ModificationItem[1];
		add[0] = new ModificationItem(LdapContext.ADD_ATTRIBUTE, psw);
		ctx.modifyAttributes(dn, add);//����?????
		// ɾ������
		BasicAttribute psw1 = new BasicAttribute("userpassword");
		ModificationItem[] rem = new ModificationItem[1];
		rem[0] = new ModificationItem(LdapContext.ADD_ATTRIBUTE, psw1);
		ctx.modifyAttributes(dn, rem);
	}
	
	public static void main(String[] args) throws Exception 
	{
		//���Լ��Ĵ����ע��
		  Properties env=new Properties();
		   env.put(RMIConnectorServer.JNDI_REBIND_ATTRIBUTE,"true");//JMXר�õ�,ֵ����"true"
	        env.put(Context.INITIAL_CONTEXT_FACTORY,		"com.sun.jndi.ldap.LdapCtxFactory");
	        env.put(Context.PROVIDER_URL,					"ldap://127.0.0.1:10389/dc=example,dc=com");//Ҫ��ô������???
	        env.put(Context.SECURITY_PRINCIPAL,				"uid=admin,ou=system");
	        env.put(Context.SECURITY_CREDENTIALS,			"secret");
	        
	      //�һ�Root DSE->new Context Entry->ѡ��create entry from scratch->��obejctClass��ѡ��dcObject,organization->����ѡ���Ѵ��ڵ�dc=example,dc=com->����o����ֵ->finish
	        LDAPTest a=   new LDAPTest(env);
	        DirContext context=  a.getDirContext();
	      
//lookup
//		      Object o = context.lookup("cn=liujianzhong");//cn=liujianzhong,ou=Account
//		      String name=o.getClass().getName();
//		      System.out.println(name);
//		      com.sun.jndi.ldap.LdapCtx obj=(com.sun.jndi.ldap.LdapCtx)o;
//		      
	        
	      //���һ�-> dc=example,dc=com -> new Entry-> create entry from scratch ->��obejctClass��ѡ��organizationUnit->��RDN������ou=Account��Ӧ����,�粻��ou��һ��ʱҲ����ou
	      //a.queryEntry();//��������"ou=Account",
	      
	     a.addEntry(); //���ú�,���Ӽ�����һ���û�cn=liujianzhong
	     
	      
	     // a.modifyEntry("cn=liujianzhong,ou=Account",context);//����ʧ��
	    //  a.delDN("ou=Account",context);//������
	     //   a.delEntry();
	       
	}
}
