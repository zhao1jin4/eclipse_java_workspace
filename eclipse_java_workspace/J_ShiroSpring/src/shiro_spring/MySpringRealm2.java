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

// AuthorizingRealm 带认证和制授权 
public class MySpringRealm2 extends AuthorizingRealm
{
	@Override
	public String getName() {
		return "MySpringRealm2";
	}
	// 授权  
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("doGetAuthorizationInfo2,"+principals);
		//String username=(String)principals.getPrimaryPrincipal();//可是放session中的对象 ,这里用户名
		UserInfo userInfo=(UserInfo)principals.getPrimaryPrincipal();
		//模拟查数据库为用户加角色，权限,不是使用配置文件方式
		
		if(userInfo.getUserAccount().equals("lisi"))
		{
			List<String> roles=new ArrayList<>();
			roles.add("adminRole");
			
			List<String> permissions=new ArrayList<>();
			permissions.add("employee:*");
			
			SimpleAuthorizationInfo auInfo=new SimpleAuthorizationInfo();
			auInfo.addRoles(roles);
			auInfo.addStringPermissions(permissions);//有addObjectPermissions
			return auInfo;
		}else if(userInfo.getUserAccount().toString().equals("wang"))
		{
			List<String> roles=new ArrayList<>();
			roles.add("queryRole");
			
			List<String> permissions=new ArrayList<>();
			permissions.add("employee:query");
			
			SimpleAuthorizationInfo auInfo=new SimpleAuthorizationInfo();
			auInfo.addRoles(roles);
			auInfo.addStringPermissions(permissions);//有addObjectPermissions
			return auInfo;
		}else
		{
			return null;
		}  
		
	}
	//认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("MyRealm2,"+token);
		String username=(String)token.getPrincipal();
		//查数据库
		if("lisi".equals(username))
		{
			String password="123";
			UserInfo userInfo=new UserInfo();
			userInfo.setUserAccount(username);
			userInfo.setFullName("李四2");//两个realm，页面显示第一个
			userInfo.setMobilePhone("130111122222");
			userInfo.setUserId("1001");
			
			String saltKey="saltKey";
			Md5Hash md5Hash2=new Md5Hash(password,saltKey,3);//散列3次
			String saltPassword=md5Hash2.toString();//模拟数据库取出的
			SimpleAuthenticationInfo authInfo=new SimpleAuthenticationInfo(userInfo,saltPassword,
					ByteSource.Util.bytes(saltKey)//盐是什么要告诉shiro
					,getName());
			//SimpleAuthenticationInfo authInfo=new SimpleAuthenticationInfo(username,password,saltPassword,getName());//username可是放session中的对象 ,这里用户名
			return authInfo;
			
		}else if("wang".equals(username))
		{
			String password="456";
			UserInfo userInfo=new UserInfo();
			userInfo.setUserAccount(username);
			userInfo.setFullName("王五");
			userInfo.setMobilePhone("130111133333");
			userInfo.setUserId("1002");
			
			String saltKey="saltKey";
			Md5Hash md5Hash2=new Md5Hash(password,saltKey,3);//散列3次
			String saltPassword=md5Hash2.toString();//模拟数据库取出的
			SimpleAuthenticationInfo authInfo=new SimpleAuthenticationInfo(userInfo,saltPassword,
					ByteSource.Util.bytes(saltKey)
					,getName());
			
			//SimpleAuthenticationInfo authInfo=new SimpleAuthenticationInfo(username,password,getName());//username可是放session中的对象 ,这里用户名
			return authInfo;
			
		}else
		{
			return null;//模拟无此用户
		}  
	}
	//清除登录用户的角色权限信息，不是登录信息，要手动调用 
	public void myClearCache() 
	{
		PrincipalCollection principalCollection=SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principalCollection);//super=AuthorizingRealm
	}
}
