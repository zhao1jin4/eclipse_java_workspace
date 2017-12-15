package org.zhaojin;

import java.util.LinkedList;
import  java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class UserManager implements ApplicationContextAware,InitializingBean ,DisposableBean
{
	List ips=new LinkedList();
	ApplicationContext context=null;
	public void setApplicationContext(ApplicationContext ctx) throws BeansException
	{
		context=ctx;
	}
	
	public boolean login(String name ,String ip)
	{
		if (ips.contains(ip))
		{
			context.publishEvent(new IPEvent(name));
			return false;
		}
		else return true;
	}
	public List getIp()
	{
		return ips;
	}

	public void setIp(List ip)
	{
		System.out.println("setIP --------------");
		this.ips = ip;
	}

	public void afterPropertiesSet() throws Exception
	{
		System.out.println("afterPropertiesSet----------------");
		
	}
	public void init()//在spring配置文件中
	{
		System.out.println("init method--------------");
	}
	public void destroy() throws Exception
	{
		System.out.println("destroy ----------------");
		
	}
}
