package shiro_main;
import java.util.ArrayList;
import java.util.List;

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


// AuthorizingRealm ����֤������Ȩ 
public class PermissonRealm extends AuthorizingRealm
{
	@Override
	public String getName() {
		return "PermissonRealm";
	}
	// ��Ȩ  
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//String username=(String)principals.getPrimaryPrincipal();//���Ƿ�session�еĶ��� ,�����û���
		UserInfo userInfo=(UserInfo)principals.getPrimaryPrincipal();
		//ģ������ݿ�Ϊ�û��ӽ�ɫ��Ȩ��,����ʹ�������ļ���ʽ
		
		List<String> roles=new ArrayList<>();
		roles.add("role1");
		
		List<String> permissions=new ArrayList<>();
		permissions.add("user:create");
		
		SimpleAuthorizationInfo auInfo=new SimpleAuthorizationInfo();
		auInfo.addRoles(roles);
		auInfo.addStringPermissions(permissions);//��addObjectPermissions
		return auInfo;
	}
	//��֤
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("MyRealm,"+token);
		String username=(String)token.getPrincipal();
		//�����ݿ�
		if(!"lisi".equals(username))//ģ���޴��û�
			return null;
		
		
		
		String password="123";
		UserInfo userInfo=new UserInfo();
		userInfo.setUserAccount(username);
		userInfo.setFullName("����");
		userInfo.setMobilePhone("130111122222");
		userInfo.setUserId("1001");
		SimpleAuthenticationInfo authInfo=new SimpleAuthenticationInfo(userInfo,password,getName());
		//SimpleAuthenticationInfo authInfo=new SimpleAuthenticationInfo(username,password,getName());//username���Ƿ�session�еĶ��� ,�����û���
		//----
		/*
		String saltKey="saltKey";
		Md5Hash md5Hash2=new Md5Hash(password,saltKey,3);//ɢ��3��
		String saltPassword=md5Hash2.toString();//ģ�����ݿ�ȡ����
		
		SimpleAuthenticationInfo authInfo=new SimpleAuthenticationInfo(username,
				saltPassword,ByteSource.Util.bytes(saltKey), //3���������ļ���
				getName());
		*/
		return authInfo;
	}
}
