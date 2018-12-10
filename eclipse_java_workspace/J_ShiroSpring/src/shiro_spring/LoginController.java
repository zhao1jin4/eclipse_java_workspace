package shiro_spring; 
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
 

@Controller

public class LoginController  
{
	@RequestMapping(value = "/login" )
	public String loginPage(HttpServletRequest request,ModelMap modelMap) 
	{
		String exceptClassName=(String) request.getAttribute("shiroLoginFailure");
		if(exceptClassName!=null)
		{
			if(UnknownAccountException.class.getName().equals(exceptClassName))
			{
				modelMap.put("error", "用户名不存在");
			}else if(IncorrectCredentialsException.class.getName().equals(exceptClassName))
			{
				modelMap.put("error", "密码错误");
			}else if(IncorrectCredentialsException.class.getName().equals(exceptClassName))
			{
				modelMap.put("error", "系统异常");
			}
		}
		//如登录成功会直接跳到登录前的页面，如没有登录前的页面默认请求/ 
		return "login";

	}
	@RequestMapping(value = "/initLoginNoFilter" )
	public String initLoginNoFilter(HttpServletRequest request,ModelMap modelMap) 
	{
		return "loginNoFilter";
	}
	@RequestMapping(value = "/submitLoginNoFilter" )
	public String submitLoginNoFilter(@RequestParam("j_username")String username,@RequestParam("j_password")String password,
			HttpServletRequest request,ModelMap modelMap) 
	{
		
		//登录 不过spring filter  配置  /loginNoFilter.mvc=anon
		//登录数据来源还是realm
		Subject subject = SecurityUtils.getSubject();
//		 org.apache.shiro.session.Session  session=subject.getSession();
//		 session.setAttribute("myAttr", "myVal");
//		 System.out.println(session.getAttribute("myAttr"));
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			subject.login(token);// 看源码
		} catch (UnknownAccountException userError) {
			System.err.println("user not exits");
			modelMap.put("error","user not exits");
			return "loginNoFilter";
		} catch (IncorrectCredentialsException passError) {
			System.err.println("password error ");
			modelMap.put("error","password error");
			return "loginNoFilter";
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("error",e.getMessage());
			return "loginNoFilter";
		}
		System.out.println("login OK?" + subject.isAuthenticated());
		return "main";//只能自己控制跳转页

	}
 
}
