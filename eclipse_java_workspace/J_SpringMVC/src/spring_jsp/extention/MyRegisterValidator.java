package spring_jsp.extention;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import spring_jsp.Student;
public class MyRegisterValidator implements Validator
{
	public void validate(Object obj, Errors errors)//obj是commandClass的值
	{
		//这里 应该  可以做连接DB的验证
		Student stu = (Student) obj;
		if(stu.getId()<0)
			errors.rejectValue("id", "id_error","ID值不正确__");
		if( "".equals(stu.getUsername()) )
			errors.rejectValue("username", "username_error","用户名值不正确__");
	}
	public boolean supports(Class clazz)
	{
		return clazz.equals(Student.class);
	}
}