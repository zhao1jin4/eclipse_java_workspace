package bean_validation;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
@EqualAttributes(message="{validation.passwordNotSame}",value={"password","rePassword"})//�Զ�����֤
public class User {
	@Past(message="�������ڱ����ǹ�ȥ������")   //@Future
	private Date birthday;
	
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
