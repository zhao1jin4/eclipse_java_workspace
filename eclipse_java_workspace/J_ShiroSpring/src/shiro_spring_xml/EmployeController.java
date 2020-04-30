package shiro_spring_xml; 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
@RequestMapping(value = "/employee" )
public class EmployeController  
{
	@RequiresAuthentication
	@RequiresRoles({"adminRole"})
	//��ϲ���ʾ��ťһ���ø�����ȫ(��δ���������ַ)
	//Ҳ������service����,��ûȨ�ޱ��쳣,Ҫ����SimpleMappingExceptionResolver (unauthorizedUrlû��)
	@RequestMapping(value = "/create" )
	public void create( HttpServletRequest requset,HttpServletResponse response) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		Object obj=subject.getPrincipal();//����UserInfo�࣬��ǿת
		
		response.getWriter().write("employee/create");

	}
	@RequiresAuthentication
	@RequiresPermissions({"employee:delete"})
	@RequestMapping(value = "/delete" )
	public void delete(  HttpServletResponse response ) throws Exception {
		response.getWriter().write("employee/delete");
	}
	//@RequiresGuest 
	@RequiresRoles({"queryRole"})
	@RequestMapping(value = "/query" )
	public String query(  ) {

		return "query";

	}
}
