package apache_shiro_bak;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.session.Session;
public class MyShiro {
	public static void logined()  
	{
		UsernamePasswordToken token = new UsernamePasswordToken("user", "pass");
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.login(token);//当使用外部系统验证成功后告诉Shiro已经登录
	}
	public static void main(String[] args) {
		
		Subject currentUser = SecurityUtils.getSubject();//得到当前用户
		System.out.println(currentUser);
		
		Session session = currentUser.getSession();
		session.setAttribute( "someKey", "aValue" );
		
	}
}
