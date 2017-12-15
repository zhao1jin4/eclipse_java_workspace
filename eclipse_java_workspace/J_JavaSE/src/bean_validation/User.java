package bean_validation;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;
@EqualAttributes(message="{validation.passwordNotSame}",value={"password","rePassword"})//�Զ�����֤
public class User {
	
	@Past(message="�������ڱ����ǹ�ȥ������")   //@Future
	private Date birthday;
	
	@Min(value=18, message="����Ҫ>18��")
	@Max(value=65, message="����Ҫ<64��")
	//@Range(min=18,max=65, message="����Ҫ��18-65��")//hiberane ��ֻ������
	private int age;
	
	@Pattern(regexp = "^[a-zA-Z0-9]{3,20}$", message = "����Ҫ�д�Сд��ĸ������,3-20����")
	private String password;
	private String rePassword;
	
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRePassword() {
		return rePassword;
	}
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
	
	
	
}
