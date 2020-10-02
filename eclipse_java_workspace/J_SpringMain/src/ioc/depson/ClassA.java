package ioc.depson;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component(value = "myClassA")
//@DependsOn( value= {"classB"} ) //这是不能循环依赖的，
public class ClassA implements InitializingBean{
	
	//@Autowired //循环注入是可以的
	//private ClassB b;

	@Override
	public void afterPropertiesSet() throws Exception {
		 System.out.println("ClassA afterPropertiesSet");
	}
	
	@PostConstruct
	public void myinit()
	{
		 System.out.println("ClassA myinit");
	}
	
}
