package spring_jsp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

//û��??/
public class ClazzMultiActionController extends MultiActionController
{
	//MultiActionController ������ʽ public (ModelAndView | Map | String | void) actionName(HttpServletRequest request, HttpServletResponse response, [,HttpSession] [,AnyObject]);
	
	public ModelAndView queryClazzDetailById(HttpServletRequest request,HttpServletResponse respnose) 
	{
		ModelAndView mv=new ModelAndView();
		System.out.println("�ڷ��� queryClazzDetailById");
		//Ĭ��view name�ͷ�����ͬ
		return mv;
	}
	//��Session ��
	public ModelAndView queryAllClazz(HttpServletRequest request,HttpServletResponse respnose,HttpSession session,Object obj)
	{
		ModelAndView mv=new ModelAndView();
		System.out.println("�ڷ��� queryAllClazz");
		return mv;
	}
}
