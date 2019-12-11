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
	@RequestMapping("/cookie_header")//����˳���޹�,������ �� @
	public String readCookie(HttpServletRequest request,HttpServletResponse response,HttpSession session,
				@CookieValue("JSESSIONID") String seession_id,@RequestHeader("user-agent") String agent)
	{
		System.out.println("readCookie �õ�JSESSIONDID="+seession_id+",user-agent="+agent+",session="+session);
		Enumeration emu=request.getHeaderNames();
		Object [] cook=request.getCookies();
		return "ok";
	}
	@RequestMapping("/returnVoid")
	public void returnVoid()
	{
		//�緵��void,��ʹ��out.println(""),Ĭ�ϸ�������·��������viewName,�� returnVoid.mvc
	}
	
	@RequestMapping("/redirect")
	public String redirect()
	{
		return "redirect:/other/returnVoid.mvc";//�緵��  redirect:xx.mvc ��ʾ���ض���
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
			//ע�����Ĵ��뻹�ǻᱻִ�е�,��������ʾ��ҳ��RequestDispatcher�Ĳ��Ƿ��ص�view
			System.out.println(1/0); 
		} catch ( Exception e) {  
			e.printStackTrace();
		}
		return "forward:/other/returnVoid.mvc"; 
	}
	@RequestMapping("/returnObject")//Ĭ�ϸ�������·��������viewName
	//public Employee returnObject()
	//public List<Employee> returnObject()
	public ModelMap returnObject()
	{//�緵����һ����,�����ModelAndView��Model��,��Employee����Ϊkey,����Ϊemployee,��ΪList<Employee>���ɵ�keyΪemployeeList
		
		Employee e=new Employee();
		e.setFirst_name("��");
		e.setLast_name("�ٽ�");

		Employee e2=new Employee();
		e2.setFirst_name("��");
		e2.setLast_name("��");
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
	public String myHandleMethod(WebRequest webRequest, Model model)//����������WebRequest
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
	    deferredResult.setResult("���� DeferredResult����");
	    return deferredResult;
	}
	@RequestMapping(value = "/responseBody", method = RequestMethod.GET)
	@ResponseBody
	public String responseBody() {
	    return "Hello World";
	}
	

	//���� MappingJackson2HttpMessageConverter
	@RequestMapping(value="/reqRespJSON", produces="application/json")
    @ResponseBody
    public Employee reqRespJSON(@RequestBody Employee emp,HttpServletRequest request) {
		System.out.println("req ="+emp);
		emp.setFirst_name(emp.getFirst_name()+"_resp");
        return emp; 
    }
	
	//���� MappingJackson2HttpMessageConverter
	@RequestMapping(value="/responseBodyJSON", produces="application/json")
    @ResponseBody
    public  Map<String, Object> responseBodyJSON(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "1231231");
        map.put("nullObj",null);//nullֵ�ɲ���ʾ��Ҳ����ʾΪ�մ������������ʹ����������Ϊnull
        map.put("reason", "ԭ��");//�������Ŀ���
        return map;
        //return "{status:'1231231',reason:'ԭ��'}";//�ַ�������֧��
    }
	
	//���� Jaxb2RootElementHttpMessageConverter
	@RequestMapping(value = "/responseBodyXML", method = RequestMethod.GET, produces="application/xml")
	@ResponseBody
	public UserDetails responseBodyXML() {
		UserDetails userDetails = new UserDetails();
	    userDetails.setUserName("Krishna");
	    userDetails.setEmailId("krishna@gmail.com");
	    return userDetails;//�༶��  @XmlRootElement
	}
	@RequestMapping(value = "/dbError", method = RequestMethod.GET)
	public ModelAndView dbError() throws SQLException {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("ok");
		if(1+1==2)
			throw new SQLException("�ҵ�SQL exception");
	    return mv; 
	}
	
	@RequestMapping(value = "/runtimeExcep", method = RequestMethod.GET)
	public ModelAndView runtimeExcep() throws  Exception  {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("ok");
		if(1+1==2)
			throw new   RuntimeException("Runtime ����");
	    return mv; 
	}
	@RequestMapping(value = "/serverError", method = RequestMethod.GET)
	public ModelAndView serverError() throws  Exception  {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("ok");
		if(1+1==2)
			throw new   Exception("Exception ϵͳ����");
	    return mv; 
	}
	
	//queryEmployeeVO2 ����
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
		//jquery .abort()����
		
		//SpringMVC OutputStream ��ȫ����Ӧǰ��������رղ������
		//jquery .abort()���� 
	
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
		output.write("three:3}".getBytes());//ajax��������abort()���ﲢû�б���
		System.out.println("done");
  
		
		//SpringMVC Writer ��ȫ����Ӧǰ��������رղ������
//		Writer writer=response.getWriter();
//		writer.write("one");
//		writer.write("two");
//		writer.write("three");  
		
	}
}
