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
        System.out.println("============Ӧ�õ�����@RequestMappingע�ⷽ��������ִ��֮ǰ�ѷ���ֵ����Model");
        return new UserDetails();
    }
	 //MyWebBindingInitializer -> @ControllerAdvice -> @Controller 
    @InitBinder
    public void initBinder(WebDataBinder binder) 
    {
    	binder.registerCustomEditor(Date.class,new  MyPropertyEditor());
        System.out.println("============Ӧ�õ�����@RequestMappingע�ⷽ��������ִ��֮ǰ��ʼ�����ݰ���");
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public String processUnauthenticatedException(NativeWebRequest request, RuntimeException e) {
        System.out.println("===========Ӧ�õ�����@RequestMappingע��ķ����������׳�RuntimeException�쳣ʱִ��:"+e.getMessage());
        return "showError"; //����һ���߼���ͼ��
    }
}