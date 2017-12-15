package spring_jsp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class TeacherMultiActionController extends MultiActionController
{
	//MultiActionController 方法格式 public (ModelAndView | Map | String | void) actionName(HttpServletRequest request, HttpServletResponse response, [,HttpSession] [,AnyObject]);
	public void queryTeacherDetailById(HttpServletRequest request,HttpServletResponse respnose) 
	{
		System.out.println("在方法 queryTeacherDetailById");
	}
	public void queryAllTeacher(HttpServletRequest request,HttpServletResponse respnose,HttpSession session,Object obj)
	{
		System.out.println("在方法 queryAllTeacher");
	}
}
