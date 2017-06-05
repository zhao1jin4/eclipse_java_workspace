package spring_jsp.annotation;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

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
	@RequestMapping(value="/responseBodyJSON", produces="application/json")
    @ResponseBody
    public Map<String, Object> responseBodyJSON(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "1231231");
        map.put("reason", "ԭ��");
        return map;
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
	 
	//???
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
	
}
