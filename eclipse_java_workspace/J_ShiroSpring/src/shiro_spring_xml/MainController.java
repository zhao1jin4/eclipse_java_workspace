package shiro_spring_xml; 
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import shiro_main.UserInfo;

@Controller   
public class MainController  
{
	@Autowired 
	private RequestMappingHandlerMapping requestMappingHandlerMapping;
	
	

	@RequestMapping(value = "/main" )
	public String loginPage(HttpServletRequest request ) 
	{
		PrincipalCollection principals=SecurityUtils.getSubject().getPrincipals();
		if(principals!=null)
		{
			Object obj1=principals.getPrimaryPrincipal();
			UserInfo userInfo=(UserInfo)obj1;
			System.out.println("user:"+userInfo.getFullName());
		}
		
		Object obj=	SecurityUtils.getSubject().getPrincipal();
		if(obj!=null)
		{
			UserInfo userInfo=(UserInfo)obj;
			System.out.println("user:"+userInfo.getFullName());
		}
		
		
		//spring把@Controller中的所有的@RequestMapping的方法
		Map<RequestMappingInfo, HandlerMethod> methods = requestMappingHandlerMapping.getHandlerMethods();
		for(HandlerMethod method:methods.values())
		{
			RequiresAuthentication auth=method.getMethodAnnotation(RequiresAuthentication.class);
			if(	auth!=null   )
				System.out.println("must be login");
			RequiresPermissions perm=method.getMethodAnnotation(RequiresPermissions.class);
			if(	perm!=null   )
				System.out.println("has perm "+Arrays.toString(perm.value()));
			RequiresRoles role=method.getMethodAnnotation(RequiresRoles.class);
			if(	role!=null   )
				System.out.println("has role "+Arrays.toString(role.value()));
		}
		
		request.getSession().setAttribute("mySessionAttr", "MySesssionVal"); 
		return "main";
	}
	
}
