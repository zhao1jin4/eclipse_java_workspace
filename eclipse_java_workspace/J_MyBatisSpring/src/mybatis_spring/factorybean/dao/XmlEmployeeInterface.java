package mybatis_spring.factorybean.dao;

import org.apache.ibatis.annotations.Param;

import mybatis_spring.Employee;

public interface XmlEmployeeInterface {
	//MapperFactoryBean,会自动找与类名相同的位置XMLEmployeeInterface.xml,如找不到会使用已经有配置
	//配置的映射文件的namespace和本类全包名必须相同
	public int deleteByEmployeeId(int emp_id);//方法名要必须和XML配置文件中的名字一样
	
	
	public int insertXML(Employee e);
	
	//使用Spring的 MapperFactoryBean也可以用@Param向XML中传参数,XML中用paramterType="map"
	public int insertManyParam(@Param("id")long id,@Param("username")String username,@Param("password")String password,@Param("age")int age);
}
