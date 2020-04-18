package mybatis_generator.model;

import java.util.List;

import javax.annotation.Generated;

public class DepartmentEntity {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String depName;

    List<Employee> employees;
    
    public List<Employee> getEmployees()
	{
		return employees;
	}

	public void setEmployees(List<Employee> employees)
	{
		this.employees = employees;
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
    public String getDepName() {
        return depName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setDepName(String depName) {
        this.depName = depName == null ? null : depName.trim();
    }
}