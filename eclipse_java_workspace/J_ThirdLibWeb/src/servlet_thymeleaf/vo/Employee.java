package servlet_thymeleaf.vo;

public class Employee {
	private int employee_id; 
	private String first_name;
	private String last_name;
	private double salary;
	private DateRange createTimeRange;
	
	private String hobby;
	
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
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
	public DateRange getCreateTimeRange() {
		return createTimeRange;
	}
	public void setCreateTimeRange(DateRange createTimeRange) {
		this.createTimeRange = createTimeRange;
	}
	 

}
