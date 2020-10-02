package ioc.depson;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**

postProcessBeforeInitialization,beanName=classB
ClassB myinit
ClassB afterPropertiesSet
postProcessAfterInitialization,beanName=classB
 
 */
@Component
@Scope(value ="prototype" ) //多例
@DependsOn( value= {"myClassA"} ) //beanId， 如不是先初始化A，可能和@Autowire有关
public class ClassB  implements InitializingBean,DisposableBean{
	
	@Autowired
	ClassA a;

 
	@Override
	public void afterPropertiesSet() throws Exception {
		 System.out.println("ClassB afterPropertiesSet");
	}
	
	@PostConstruct
	public void myinit() //顺序 先于 afterPropertiesSet  
	{
		 System.out.println("ClassB myinit");
	}
	
	@PreDestroy 
	public void mydestroy()
	{
		 System.out.println("ClassB mydestroy");
	}

	@Override
	public void destroy() throws Exception {
		 System.out.println("ClassB destroy");
	}
}
