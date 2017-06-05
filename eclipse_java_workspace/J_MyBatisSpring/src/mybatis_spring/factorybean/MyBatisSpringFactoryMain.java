package mybatis_spring.factorybean;

import mybatis_spring.Employee;
import mybatis_spring.factorybean.dao.AnnoEmployeeInterface;
import mybatis_spring.factorybean.dao.XmlEmployeeInterface;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class MyBatisSpringFactoryMain 
{ 
	public static void main(String[] args) {
		ApplicationContext ctx = new  ClassPathXmlApplicationContext("mybatis_spring/factorybean/applicationContext.xml");
		
		XmlEmployeeInterface xmlInterface=(XmlEmployeeInterface)ctx.getBean("xmlEmployeeInterface"); 
		    //如类名是XMLEmployeeInterface,这里得到类名???,要用类名XmlEmployeeInterface
		//int effectRow=xmlInterface.deleteByEmployeeId(102);
		
//		Employee e=new Employee();
//		e.setId(90012);
//		e.setUsername("springXmlObject");
//		xmlInterface.insertXML(e);
		xmlInterface.insertManyParam(90013,"springXmlManyParam","password",25);
		
		//测试 org.mybatis.spring.mapper.MapperFactoryBean ,XML的不用Dao实现类
		//--------
//		AnnoEmployeeInterface annoEmp=(AnnoEmployeeInterface)ctx.getBean("annoEmployeeInterface");
//		int effectRow2=annoEmp.deleteByEmployeeId(102);
		
		//---做事务
		AnnoService annoService=(AnnoService)ctx.getBean("annoService"); 
//		annoService.doInTransactionUseTemplate();
//		annoService.doInTransactionUseAnnotation();
        
		annoService.batchUseAnnotation();
		
		
	}
	
}
