package spring_jsp.annotation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import spring_jsp.annotation.form.Employee;

@Controller
@RequestMapping("/session") 
@SessionAttributes({"sessionAttr","sessionEmp"})//将ModelMap中的 sessionAttr,sessionEmp 放在session中
public class SessionController 
{
	@ModelAttribute("initEmployees")
	//向ModelMap中添加一个名为initEmployees的属性,值是方法的返回值
	//在任何请求处理前调用,使用带ModelMap参数的方法中可以得到,Session无关的
	public List<Employee> initAllEmployee()
	{
		Employee e=new Employee();
		e.setFirst_name("李11");
		e.setLast_name("召进11");

		Employee e2=new Employee();
		e2.setFirst_name("张11");
		e2.setLast_name("三11");
		List<Employee> list=new ArrayList<>();
		list.add(e);
		list.add(e2);
		return list;
	}
	
	@RequestMapping(params="method=testParams1")// 请求URL为session.mvc?method=testParams1
	public String testParamsStep1(ModelMap model)
	{
		model.addAttribute("sessionAttr", "zhangsang");//如 设置@SessionAttributes("sessionAttr"),request和session中都有,否则只有request中有
		
		Employee e=new Employee();
		e.setFirst_name("王");
		e.setLast_name("五");

		model.addAttribute("sessionEmp",e);
		
		System.out.println("in testParamsStep1======="+model.get("initEmployees"));//可以得到初始化的数据, Session无关的
		
		return "company_annotaion/profile";
	}
	@RequestMapping(params="method=testParams2") 
	public String testParamsStep2(@ModelAttribute("sessionEmp") Employee emp1,ModelMap modelMap, BindingResult result, SessionStatus status)
	{   
		//接收session的方式可以用  @ModelAttribute("sessionEmp")或  参数ModelMap , BindingResult和SessionStatus可无
		//从在session 中找 sessionEmp 的值给 emp1 ,类型要一致 ,必须要确保 session 中有sessionEmp
		if (result.hasErrors())
			System.out.println("in testParamsStep2 有错误---------");
		
		System.out.println("in testParamsStep2====得到session为==="+emp1.getLast_name());
		status.setComplete();//该Controller所有放在 session 级别的模型属性数据将从 session 中清空
		return "company_annotaion/profile";
	}
}
