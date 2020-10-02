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


// AuthorizingRealm 带认证和制授权 
public class PermissonRealm extends AuthorizingRealm
{
	@Override
	public String getName() {
		return "PermissonRealm";
	}
	// 授权  
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//String username=(String)principals.getPrimaryPrincipal();//可是放session中的对象 ,这里用户名
		UserInfo userInfo=(UserInfo)principals.getPrimaryPrincipal();
		//模拟查数据库为用户加角色，权限,不是使用配置文件方式
		
		List<String> roles=new ArrayList<>();
		roles.add("role1");
		
		List<String> permissions=new ArrayList<>();
		permissions.add("user:create");
		
		SimpleAuthorizationInfo auInfo=new SimpleAuthorizationInfo();
		auInfo.addRoles(roles);
		auInfo.addStringPermissions(permissions);//有addObjectPermissions
		return auInfo;
	}
	//认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("MyRealm,"+token);
		String username=(String)token.getPrincipal();
		//查数据库
		if(!"lisi".equals(username))//模拟无此用户
			return null;
		
		
		
		String password="123";
		UserInfo userInfo=new UserInfo();
		userInfo.setUserAccount(username);
		userInfo.setFullName("李四");
		userInfo.setMobilePhone("130111122222");
		userInfo.setUserId("1001");
		SimpleAuthenticationInfo authInfo=new SimpleAuthenticationInfo(userInfo,password,getName());
		//SimpleAuthenticationInfo authInfo=new SimpleAuthenticationInfo(username,password,getName());//username可是放session中的对象 ,这里用户名
		//----
		/*
		String saltKey="saltKey";
		Md5Hash md5Hash2=new Md5Hash(password,saltKey,3);//散列3次
		String saltPassword=md5Hash2.toString();//模拟数据库取出的
		
		SimpleAuthenticationInfo authInfo=new SimpleAuthenticationInfo(username,
				saltPassword,ByteSource.Util.bytes(saltKey), //3次在配置文件中
				getName());
		*/
		return authInfo;
	}
}
