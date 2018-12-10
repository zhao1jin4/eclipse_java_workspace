package spring_jsp.annotation.form;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import spring_jsp.extention.DateRange;
import spring_jsp.extention.MyDateJsonDeserializer;
import spring_jsp.extention.MyDateJsonSerializer;



public class Employee {
	
	@NotNull
	private int employee_id;
	@Size(max=50,min=3)
	private String first_name;
	private String last_name;
	private double salary;
	private DateRange createTimeRange;
	
	//JSON 类级别的单独日期格式化  方式一
	@DateTimeFormat(pattern="yyyy-MM-dd") //是将String转换成Date，一般前台给后台传值时用
	@JsonFormat(pattern="yyyy-MM-dd")//将Date转换成String 一般后台传值给前台时
	private Date birthDay;
	
	//JSON 类级别的单独日期格式化   方式二
   // @JsonSerialize(using = MyDateJsonSerializer.class)    
	public Date getBirthDay() {
		return birthDay;
	}
	// @JsonDeserialize(using = MyDateJsonDeserializer.class)    
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	
	public DateRange getCreateTimeRange() {
		return createTimeRange;
	}
	public void setCreateTimeRange(DateRange createTimeRange) {
		this.createTimeRange = createTimeRange;
	}
	public int getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	 

}
