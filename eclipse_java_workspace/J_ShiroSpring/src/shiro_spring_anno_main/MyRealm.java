package shiro_spring_anno_main;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

// AuthorizingRealm 带认证和制授权 
public class MyRealm extends AuthorizingRealm
{
	@Override
	public String getName() {
		return "MyRealm";
	}
	// 授权  
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}
	//认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("MyRealm,"+token);
		String username=(String)token.getPrincipal();//可是放session中的对象
		//查数据库
		if(!"lisi".equals(username))//模拟无此用户
			return null;
		String password="123";
		SimpleAuthenticationInfo authInfo=new SimpleAuthenticationInfo(username,password,getName());
		return authInfo;
	}
}
