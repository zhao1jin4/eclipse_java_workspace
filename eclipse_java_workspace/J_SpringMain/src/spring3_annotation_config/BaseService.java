package spring3_annotation_config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

@Service("baseService1") //Service层,id的值默认为类名,第一个字母变小写
//@Scope("prototype")
public class BaseService 
{
	
	@PostConstruct //放在方法前,相当于init-method
	public void init()
	{
		System.out.println("BaseService init方法被调用");
	}
	@PreDestroy //放在方法前,相当于destory-method,要单例,调用AbstractApplicationContext  的 close();
	public void destory()
	{
		System.out.println("BaseService destory 方法被调用");
	}
	
}
