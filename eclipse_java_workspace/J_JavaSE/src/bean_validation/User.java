package bean_validation;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;
@EqualAttributes(message="{validation.passwordNotSame}",value={"password","rePassword"})//自定义验证
public class User {
	
	@Past(message="出生日期必须是过去的日期")   //@Future
	private Date birthday;
	
	@Min(value=18, message="年龄要>18岁")
	@Max(value=65, message="年龄要<64岁")
	//@Range(min=18,max=65, message="年龄要在18-65岁")//hiberane 的只能整数
	private int age;
	
	@Pattern(regexp = "^[a-zA-Z0-9]{3,20}$", message = "密码要有大小写字母和数字,3-20长度")
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
