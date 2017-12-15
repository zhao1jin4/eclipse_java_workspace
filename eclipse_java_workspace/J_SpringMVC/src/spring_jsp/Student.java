package spring_jsp;

import java.util.Date;
import java.util.List;

public class Student 
{
	private int id;
	private String username;
	private String password;
	private int age;
	private Date birthday ;
	private String clazz;
	private List<Integer> course_ids; //对多选的方式,OK
	public List<Integer> getCourse_ids() {
		return course_ids;
	}
	public void setCourse_ids(List<Integer> course_ids) {
		this.course_ids = course_ids;
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
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	
	
}
