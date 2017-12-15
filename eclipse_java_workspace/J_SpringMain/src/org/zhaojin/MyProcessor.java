package org.zhaojin;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyProcessor implements BeanPostProcessor
{
	public Object postProcessBeforeInitialization(Object obj, String name) throws BeansException
	{	System.out.println("--------postProcessBeforeInitialization-------");
		if (obj instanceof UserManager)
		{
			UserManager um=(UserManager)obj;
			System.out.println(um.getIp()+": name :"+name);
		}
		return obj;
	}
	public Object postProcessAfterInitialization(Object obj, String name) throws BeansException
	{
		System.out.println("------postProcessAfterInitialization-------");
		if (obj instanceof UserManager)
		{
			UserManager um=(UserManager)obj;
			System.out.println(um.getIp()+": name :"+name);
		}
		return obj;
	}
}
