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
#spring.thymeleaf.suffix=.html
spring.thymeleaf.suffix=
		
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
		mv.setViewName("springFirst"); //找不到？？？？？？？
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
	
	@RequestMapping("/thymeleaf") //http://127.0.0.1:8081/J_SpringBoot/thymeleaf
	public ModelAndView sayFree(HttpServletRequest request) {
	
		
		ModelAndView mv=new ModelAndView();
		mv.setViewName("springFirst");
		
		mv.getModelMap().put("user", "li123");
		
		
		 Locale locale = RequestContextUtils.getLocale(request);
        System.out.println("Locale:" + locale.getLanguage());

//	    String msg1 = this.messageSource.getMessage("try", null, Locale.CHINA);
        String msg1 = this.messageSource.getMessage("try", null, Locale.CHINESE); 
        System.out.println("Msg:" + msg1);
        
		return mv;
	} 
	 
	@RequestMapping("json")  // http://127.0.0.1:8081/json  
	@ResponseBody  //返回jSON
	public UserVO json() {
		List<UserVO> list=new ArrayList<>();
		
		UserVO user=new UserVO();
		user.setId(32);
		user.setUsername("李");
		user.setBirthday(new Date());
		
		list.add( user); 
		
		
		
		UserVO user2=new UserVO();
		user2.setId(322);
		user2.setUsername("李2");
		user2.setBirthday(new Date());
		
		list.add( user2); 
		return user;
	}
	
	@RequestMapping(value="reqJson",method= {RequestMethod.POST})  // http://127.0.0.1:8081/reqJson  
	@ResponseBody  
	public UserVO reqJson(@RequestBody UserVO req) { //请求JSON
		System.out.println("收到请求为"+req);
		return json();
	}
	//Content-Type=application/json
	//{"username":"李req","id":32,"birthday":"2018-06-30T08:21:39.956+0000"}
	
	
	
}
