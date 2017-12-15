package spring_jsp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class StudentMultiActionController extends MultiActionController
{
	//MultiActionController 方法格式 public (ModelAndView | Map | String | void) actionName(HttpServletRequest request, HttpServletResponse response, [,HttpSession] [,AnyObject]);
	
	public ModelAndView queryStudentDetailById(HttpServletRequest request,HttpServletResponse respnose) 
	{
		System.out.println("在方法 queryStudentDetailById");
		
		Student stu=new Student();
		stu.setUsername("张三");
        return new ModelAndView("school/studentDetail", "stduent", stu);
	}
	public ModelAndView queryStudentByClazz(HttpServletRequest request,HttpServletResponse respnose,HttpSession session,Object obj)
	{
		System.out.println("在方法 queryStudentByClazz");
		
		ModelAndView mv=new ModelAndView();
      
        
		Map<String,List<Student>> model = new HashMap<> ();
		List<Student> allStudent=new ArrayList<>();
        for (int i=0;i<5;i++)
        {
        	Student stu=new Student();
    		stu.setUsername("张三");
    		allStudent.add(stu);
        }
		model.put("allStudent",allStudent);
        mv.addObject("model", model);
        mv.setViewName("school/studentList");
        return mv;
	}
}
