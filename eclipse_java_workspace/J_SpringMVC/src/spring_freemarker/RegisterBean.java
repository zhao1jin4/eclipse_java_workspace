package spring_freemarker;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class RegisterBean  implements Serializable
{
	private static final long serialVersionUID = 1L;

	private int id;
	
	//@NotNull//只是值为null,如为空串用这个无效
	@NotBlank(message="名字不能为空")//hibernate的,可以验证空串
	//@Size(min = 3, max = 20)//message="{validation.username_length}"
	private String username;
	
	private String password;
	private int age;
	private Date birthday;
	private String remark;
	private String gender; //F=female,M=male, 不能用char
	private int clazz_id;
	
	//List 没有实现  Serializable ,@Valid 验证无效
	private List<Integer> course_ids;
	
	private List<Integer> interest_ids;
	
	public List<Integer> getCourse_ids() {
		return course_ids;
	}
	public void setCourse_ids(List<Integer> course_ids) {
		this.course_ids = course_ids;
	}
	public List<Integer> getInterest_ids() {
		return interest_ids;
	}
	public void setInterest_ids(List<Integer> interest_ids) {
		this.interest_ids = interest_ids;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getClazz_id() {
		return clazz_id;
	}
	public void setClazz_id(int clazz_id) {
		this.clazz_id = clazz_id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@Override
	public String toString() {
		return "RegisterBean [id=" + id + ", username=" + username + ", password=" + password + ", age=" + age
				+ ", birthday=" + birthday + ", remark=" + remark + ", gender=" + gender + ", clazz_id=" + clazz_id
				+ ", course_ids=" + course_ids + ", interest_ids=" + interest_ids + "]";
	}
	
}
