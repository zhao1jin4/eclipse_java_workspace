package spring_jsp.annotation.form;

import java.util.List;

public class EmployeeResult
{
	private List<String> allClothes;
	private List<Employee> underEmp;
	
	public List<Employee> getUnderEmp()
	{
		return underEmp;
	}

	public void setUnderEmp(List<Employee> underEmp)
	{
		this.underEmp = underEmp;
	}

	public List<String> getAllClothes()
	{
		return allClothes;
	}

	public void setAllClothes(List<String> allClothes)
	{
		this.allClothes = allClothes;
	}
	
}
