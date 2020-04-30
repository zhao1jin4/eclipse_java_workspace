package shiro_spring_anno_main;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

// AuthorizingRealm ����֤������Ȩ 
public class MyRealm extends AuthorizingRealm
{
	@Override
	public String getName() {
		return "MyRealm";
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
		SimpleAuthenticationInfo authInfo=new SimpleAuthenticationInfo(username,password,getName());
		return authInfo;
	}
}
