package spring_jsp.annotation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import spring_jsp.annotation.form.Employee;
import spring_jsp.annotation.form.EmployeeResult;

@Controller
@RequestMapping("/json") 
public class JSONController //OK
{
		@RequestMapping(value="/queryEmployeeVO")//,method=RequestMethod.POST
		@ResponseStatus(HttpStatus.OK)
	    @ResponseBody //加这个表示只返回数据,不跳转页面(默认是和RequestMapping相同页)
		public EmployeeResult queryEmployeeVO	( @RequestBody  Employee emp)
		{
			System.out.println(emp.getEmployee_id());
			System.out.println(emp.getFirst_name());
			
			EmployeeResult result=new EmployeeResult();
			List<String> allClothes=new ArrayList<String>();
			allClothes.add("西装");
			allClothes.add("衬衫");
			
			List<Employee> underEmp=new ArrayList<Employee>();
			Employee lisi=new Employee();
			lisi.setFirst_name("li");
			lisi.setLast_name("si");
			Employee wang=new Employee();
			wang.setFirst_name("王");
			wang.setLast_name("五");
			underEmp.add(lisi);
			underEmp.add(wang);
			
			result.setAllClothes(allClothes);
			result.setUnderEmp(underEmp);
			return result;
		}
		@RequestMapping(value="/queryEmployeeVO2" , method=RequestMethod.POST)
	    public ResponseEntity<EmployeeResult> testJson2(HttpEntity<Employee> entity)
	    {  
			System.out.println(entity.getBody().getEmployee_id());
			System.out.println(entity.getBody().getFirst_name());  
			 
			EmployeeResult result=new EmployeeResult();
			List<String> allClothes=new ArrayList<String>();
			allClothes.add("西装");
			allClothes.add("衬衫");
			
			List<Employee> underEmp=new ArrayList<Employee>();
			Employee lisi=new Employee();
			lisi.setFirst_name("li");
			lisi.setLast_name("si");
			Employee wang=new Employee();
			wang.setFirst_name("王");
			wang.setLast_name("五");
			underEmp.add(lisi);
			underEmp.add(wang);
			result.setAllClothes(allClothes);
			result.setUnderEmp(underEmp);
			 
			ResponseEntity<EmployeeResult> responseResult = new ResponseEntity<EmployeeResult>( result,HttpStatus.OK);  
			return responseResult;  
	}  
}
