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
				modelMap.put("error", "�û���������");
			}else if(IncorrectCredentialsException.class.getName().equals(exceptClassName))
			{
				modelMap.put("error", "�������");
			}else if(IncorrectCredentialsException.class.getName().equals(exceptClassName))
			{
				modelMap.put("error", "ϵͳ�쳣");
			}
		}
		//���¼�ɹ���ֱ��������¼ǰ��ҳ�棬��û�е�¼ǰ��ҳ��Ĭ������/ 
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
		
		//��¼ ����spring filter  ����  /loginNoFilter.mvc=anon
		//��¼������Դ����realm
		Subject subject = SecurityUtils.getSubject();
//		 org.apache.shiro.session.Session  session=subject.getSession();
//		 session.setAttribute("myAttr", "myVal");
//		 System.out.println(session.getAttribute("myAttr"));
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			subject.login(token);// ��Դ��
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
		return "main";//ֻ���Լ�������תҳ

	}
 
}
