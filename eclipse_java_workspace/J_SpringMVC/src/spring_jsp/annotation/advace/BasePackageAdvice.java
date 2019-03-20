package spring_jsp.annotation.advace;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;

import spring_jsp.annotation.form.Employee;
import spring_jsp.annotation.form.UserDetails;
import spring_jsp.extention.DateRange;
import spring_jsp.extention.DateRangeEditor;
import spring_jsp.extention.MyPropertyEditor;

@ControllerAdvice("spring_jsp.annotation") //Ӧ�õ�����@RequestMappingע�ⷽ��
public class BasePackageAdvice 
{
	 @ModelAttribute
	 public UserDetails newUser() 
	 {
	 
        System.out.println("============Ӧ�õ�����@RequestMappingע�ⷽ��������ִ��֮ǰ�ѷ���ֵ����Model");
        return new UserDetails();
    }
	 //MyWebBindingInitializer -> @ControllerAdvice -> @Controller 
    @InitBinder
    public void initBinder(WebDataBinder binder) 
    {
    	binder.registerCustomEditor(Date.class,new  MyPropertyEditor());
    	binder.registerCustomEditor(DateRange.class,new  DateRangeEditor());
        System.out.println("============Ӧ�õ�����@RequestMappingע�ⷽ��������ִ��֮ǰ��ʼ�����ݰ���");
    }
    
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public String processUnauthenticatedException(NativeWebRequest request, RuntimeException e) {
        System.out.println("===========Ӧ�õ�����@RequestMappingע��ķ�����processUnauthenticatedException�����׳��쳣��Ϊ:"+e.getClass()+",ԭ��Ϊ:"+e.getMessage());
        return "error/showError2"; //����һ���߼���ͼ��
    }
    
    //��ַ��Ҫ���ִ��ַ���Controller��������@PathVariable("page")int pageNO�������ֻᱨMethodArgumentTypeMismatchException 
    @ExceptionHandler(MethodArgumentTypeMismatchException.class) 
    @ResponseBody
    public  Map<String, Object> typeMismatch(NativeWebRequest request,HttpServletResponse response, RuntimeException e) {
        System.out.println("===========Ӧ�õ�����@RequestMappingע��ķ�����typeMismatch�����׳��쳣��Ϊ:"+e.getClass()+",ԭ��Ϊ:"+e.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "900");
        map.put("reason", "����ת��Ϊ���ֵ��ַ�");
        return map;
        //return "{status:'900',reason:'����ת��Ϊ���ֵ��ַ�'}";//�緵���ַ������Ĳ�֧�֣�����
    }
     
    //js�е�salaryд�ַ�ת�����@RequestBody Employee ������ ��HttpMessageNotReadableException
    @ExceptionHandler(HttpMessageNotReadableException.class) 
    @ResponseBody
    public  Map<String, Object> notReadable(NativeWebRequest request,HttpServletResponse response, RuntimeException e) {
    	System.out.println("===========Ӧ�õ�����@RequestMappingע��ķ�����notReadable�����׳��쳣��Ϊ:"+e.getClass()+",ԭ��Ϊ:"+e.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "901");
        map.put("reason", "JSON����ת��Ϊ���ֵ��ַ�");
        return map;
        //return "{status:'900',reason:'����ת��Ϊ���ֵ��ַ�'}";//�緵���ַ������Ĳ�֧�֣�����
    }
    
    
    
    
}