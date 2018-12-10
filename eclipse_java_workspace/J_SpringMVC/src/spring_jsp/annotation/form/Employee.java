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
	
	//JSON �༶��ĵ������ڸ�ʽ��  ��ʽһ
	@DateTimeFormat(pattern="yyyy-MM-dd") //�ǽ�Stringת����Date��һ��ǰ̨����̨��ֵʱ��
	@JsonFormat(pattern="yyyy-MM-dd")//��Dateת����String һ���̨��ֵ��ǰ̨ʱ
	private Date birthDay;
	
	//JSON �༶��ĵ������ڸ�ʽ��   ��ʽ��
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
