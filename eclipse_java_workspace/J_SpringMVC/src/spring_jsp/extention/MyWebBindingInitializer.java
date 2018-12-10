package spring_jsp.extention;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

//先@ControllerAdvice ,再@Controller中, 如有 MyWebBindingInitializer则只调这个了 
public class MyWebBindingInitializer implements WebBindingInitializer 
{
	@Override
	public void initBinder(WebDataBinder binder) {
		System.out.println("调用 MyWebBindingInitializer ");
//		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));//JSON格式无效
//		binder.registerCustomEditor(Date.class,new  MyPropertyEditor());
		binder.registerCustomEditor(DateRange.class,new  DateRangeEditor());//employee/employeeList
		
	}
}
