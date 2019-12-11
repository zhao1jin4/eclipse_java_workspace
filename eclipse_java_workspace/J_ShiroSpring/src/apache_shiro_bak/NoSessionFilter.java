package apache_shiro_bak;

import javax.security.auth.Subject;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

//为JWT，未测试 
//放到ShiroFilterFactoryBean中的filter属性中 把/**=authc 修改为这个jwt,其它所有用到的拦截器都要修改/增加新的
//已有的代码 Subject currentUser = SecurityUtils.getSubject().getPrincipal();无效？？？
// 手机端token， 持浏览器cookie只能分开接入？？？
public class NoSessionFilter extends BasicHttpAuthenticationFilter {
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) 
	{
		try {
		
//		String[] values=(String[])mappedValue;//当配置 /xx=role[read] 时
		HttpServletRequest req=(HttpServletRequest)request;
		String token =req.getParameter("token");//也可放在http header中
		Subject userInfo=null;//检查token在内存中有吗？登录成功后放Map(Redis带失效时间/DB)中，key为生成UUID的token值，value为subject
		if(userInfo==null)
		{
			System.out.println("未登录");
			return false;
		}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		System.out.println("无权限");
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write("{\"code\":503}");
		return false;
	}
}
