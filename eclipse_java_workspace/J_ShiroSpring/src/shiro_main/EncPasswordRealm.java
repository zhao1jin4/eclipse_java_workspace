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

// AuthorizingRealm 带认证和制授权 
public class EncPasswordRealm extends AuthorizingRealm
{
	@Override
	public String getName() {
		return "PasswordRealm";
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
		//SimpleAuthenticationInfo authInfo=new SimpleAuthenticationInfo(username,password,getName());
		
		//----
		String saltKey="saltKey";
		Md5Hash md5Hash2=new Md5Hash(password,saltKey,3);//散列3次
		String saltPassword=md5Hash2.toString();//模拟数据库取出的
		
		SimpleAuthenticationInfo authInfo=new SimpleAuthenticationInfo(username,
				saltPassword,ByteSource.Util.bytes(saltKey), //盐是什么要告诉shiro,3次在配置文件中
				getName());
		
		return authInfo;
	}
}
