package ioc.depson;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component(value = "myClassA")
//@DependsOn( value= {"classB"} ) //���ǲ���ѭ�������ģ�
public class ClassA implements InitializingBean{
	
	//@Autowired //ѭ��ע���ǿ��Ե�
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
