package mybatis_spring;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
public class EmployeeSqlProvider
{
	
	private String typeToString(Object val)
	{
		if(val == null)
		{
			return null;
		}
		
		if(val instanceof String )
		{
			return "'"+val.toString()+"'";
		}else if (val instanceof Date )
		{
			SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd");
			return "'"+format.format(val)+"'";
		}else
			return val.toString();
	}
	 
	public String insertSqlBatch(Map<String,Object> params)
	{
		List<Employee> employeeList=(List<Employee>)params.get("dataList");
		StringBuilder strBuilder =new StringBuilder ("insert into employee(id,username,password,age) values ");
		int size=employeeList.size();
		for(int i=0; i< size;i++ )
		{
			Employee emp=employeeList.get(i);
			
			//手工 拼SQL 方式 ,应该还有更好的方式,但如果使用#{}绑定可能会更慢
			strBuilder.append("(");
				strBuilder.append(typeToString(emp.getId())).append(",");
				strBuilder.append(typeToString(emp.getUsername())).append(",");
				strBuilder.append(typeToString(emp.getPassword())).append(",");
				strBuilder.append(emp.getAge()) ;
			strBuilder.append(")");
			if(i < size-1)
				strBuilder.append(",\n");
		}
//		System.out.println(strBuilder.toString());
		return strBuilder.toString();
	}
	 
}
