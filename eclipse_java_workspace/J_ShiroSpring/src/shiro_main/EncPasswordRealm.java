package shiro_main;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

// AuthorizingRealm ����֤������Ȩ 
public class EncPasswordRealm extends AuthorizingRealm
{
	@Override
	public String getName() {
		return "PasswordRealm";
	}
	// ��Ȩ  
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}
	//��֤
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("MyRealm,"+token);
		String username=(String)token.getPrincipal();//���Ƿ�session�еĶ���
		//�����ݿ�
		if(!"lisi".equals(username))//ģ���޴��û�
			return null;
		String password="123";
		//SimpleAuthenticationInfo authInfo=new SimpleAuthenticationInfo(username,password,getName());
		
		//----
		String saltKey="saltKey";
		Md5Hash md5Hash2=new Md5Hash(password,saltKey,3);//ɢ��3��
		String saltPassword=md5Hash2.toString();//ģ�����ݿ�ȡ����
		
		SimpleAuthenticationInfo authInfo=new SimpleAuthenticationInfo(username,
				saltPassword,ByteSource.Util.bytes(saltKey), //����ʲôҪ����shiro,3���������ļ���
				getName());
		
		return authInfo;
	}
}
