package spring_jsp;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class MyDelegateMulti
{
	public ModelAndView method1(HttpServletRequest request,HttpServletResponse respnose) 
	{
		System.out.println("你调用的是方法１111111111");
		Map model = new HashMap();
        model.put("message", "你调用的是方法１111111111");
        return new ModelAndView("delegate_multi", "model", model);
	}
	
	public ModelAndView method2(HttpServletRequest request,HttpServletResponse respnose) 
	{
		System.out.println("你调用的是方法22222");
		Map model = new HashMap();
        model.put("message", "你调用的是方法22222");
        return new ModelAndView("delegate_multi", "model", model);
	}
}
