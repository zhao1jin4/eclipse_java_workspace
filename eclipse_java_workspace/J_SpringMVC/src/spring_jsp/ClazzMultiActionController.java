package spring_jsp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

//没用??/
public class ClazzMultiActionController extends MultiActionController
{
	//MultiActionController 方法格式 public (ModelAndView | Map | String | void) actionName(HttpServletRequest request, HttpServletResponse response, [,HttpSession] [,AnyObject]);
	
	public ModelAndView queryClazzDetailById(HttpServletRequest request,HttpServletResponse respnose) 
	{
		ModelAndView mv=new ModelAndView();
		System.out.println("在方法 queryClazzDetailById");
		//默认view name和方法名同
		return mv;
	}
	//有Session 报
	public ModelAndView queryAllClazz(HttpServletRequest request,HttpServletResponse respnose,HttpSession session,Object obj)
	{
		ModelAndView mv=new ModelAndView();
		System.out.println("在方法 queryAllClazz");
		return mv;
	}
}
