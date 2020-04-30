package shiro_main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class ShiroMain {
	public static void main(String[] args) {
		iniLogin("classpath:shiro_main/shiro.ini");
		//iniLogin("classpath:shiro_main/shiro-realm.ini");
		encPassword();
		//iniLogin("classpath:shiro_main/shiro-cryptography.ini");
		//---
		//iniLogin("classpath:shiro_main/shiro-permisson.ini");
		//hasRole();
		//hasPermission();
		//---
		//iniLogin("classpath:shiro_main/shiro-permisson-realm.ini");
		hasRole();
		hasPermission();//û�л��棬ÿ�ζ�����ȡ���ݣ�����
	}

	public static void iniLogin(String configFile) {
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(configFile);
		org.apache.shiro.mgt.SecurityManager securitManger = factory.getInstance();
		SecurityUtils.setSecurityManager(securitManger);
		Subject subject = SecurityUtils.getSubject();
		
		org.apache.shiro.session.Session  session=subject.getSession();
		session.setAttribute("myAttr", "myVal");
		System.out.println(session.getAttribute("myAttr"));
		
		UsernamePasswordToken token = new UsernamePasswordToken("lisi", "123");
		token.setRememberMe(true);
		
		try {
			subject.login(token);// ��Դ��
		} catch (UnknownAccountException userError) {
			System.err.println("user not exits");
		} catch (IncorrectCredentialsException passError) {
			System.err.println("password error ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("login OK?" + subject.isAuthenticated());
		//subject.logout();
		//System.out.println("login OK?" + subject.isAuthenticated());
	}

	 
	public static void encPassword() {
		String password="123";
		Md5Hash md5Hash=new Md5Hash(password);
		System.out.println(md5Hash);//ɢ���㷨��MD5 ��SHA
		
		Md5Hash md5Hash1=new Md5Hash(password,"saltKey");//����
		System.out.println(md5Hash1);
		
		Md5Hash md5Hash2=new Md5Hash(password,"saltKey",3);//ɢ��3��
		System.out.println(md5Hash2);//3e751882a57e7f803dcc9c47eeda7be2
		
		/* ini config file
		org.apache.shiro.authc.credential.HashedCredentialsMatcher credntialMatcher=new HashedCredentialsMatcher();
		credntialMatcher.setHashAlgorithmName("md5");
		credntialMatcher.setHashIterations(3);
		
		EncPasswordRealm realm=new EncPasswordRealm();
		realm.setCredentialsMatcher(credntialMatcher);
		*/
	} 
	public static void hasRole()
	{  
		Subject subject = SecurityUtils.getSubject();
		Object obj=subject.getPrincipal();//������֤ʱ��SimpleAuthenticationInfo�е�UserInfo��
		//��ʹ�õ���UsernamePasswordToken��������ִ��û���
		System.out.println(subject.hasRole("role1"));//��Դ��
		List<String> list=new ArrayList<>();
		list.add("role1");
		list.add("role2");
		list.add("role3");
		System.out.println(Arrays.toString(subject.hasRoles(list))); //����boolean����
		System.out.println(subject.hasAllRoles(list));
		//subject.checkRole("role3");//��û��ɫ����
	}
	public static void hasPermission()
	{
		Subject subject = SecurityUtils.getSubject();
		System.out.println(subject.isPermitted("user:delete"));//��Դ��
		System.out.println(subject.isPermittedAll("user:delete","user:update"));
		subject.checkPermission("user:query");//��û ����
	}
	 
}
