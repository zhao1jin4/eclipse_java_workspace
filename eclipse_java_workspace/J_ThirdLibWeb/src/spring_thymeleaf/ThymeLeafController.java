package spring_thymeleaf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.spring5.SpringTemplateEngine;

import servlet_thymeleaf.vo.DateRange;
import servlet_thymeleaf.vo.Employee;
import servlet_thymeleaf.vo.NamValuePair;

@Controller
@RequestMapping("/thymeleafSpring")
public class ThymeLeafController {
	
	@RequestMapping("/first")
	public ModelAndView first(HttpServletRequest request)
	{
		ModelAndView mv=new ModelAndView();
		
		Employee emp=new Employee();
        emp.setEmployee_id(100);
        emp.setFirst_name("张");
        emp.setLast_name("三");
        emp.setSalary(8000.00);

        Calendar calendar= Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-1);
       
        DateRange range=new DateRange();
        range.setBeginTime(calendar.getTime());
        range.setEndTime(new Date());
        emp.setCreateTimeRange(range);
        emp.setHobby("football");
        
        mv.getModelMap().put("currentUser",emp);
		mv.getModelMap().put("name", "张三");
		mv.getModelMap().put("serverHTML", "<p style='color:red'>内容<p>"); 
		mv.setViewName("springFirst"); 
		return mv;
	}
	
	@ModelAttribute("allEmps")
	public List<Employee> populateEmps() 
	{
		List<Employee> emps=new ArrayList< >();
		for(int i=0;i<10;i++)
		{
			Employee emp=new Employee();
	        emp.setEmployee_id(10+i);
	        emp.setFirst_name("张"+i);
	        emp.setLast_name("三"+i);
	        emp.setSalary(8000.00+i);

	        Calendar calendar= Calendar.getInstance();
	        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-1);
	       
	        DateRange range=new DateRange();
	        range.setBeginTime(calendar.getTime());
	        range.setEndTime(new Date());
	        emp.setCreateTimeRange(range);
			emps.add(emp);
		} 
		return emps;
	}

	@ModelAttribute("hobbys")
	public List<NamValuePair> populateHobbys() 
	{
		List<NamValuePair> hobbys=new ArrayList<>();
		hobbys.add(new NamValuePair("足球","football"));
		hobbys.add(new NamValuePair("篮球","basketball"));
		return hobbys;	        
	}
}
