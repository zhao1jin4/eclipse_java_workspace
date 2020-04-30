package spring_jsp.annotation;
 
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import spring_jsp.annotation.form.Employee;
import spring_jsp.annotation.form.EmployeeResult;

@Controller
@RequestMapping("/json") 
public class JSONController //OK
{
	 
	@Autowired
	private Validator validator; //������ LocalValidatorFactoryBean
	private String validteReuestForm(Object form,Locale locale)
	{
		LocaleContextHolder.setLocale(locale);//Spring ���Զ��л���֤���������
		
		Set<ConstraintViolation<Object>> violations = validator.validate(form); 
		 if(violations.size()> 0) 
		 {
			 StringBuffer buf = new StringBuffer(); 
			 ResourceBundle bundle = ResourceBundle.getBundle("i18n",Locale.CHINESE); 
			 for(ConstraintViolation<Object> violation: violations)
			 { 
				//bundle.getString(violation.getMessageTemplate() );//���ʻ���Key,�����Լ���key
				buf.append( violation.getPropertyPath().toString()); 
				buf.append(violation.getMessage() ) .append("<BR>\n");
			 }
			 System.out.println(buf);
			 return buf.toString();
		 }
		 return null;
	}
	
	
	/*
	 //JSON��Ч
	@InitBinder	//��Ҫ����Date��ʱ��,�Զ������������
	public void initBinder(WebDataBinder binder)//Ҫ�� WebDataBinder
	{
		System.out.println("����initBinder");
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
*/


	@RequestMapping(value="/queryEmployeeVO")//,method=RequestMethod.POST
	@ResponseStatus(HttpStatus.OK)
    @ResponseBody //�������ʾֻ��������,����תҳ��(Ĭ���Ǻ�RequestMapping��ͬҳ)
	public EmployeeResult queryEmployeeVO	(HttpServletRequest request, @RequestBody  Employee emp)
	{
		String res=validteReuestForm(emp,request.getLocale() );//
		if(res!=null)
		{
			System.err.println("��֤ʧ�ܣ�"+res.toString());
		}
		System.out.println(emp.getEmployee_id());
		System.out.println(emp.getFirst_name());
		System.out.println(emp.getBirthDay());
		EmployeeResult result=	genData();
		
		return result;
	}
	@RequestMapping(value="/queryEmployeeVO2" , method=RequestMethod.POST)
    public ResponseEntity<EmployeeResult> testJson2(HttpEntity<Employee> entity)
    {  
		System.out.println(entity.getBody().getEmployee_id());
		System.out.println(entity.getBody().getFirst_name());  
		System.out.println(entity.getBody().getBirthDay());
		
		EmployeeResult result=	genData();
		
		ResponseEntity<EmployeeResult> responseResult = new ResponseEntity<EmployeeResult>( result,HttpStatus.OK);  
		return responseResult;  
	}  

	@RequestMapping(value="/paramList",method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
    @ResponseBody 
    //��@RequestBody  List<Employee> emps ���Խ�������Spring3 List�е���Map����
    //@RequestBody  Employee[] emps  �ǿ��Ե�
	//public EmployeeResult paramList(HttpServletRequest request, @RequestBody  Employee[] emps)//OK
    public EmployeeResult paramList(HttpServletRequest request, @RequestBody  List<Employee> emps)//Spring5 ��ʵ���� OK ,Spring3 ��Map
	{
		System.out.println(emps.size());
		//System.out.println(emps.length);
		for(Employee emp:emps)
		{
			System.out.println(emp.getEmployee_id());
			System.out.println(emp.getFirst_name());
			System.out.println(emp.getBirthDay());
		}
		
		EmployeeResult result=	genData();
		
		return result;
	}
	private EmployeeResult genData()
	{
		EmployeeResult result=new EmployeeResult();
		List<String> allClothes=new ArrayList<String>();
		allClothes.add("��װ");
		allClothes.add("����");
		
		List<Employee> underEmp=new ArrayList<Employee>();
		Employee lisi=new Employee();
		lisi.setFirst_name("li");
		lisi.setLast_name("si");
		lisi.setBirthDay(new Date());//
		lisi.setCreateTime(new Timestamp(new Date().getTime()));//
		
		Employee wang=new Employee();
		wang.setFirst_name("��");
		wang.setLast_name("��");
		wang.setBirthDay(new Date());//
		wang.setCreateTime(new Timestamp(new Date().getTime()));//
		
		
		underEmp.add(lisi);
		underEmp.add(wang);
		result.setAllClothes(allClothes);
		result.setUnderEmp(underEmp);
		return result;
	}
}
