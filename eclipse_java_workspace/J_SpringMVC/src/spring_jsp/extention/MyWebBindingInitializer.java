package spring_jsp.extention;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

//��@ControllerAdvice ,��@Controller��, ���� MyWebBindingInitializer��ֻ������� 
public class MyWebBindingInitializer implements WebBindingInitializer 
{
	@Override
	public void initBinder(WebDataBinder binder) {
		System.out.println("���� MyWebBindingInitializer ");
//		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));//JSON��ʽ��Ч
//		binder.registerCustomEditor(Date.class,new  MyPropertyEditor());
		binder.registerCustomEditor(DateRange.class,new  DateRangeEditor());//employee/employeeList
		
	}
}
