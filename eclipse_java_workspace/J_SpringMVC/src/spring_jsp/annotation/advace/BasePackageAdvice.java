package spring_jsp.annotation.advace;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.http.HttpStatus;

import spring_jsp.annotation.form.UserDetails;
import spring_jsp.extention.MyPropertyEditor;

@ControllerAdvice("spring_jsp.annotation")
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
        System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器");
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public String processUnauthenticatedException(NativeWebRequest request, RuntimeException e) {
        System.out.println("===========应用到所有@RequestMapping注解的方法，在其抛出RuntimeException异常时执行:"+e.getMessage());
        return "showError"; //返回一个逻辑视图名
    }
}