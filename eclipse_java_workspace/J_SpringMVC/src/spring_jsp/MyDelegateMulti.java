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
		System.out.println("����õ��Ƿ�����111111111");
		Map model = new HashMap();
        model.put("message", "����õ��Ƿ�����111111111");
        return new ModelAndView("delegate_multi", "model", model);
	}
	
	public ModelAndView method2(HttpServletRequest request,HttpServletResponse respnose) 
	{
		System.out.println("����õ��Ƿ���22222");
		Map model = new HashMap();
        model.put("message", "����õ��Ƿ���22222");
        return new ModelAndView("delegate_multi", "model", model);
	}
}
