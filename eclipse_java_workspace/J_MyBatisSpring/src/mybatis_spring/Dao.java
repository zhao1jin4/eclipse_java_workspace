package mybatis_spring;



public interface Dao {
	public Employee getEmployeeByName(String name);
	public Employee getEmployeeById(int emp_id);
	public int addAge(int emp_id);
	public int plusAge(int emp_id);
}
