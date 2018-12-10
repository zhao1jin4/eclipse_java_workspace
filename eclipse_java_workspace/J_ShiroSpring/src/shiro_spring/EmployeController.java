package shiro_spring; 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
@RequestMapping(value = "/employee" )
public class EmployeController  
{
	@RequiresAuthentication
	@RequiresRoles({"adminRole"})
	//结合不显示按钮一起用更更安全(对未配置这个地址)
	//也可用于service方法,如没权限报异常,要配置SimpleMappingExceptionResolver (unauthorizedUrl没用)
	@RequestMapping(value = "/create" )
	public void create( HttpServletRequest requset,HttpServletResponse response) throws Exception {
		UnauthorizedException exp;
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
