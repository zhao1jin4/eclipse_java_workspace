package spring_jsp.annotation.form;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import spring_jsp.annotation.form.pwdAnnotation.EqualAttributes;

//自定义验证
@EqualAttributes(message="{validation.passwordNotSame}",value={"password","rePassword"})
public class Account implements Serializable //必须实现 Serializable
{   
    private static final long serialVersionUID = 1;
    
    private int id; 
    //@NotNull(message = "{validation.username_empty}")
    @Size(min = 3, max = 20, message = "{validation.username_length}")//国际化串中可以使用{min},{max}
    private String username;
    
    @Pattern(regexp = "^[a-zA-Z0-9]{3,20}$", message ="{validation.password_complex}")
    private String password;
    
    private String rePassword;
    
    @Min(value=18,message="{validation.age_lessThan}")////国际化串中可以使用{value}
    @Max(value=70,message="{validation.age_greatThan}")
    private int age;
    
    private int month;
    private   List<String> skills;
    
    public Account() {   
    }   
   

	public List<String> getSkills() {
		return skills;
	}


	public void setSkills(List<String> skills) {
		this.skills = skills;
	}


	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public String getRePassword() {
		return rePassword;
	}
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}   
}  