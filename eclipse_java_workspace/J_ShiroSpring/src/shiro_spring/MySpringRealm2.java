package shiro_spring;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import shiro_main.UserInfo;

// AuthorizingRealm ����֤������Ȩ 
public class MySpringRealm2 extends AuthorizingRealm
{
	@Override
	public String getName() {
		return "MySpringRealm2";
	}
	// ��Ȩ  
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("doGetAuthorizationInfo2,"+principals);
		//String username=(String)principals.getPrimaryPrincipal();//���Ƿ�session�еĶ��� ,�����û���
		UserInfo userInfo=(UserInfo)principals.getPrimaryPrincipal();
		//ģ������ݿ�Ϊ�û��ӽ�ɫ��Ȩ��,����ʹ�������ļ���ʽ
		
		if(userInfo.getUserAccount().equals("lisi"))
		{
			List<String> roles=new ArrayList<>();
			roles.add("adminRole");
			
			List<String> permissions=new ArrayList<>();
			permissions.add("employee:*");
			
			SimpleAuthorizationInfo auInfo=new SimpleAuthorizationInfo();
			auInfo.addRoles(roles);
			auInfo.addStringPermissions(permissions);//��addObjectPermissions
			return auInfo;
		}else if(userInfo.getUserAccount().toString().equals("wang"))
		{
			List<String> roles=new ArrayList<>();
			roles.add("queryRole");
			
			List<String> permissions=new ArrayList<>();
			permissions.add("employee:query");
			
			SimpleAuthorizationInfo auInfo=new SimpleAuthorizationInfo();
			auInfo.addRoles(roles);
			auInfo.addStringPermissions(permissions);//��addObjectPermissions
			return auInfo;
		}else
		{
			return null;
		}  
		
	}
	//��֤
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("MyRealm2,"+token);
		String username=(String)token.getPrincipal();
		//�����ݿ�
		if("lisi".equals(username))
		{
			String password="123";
			UserInfo userInfo=new UserInfo();
			userInfo.setUserAccount(username);
			userInfo.setFullName("����2");//����realm��ҳ����ʾ��һ��
			userInfo.setMobilePhone("130111122222");
			userInfo.setUserId("1001");
			
			String saltKey="saltKey";
			Md5Hash md5Hash2=new Md5Hash(password,saltKey,3);//ɢ��3��
			String saltPassword=md5Hash2.toString();//ģ�����ݿ�ȡ����
			SimpleAuthenticationInfo authInfo=new SimpleAuthenticationInfo(userInfo,saltPassword,
					ByteSource.Util.bytes(saltKey)//����ʲôҪ����shiro
					,getName());
			//SimpleAuthenticationInfo authInfo=new SimpleAuthenticationInfo(username,password,saltPassword,getName());//username���Ƿ�session�еĶ��� ,�����û���
			return authInfo;
			
		}else if("wang".equals(username))
		{
			String password="456";
			UserInfo userInfo=new UserInfo();
			userInfo.setUserAccount(username);
			userInfo.setFullName("����");
			userInfo.setMobilePhone("130111133333");
			userInfo.setUserId("1002");
			
			String saltKey="saltKey";
			Md5Hash md5Hash2=new Md5Hash(password,saltKey,3);//ɢ��3��
			String saltPassword=md5Hash2.toString();//ģ�����ݿ�ȡ����
			SimpleAuthenticationInfo authInfo=new SimpleAuthenticationInfo(userInfo,saltPassword,
					ByteSource.Util.bytes(saltKey)
					,getName());
			
			//SimpleAuthenticationInfo authInfo=new SimpleAuthenticationInfo(username,password,getName());//username���Ƿ�session�еĶ��� ,�����û���
			return authInfo;
			
		}else
		{
			return null;//ģ���޴��û�
		}  
	}
	//�����¼�û��Ľ�ɫȨ����Ϣ�����ǵ�¼��Ϣ��Ҫ�ֶ����� 
	public void myClearCache() 
	{
		PrincipalCollection principalCollection=SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principalCollection);//super=AuthorizingRealm
	}
}
