package org.liukai.tutorial.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("auth")
public class LoginController {

	protected static Logger logger = Logger.getLogger("controller");

	/**
	 * 指向登录页面
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage(
			@RequestParam(value = "error", required = false) boolean error,
			ModelMap model) {

		logger.debug("Received request to show login page");

		if (error == true) {
			// Assign an error message
			model.put("error", "You have entered an invalid username or password!");
		} else {
			model.put("error", "");
		}
		return "loginpage";

	}

	/**
	 * 指定无访问额权限页面
	 */
	@RequestMapping(value = "/denied", method = RequestMethod.GET)
	public String getDeniedPage() {
		logger.debug("Received request to show denied page");
		return "deniedpage";

	} 
	
	@RequestMapping(value = "/invalidSession", method = RequestMethod.GET)
	public String sessionTimeout() {
		logger.debug("Received request to invalidSession page");
		return "invalidSession";
	}
	@RequestMapping(value = "/secondLogin", method = RequestMethod.GET)
	public String secondLogin() {
		logger.debug("Received request to secondLogin page");
		return "secondLogin";
	}
	@Autowired
	AuthenticationManager authenticationManager=null; //<security:authentication-manager id="authenticationManager">
	
	@Autowired
	SessionAuthenticationStrategy authenticationStrategy=null;//new NullAuthenticatedSessionStrategy();
	//成功的是spring-3.2.8(security3.2.6)版本,值 是 CompositeSessionAuthenticationStrategy(delegate=SessionFixationProtectionStrategy)
	//而我的是多个值？？？   有Csrf,3个session
	
	/** 外部系统验证登录  */
	@RequestMapping(value = "/loginNotify", method = RequestMethod.GET)
	public String loginNotify(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		//外部系统验证登录,告诉SpringSecurity验证成功
		 
		 UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken("user","nopassword");
		 token.setDetails(new WebAuthenticationDetails(request));
		 Authentication auth=authenticationManager.authenticate(token);
		 //会调用<security:authentication-provider user-service-ref="customUserDetailsService" > loadUserByUsername 中有new SimpleGrantedAuthority("ROLE_USER")
		 //和 PasswordEncoder 的matches
		 System.out.println(auth.getAuthorities());//ROLE_USER 角色 
		 SecurityContext context= SecurityContextHolder.getContext();
		 context.setAuthentication(auth);//告诉Spring Security 登录完成 ??
		 
		 authenticationStrategy.onAuthentication(auth, request, response);//告诉Spring Security 登录完成 ???
//		 不能让spring认为已经登录了？？？？，如权限不够会报 Access is denied
		 
		//request.getSession().setAttribute("session_key", "redis_value");//test for spring session
		//response.sendRedirect("../main/common.mvc");// 没有经过 SecurityContextPersistenceFilter？？， SecurityContextPersistenceFilter 把 SecurityContext保存到session中
		return "commonpage"; 
	}
	
}
