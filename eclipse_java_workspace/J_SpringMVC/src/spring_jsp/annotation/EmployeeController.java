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
@RequestMapping("/employee") //�൱��һ��Ŀ¼
public class EmployeeController {
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping("/delete")
	public String deleteEmployee(HttpServletRequest request)//Ҳʹ��request
	{
		String id=request.getParameter("id");
		System.out.println("deleteEmployee �õ�id=:"+id);
		return "ok";
	}
	
	@RequestMapping("/list/{page}")//Ҫʹ��employee/list/1
	public ModelAndView listEmployee(@PathVariable("page")int pageNO,HttpServletRequest request )//����ʹ��@PathVariable int page,����debug����Ͳ���
	{
	    
		System.out.println("listEmployee �õ�page IDΪ:"+pageNO);
		List<Employee> allEmployee =new ArrayList<>();
		for (int i=pageNO*20;i<(pageNO+1)*20;i++)
		{
			Employee e=new Employee();
			e.setEmployee_id(100+i);
			e.setFirst_name("Ա��"+i);
			allEmployee.add(e);
		}
		ModelAndView mv=new ModelAndView();
		mv.setViewName("/company_annotation/employeeList");
		mv.getModel().put("allEmployee", allEmployee);
		
		Locale locale=request.getLocale();
//		locale=Locale.CHINESE;
		String i18nStr=messageSource.getMessage("title",new Object[] {"��","��"} , locale);
		System.out.println("i18nStr="+i18nStr);
		return mv;
	}
	
	@InitBinder	//��Ҫ����Date��ʱ��,�Զ������������
	public void initBinder(WebDataBinder binder)//Ҫ�� WebDataBinder
	{
		System.out.println("����initBinder");
		//binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		//binder.registerCustomEditor(DateRange.class, new DateRangeEditor() );//MyWebBindingInitializer�е�
	}
	
	@RequestMapping("/add/{id}/{name}/{birthday}") 
	public String addEmployee(@PathVariable("id") int id,@PathVariable("name") String name,@PathVariable("birthday") java.util.Date birthday)
	{
		System.out.println("addEmployee �õ�id=:"+id+",name="+name+",birthday="+birthday);
		return "ok";
	}
	
	@RequestMapping(method=RequestMethod.GET)//ʹ���༶���,�����Ϊ employee.mvc
	public String searchEmployee()
	{
		System.out.println("searchEmployee ");
		return "/company_annotation/employeeList";
	}
	@RequestMapping(value="/submitQuery",method=RequestMethod.GET)  
	public String submitQuery(Employee param,String otherParam )//���������Ե�������ͬ��������
	{
		System.out.println(String.format("submitQuery datarange =%s,otherParam=%s",param.getCreateTimeRange(),otherParam));
		return "/company_annotation/employeeList";
	}
	@RequestMapping(value="/update",method=RequestMethod.GET) //���൱·��,Get,Post��ͬ
	public ModelAndView initUpdateEmployee(HttpServletRequest request,@RequestParam("id")int emp_id)
	{
		String name=request.getParameter("name");//JS����һ��encodeURI(),�����SpringMVC�Զ���decodeURIת��Ϊ����
		System.out.println("cn name =:"+name);
		
		System.out.println("initUpdateEmployee �õ�page IDΪ:"+emp_id);
		Employee emp=new Employee();
		emp.setEmployee_id(emp_id);
		emp.setFirst_name("Ա��");
		return new ModelAndView("/company_annotation/employeeUpdate","emp",emp);
	}
	@RequestMapping(value="/update",method=RequestMethod.POST)//POST��ʽ������@PathVariable
	public String updateEmployee(@RequestParam("employee_id")int employee_id,@RequestParam("first_name") String first_name)
	{
		//first_name=new String(first_name.getBytes("iso8859-1"),"UTF-8");//web.xml����CharacterEncodingFilter
		System.out.println("updateEmployee �õ�employee_idΪ:"+employee_id+",first_name="+first_name);
		return "ok";
	}

	@RequestMapping("/updateEmployeeByDept")
	public String updateEmployeeByDept(@RequestParam("dep_id") int dept_id)//?depat_id=
	{
		System.out.println("updateEmployeeByDept �õ�dep_id=:"+dept_id);
		return "ok";
	}
}
