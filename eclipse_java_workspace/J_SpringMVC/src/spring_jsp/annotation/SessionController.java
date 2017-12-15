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
@SessionAttributes({"sessionAttr","sessionEmp"})//��ModelMap�е� sessionAttr,sessionEmp ����session��
public class SessionController 
{
	@ModelAttribute("initEmployees")
	//��ModelMap�����һ����ΪinitEmployees������,ֵ�Ƿ����ķ���ֵ
	//���κ�������ǰ����,ʹ�ô�ModelMap�����ķ����п��Եõ�,Session�޹ص�
	public List<Employee> initAllEmployee()
	{
		Employee e=new Employee();
		e.setFirst_name("��11");
		e.setLast_name("�ٽ�11");

		Employee e2=new Employee();
		e2.setFirst_name("��11");
		e2.setLast_name("��11");
		List<Employee> list=new ArrayList<>();
		list.add(e);
		list.add(e2);
		return list;
	}
	
	@RequestMapping(params="method=testParams1")// ����URLΪsession.mvc?method=testParams1
	public String testParamsStep1(ModelMap model)
	{
		model.addAttribute("sessionAttr", "zhangsang");//�� ����@SessionAttributes("sessionAttr"),request��session�ж���,����ֻ��request����
		
		Employee e=new Employee();
		e.setFirst_name("��");
		e.setLast_name("��");

		model.addAttribute("sessionEmp",e);
		
		System.out.println("in testParamsStep1======="+model.get("initEmployees"));//���Եõ���ʼ��������, Session�޹ص�
		
		return "company_annotaion/profile";
	}
	@RequestMapping(params="method=testParams2") 
	public String testParamsStep2(@ModelAttribute("sessionEmp") Employee emp1,ModelMap modelMap, BindingResult result, SessionStatus status)
	{   
		//����session�ķ�ʽ������  @ModelAttribute("sessionEmp")��  ����ModelMap , BindingResult��SessionStatus����
		//����session ���� sessionEmp ��ֵ�� emp1 ,����Ҫһ�� ,����Ҫȷ�� session ����sessionEmp
		if (result.hasErrors())
			System.out.println("in testParamsStep2 �д���---------");
		
		System.out.println("in testParamsStep2====�õ�sessionΪ==="+emp1.getLast_name());
		status.setComplete();//��Controller���з��� session �����ģ���������ݽ��� session �����
		return "company_annotaion/profile";
	}
}
