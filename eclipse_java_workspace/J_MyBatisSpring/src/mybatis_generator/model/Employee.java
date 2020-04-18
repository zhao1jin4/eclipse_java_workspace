package mybatis_generator.model;

import java.util.Date;
import javax.annotation.Generated;

public class Employee {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String userName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date brithDay;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer age;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer employee_type;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer raise_salary;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer deduct_salary;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer department_id;
    
    private DepartmentEntity department;
    
    public DepartmentEntity getDepartment()
	{
		return department;
	}

	public void setDepartment(DepartmentEntity department)
	{
		this.department = department;
	}

	@Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getId() {
        return id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setId(Integer id) {
        this.id = id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getUserName() {
        return userName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Date getBrithDay() {
        return brithDay;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setBrithDay(Date brithDay) {
        this.brithDay = brithDay;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getAge() {
        return age;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setAge(Integer age) {
        this.age = age;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getEmployee_type() {
        return employee_type;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setEmployee_type(Integer employee_type) {
        this.employee_type = employee_type;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getRaise_salary() {
        return raise_salary;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setRaise_salary(Integer raise_salary) {
        this.raise_salary = raise_salary;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getDeduct_salary() {
        return deduct_salary;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setDeduct_salary(Integer deduct_salary) {
        this.deduct_salary = deduct_salary;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getDepartment_id() {
        return department_id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }
}