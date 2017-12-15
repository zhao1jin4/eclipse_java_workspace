package spring3_annotation_config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

@Service("baseService1") //Service��,id��ֵĬ��Ϊ����,��һ����ĸ��Сд
//@Scope("prototype")
public class BaseService 
{
	
	@PostConstruct //���ڷ���ǰ,�൱��init-method
	public void init()
	{
		System.out.println("BaseService init����������");
	}
	@PreDestroy //���ڷ���ǰ,�൱��destory-method,Ҫ����,����AbstractApplicationContext  �� close();
	public void destory()
	{
		System.out.println("BaseService destory ����������");
	}
	
}
