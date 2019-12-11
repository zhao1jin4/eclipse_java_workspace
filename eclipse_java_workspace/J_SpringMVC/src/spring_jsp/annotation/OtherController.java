package spring_jsp.annotation;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import spring_jsp.annotation.form.Employee;
import spring_jsp.annotation.form.UserDetails;

@Controller
@RequestMapping("/other") 
public class OtherController 
{
	@RequestMapping("/cookie_header")//参数顺序无关,按类型 和 @
	public String readCookie(HttpServletRequest request,HttpServletResponse response,HttpSession session,
				@CookieValue("JSESSIONID") String seession_id,@RequestHeader("user-agent") String agent)
	{
		System.out.println("readCookie 得到JSESSIONDID="+seession_id+",user-agent="+agent+",session="+session);
		Enumeration emu=request.getHeaderNames();
		Object [] cook=request.getCookies();
		return "ok";
	}
	@RequestMapping("/returnVoid")
	public void returnVoid()
	{
		//如返回void,可使用out.println(""),默认根据请求路径来生成viewName,即 returnVoid.mvc
	}
	
	@RequestMapping("/redirect")
	public String redirect()
	{
		return "redirect:/other/returnVoid.mvc";//如返回  redirect:xx.mvc 表示是重定向
	}
	@RequestMapping("/forward")
	public String forward()
	{
		return "forward:/other/returnVoid.mvc"; 
	}
	@RequestMapping("/forwardServlet")
	public String forwardServlet(HttpServletRequest request,HttpServletResponse response)
	{
		try {
			request.getRequestDispatcher("/session.jsp").forward(request, response);
			//注意后面的代码还是会被执行的,但最终显示的页是RequestDispatcher的不是返回的view
			System.out.println(1/0); 
		} catch ( Exception e) {  
			e.printStackTrace();
		}
		return "forward:/other/returnVoid.mvc"; 
	}
	@RequestMapping("/returnObject")//默认根据请求路径来生成viewName
	//public Employee returnObject()
	//public List<Employee> returnObject()
	public ModelMap returnObject()
	{//如返回是一个类,会放在ModelAndView的Model中,以Employee类名为key,生成为employee,如为List<Employee>生成的key为employeeList
		
		Employee e=new Employee();
		e.setFirst_name("李");
		e.setLast_name("召进");

		Employee e2=new Employee();
		e2.setFirst_name("张");
		e2.setLast_name("三");
		List<Employee> list=new ArrayList<>();
		list.add(e);
		list.add(e2);
		
//		return e;
//		return list;
		
		ModelMap springMap=new ModelMap();
		springMap.put("myKey1", "myVale1");
		return springMap;
	}
	
	@RequestMapping("/webRequest")
	public String myHandleMethod(WebRequest webRequest, Model model)//参数可以是WebRequest
	{
		long lastModifiedTimestamp = 0;
		if(webRequest.checkNotModified(lastModifiedTimestamp))
		{
			return null;
		}
	    return "/other/returnVoid";
	}
	@RequestMapping("/deferredResult")
	@ResponseBody
	public DeferredResult<String> deferredResult() 
	{
	    DeferredResult<String> deferredResult = new DeferredResult<String>();
	    deferredResult.setResult("这是 DeferredResult数据");
	    return deferredResult;
	}
	@RequestMapping(value = "/responseBody", method = RequestMethod.GET)
	@ResponseBody
	public String responseBody() {
	    return "Hello World";
	}
	

	//配置 MappingJackson2HttpMessageConverter
	@RequestMapping(value="/reqRespJSON", produces="application/json")
    @ResponseBody
    public Employee reqRespJSON(@RequestBody Employee emp,HttpServletRequest request) {
		System.out.println("req ="+emp);
		emp.setFirst_name(emp.getFirst_name()+"_resp");
        return emp; 
    }
	
	//配置 MappingJackson2HttpMessageConverter
	@RequestMapping(value="/responseBodyJSON", produces="application/json")
    @ResponseBody
    public  Map<String, Object> responseBodyJSON(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "1231231");
        map.put("nullObj",null);//null值可不显示，也可显示为空串，对日期类型传给到服务端为null
        map.put("reason", "原因");//这样中文可以
        return map;
        //return "{status:'1231231',reason:'原因'}";//字符串中文支持
    }
	
	//配置 Jaxb2RootElementHttpMessageConverter
	@RequestMapping(value = "/responseBodyXML", method = RequestMethod.GET, produces="application/xml")
	@ResponseBody
	public UserDetails responseBodyXML() {
		UserDetails userDetails = new UserDetails();
	    userDetails.setUserName("Krishna");
	    userDetails.setEmailId("krishna@gmail.com");
	    return userDetails;//类级加  @XmlRootElement
	}
	@RequestMapping(value = "/dbError", method = RequestMethod.GET)
	public ModelAndView dbError() throws SQLException {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("ok");
		if(1+1==2)
			throw new SQLException("我的SQL exception");
	    return mv; 
	}
	
	@RequestMapping(value = "/runtimeExcep", method = RequestMethod.GET)
	public ModelAndView runtimeExcep() throws  Exception  {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("ok");
		if(1+1==2)
			throw new   RuntimeException("Runtime 错误");
	    return mv; 
	}
	@RequestMapping(value = "/serverError", method = RequestMethod.GET)
	public ModelAndView serverError() throws  Exception  {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("ok");
		if(1+1==2)
			throw new   Exception("Exception 系统错误");
	    return mv; 
	}
	
	//queryEmployeeVO2 中有
	@RequestMapping("/httpEntity")
	public ResponseEntity<String> HttpEntity(HttpEntity<byte[]> requestEntity) throws UnsupportedEncodingException 
	{
		
	    String requestHeader = requestEntity.getHeaders().getFirst("MyRequestHeader");
	    byte[] requestBody = requestEntity.getBody();

	    // do something with request header and body

	    HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("MyResponseHeader", "MyValue");
	    return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value = "/ajaxAbortServerErrorLog", method = RequestMethod.POST)
	public void ajaxAbortServerErrorLog(HttpServletRequest request,HttpServletResponse response) throws  Exception  {
		//jquery .abort()方法
		
		//SpringMVC OutputStream 在全部响应前，浏览器关闭不会出错
		//jquery .abort()方法 
	
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=UTF-8");
		OutputStream output=response.getOutputStream();
		output.write("{one:1,".getBytes());
		output.write("two:2,".getBytes());
		
		output.flush();
		response.flushBuffer();
		try {
			System.out.println("sleep 3");
			Thread.sleep(3000);
		} catch (InterruptedException e) { 
			e.printStackTrace();
		}
		output.write("three:3}".getBytes());//ajax，调用了abort()这里并没有报错
		System.out.println("done");
  
		
		//SpringMVC Writer 在全部响应前，浏览器关闭不会出错
//		Writer writer=response.getWriter();
//		writer.write("one");
//		writer.write("two");
//		writer.write("three");  
		
	}
}
