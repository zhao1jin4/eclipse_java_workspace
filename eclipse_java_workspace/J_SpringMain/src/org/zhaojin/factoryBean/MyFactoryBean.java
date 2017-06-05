package org.zhaojin.factoryBean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;

public class MyFactoryBean implements FactoryBean ,BeanFactoryAware
{
	private String targetName;
	BeanFactory factory=null;
	Object o=null;

	public Object getObject() throws Exception
	{
		return o;
	}

	public Class getObjectType()
	{
			return o.getClass();
	}

	public boolean isSingleton()
	{
		return true;
	}

	public void setBeanFactory(BeanFactory factory) throws BeansException
	{
		this.factory=factory;
		 o=this.factory.getBean(targetName);
		
	}

	public String getTargetName()
	{
		return targetName;
	}

	public void setTargetName(String targetName)
	{
		this.targetName = targetName;
	}

}
