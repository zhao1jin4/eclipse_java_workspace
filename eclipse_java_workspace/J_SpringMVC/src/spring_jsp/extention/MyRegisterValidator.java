package spring_jsp.extention;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import spring_jsp.Student;
public class MyRegisterValidator implements Validator
{
	public void validate(Object obj, Errors errors)//obj��commandClass��ֵ
	{
		//���� Ӧ��  ����������DB����֤
		Student stu = (Student) obj;
		if(stu.getId()<0)
			errors.rejectValue("id", "id_error","IDֵ����ȷ__");
		if( "".equals(stu.getUsername()) )
			errors.rejectValue("username", "username_error","�û���ֵ����ȷ__");
	}
	public boolean supports(Class clazz)
	{
		return clazz.equals(Student.class);
	}
}