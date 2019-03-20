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

@ControllerAdvice("spring_jsp.annotation") //应用到所有@RequestMapping注解方法
public class BasePackageAdvice 
{
	 @ModelAttribute
	 public UserDetails newUser() 
	 {
	 
        System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前把返回值放入Model");
        return new UserDetails();
    }
	 //MyWebBindingInitializer -> @ControllerAdvice -> @Controller 
    @InitBinder
    public void initBinder(WebDataBinder binder) 
    {
    	binder.registerCustomEditor(Date.class,new  MyPropertyEditor());
    	binder.registerCustomEditor(DateRange.class,new  DateRangeEditor());
        System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器");
    }
    
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public String processUnauthenticatedException(NativeWebRequest request, RuntimeException e) {
        System.out.println("===========应用到所有@RequestMapping注解的方法，processUnauthenticatedException在其抛出异常类为:"+e.getClass()+",原因为:"+e.getMessage());
        return "error/showError2"; //返回一个逻辑视图名
    }
    
    //地址栏要数字传字符即Controller方法参数@PathVariable("page")int pageNO接收数字会报MethodArgumentTypeMismatchException 
    @ExceptionHandler(MethodArgumentTypeMismatchException.class) 
    @ResponseBody
    public  Map<String, Object> typeMismatch(NativeWebRequest request,HttpServletResponse response, RuntimeException e) {
        System.out.println("===========应用到所有@RequestMapping注解的方法，typeMismatch在其抛出异常类为:"+e.getClass()+",原因为:"+e.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "900");
        map.put("reason", "不可转换为数字的字符");
        return map;
        //return "{status:'900',reason:'不可转换为数字的字符'}";//如返回字符串中文不支持？？？
    }
     
    //js中的salary写字符转服务端@RequestBody Employee 的数字 报HttpMessageNotReadableException
    @ExceptionHandler(HttpMessageNotReadableException.class) 
    @ResponseBody
    public  Map<String, Object> notReadable(NativeWebRequest request,HttpServletResponse response, RuntimeException e) {
    	System.out.println("===========应用到所有@RequestMapping注解的方法，notReadable在其抛出异常类为:"+e.getClass()+",原因为:"+e.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", "901");
        map.put("reason", "JSON不可转换为数字的字符");
        return map;
        //return "{status:'900',reason:'不可转换为数字的字符'}";//如返回字符串中文不支持？？？
    }
    
    
    
    
}