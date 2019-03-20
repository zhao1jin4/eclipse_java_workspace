package spring_jsp.annotation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.annotation.Generated;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.context.support.ResourceBundleThemeSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebRequestDataBinder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import spring_jsp.annotation.form.Employee;
import spring_jsp.extention.DateRange;
import spring_jsp.extention.DateRangeEditor;

@Controller
@RequestMapping("/employee") //相当于一个目录
public class EmployeeController {
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping("/delete")
	public String deleteEmployee(HttpServletRequest request)//也使用request
	{
		String id=request.getParameter("id");
		System.out.println("deleteEmployee 得到id=:"+id);
		return "ok";
	}
	
	@RequestMapping("/list/{page}")//要使用employee/list/1
	public ModelAndView listEmployee(@PathVariable("page")int pageNO,HttpServletRequest request )//不能使用@PathVariable int page,因如debug编译就不行
	{
	    
		System.out.println("listEmployee 得到page ID为:"+pageNO);
		List<Employee> allEmployee =new ArrayList<>();
		for (int i=pageNO*20;i<(pageNO+1)*20;i++)
		{
			Employee e=new Employee();
			e.setEmployee_id(100+i);
			e.setFirst_name("员工"+i);
			allEmployee.add(e);
		}
		ModelAndView mv=new ModelAndView();
		mv.setViewName("/company_annotation/employeeList");
		mv.getModel().put("allEmployee", allEmployee);
		
		Locale locale=request.getLocale();
//		locale=Locale.CHINESE;
		String i18nStr=messageSource.getMessage("title",new Object[] {"张","王"} , locale);
		System.out.println("i18nStr="+i18nStr);
		return mv;
	}
	
	@InitBinder	//需要处理Date的时候,自动调用这个方法
	public void initBinder(WebDataBinder binder)//要用 WebDataBinder
	{
		System.out.println("调用initBinder");
		//binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		//binder.registerCustomEditor(DateRange.class, new DateRangeEditor() );//MyWebBindingInitializer中的
	}
	
	@RequestMapping("/add/{id}/{name}/{birthday}") 
	public String addEmployee(@PathVariable("id") int id,@PathVariable("name") String name,@PathVariable("birthday") java.util.Date birthday)
	{
		System.out.println("addEmployee 得到id=:"+id+",name="+name+",birthday="+birthday);
		return "ok";
	}
	
	@RequestMapping(method=RequestMethod.GET)//使用类级别的,如仿问为 employee.mvc
	public String searchEmployee()
	{
		System.out.println("searchEmployee ");
		return "/company_annotation/employeeList";
	}
	@RequestMapping(value="/submitQuery",method=RequestMethod.GET)  
	public String submitQuery(Employee param,String otherParam )//表单参数可以单独参数同名传过来
	{
		System.out.println(String.format("submitQuery datarange =%s,otherParam=%s",param.getCreateTimeRange(),otherParam));
		return "/company_annotation/employeeList";
	}
	@RequestMapping(value="/update",method=RequestMethod.GET) //可相当路径,Get,Post不同
	public ModelAndView initUpdateEmployee(HttpServletRequest request,@RequestParam("id")int emp_id)
	{
		String name=request.getParameter("name");//JS端做一次encodeURI(),服务端SpringMVC自动做decodeURI转换为中文
		System.out.println("cn name =:"+name);
		
		System.out.println("initUpdateEmployee 得到page ID为:"+emp_id);
		Employee emp=new Employee();
		emp.setEmployee_id(emp_id);
		emp.setFirst_name("员工");
		return new ModelAndView("/company_annotation/employeeUpdate","emp",emp);
	}
	@RequestMapping(value="/update",method=RequestMethod.POST)//POST方式不能用@PathVariable
	public String updateEmployee(@RequestParam("employee_id")int employee_id,@RequestParam("first_name") String first_name)
	{
		//first_name=new String(first_name.getBytes("iso8859-1"),"UTF-8");//web.xml加了CharacterEncodingFilter
		System.out.println("updateEmployee 得到employee_id为:"+employee_id+",first_name="+first_name);
		return "ok";
	}

	@RequestMapping("/updateEmployeeByDept")
	public String updateEmployeeByDept(@RequestParam("dep_id") int dept_id)//?depat_id=
	{
		System.out.println("updateEmployeeByDept 得到dep_id=:"+dept_id);
		return "ok";
	}
}
