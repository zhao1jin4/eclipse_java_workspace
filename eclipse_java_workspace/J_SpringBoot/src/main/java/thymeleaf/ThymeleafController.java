package thymeleaf;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import freemarker.UserVO;
 
@Controller
public class ThymeleafController {
	
/*		
 <dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>

spring.thymeleaf.enabled=true
spring.thymeleaf.cache=true

spring.thymeleaf.servlet.content-type=text/html
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.mode=HTML

spring.thymeleaf.prefix=classpath:/templates/ 
spring.thymeleaf.suffix=.xhtml
		
		 */
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping("/first") // http://127.0.0.1:8081/J_SpringBoot/first
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
		
		
		/*
		//--不行？？？
		Locale locale = RequestContextUtils.getLocale(request);
       System.out.println("Locale:" + locale.getLanguage());
       String msg1 = this.messageSource.getMessage("title", null, Locale.SIMPLIFIED_CHINESE); 
       System.out.println("title:" + msg1);
	       */
	       
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
	
	///-----------------
	
	
}
