package org.zhaojin.cxf.spring;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

//client 和server必须是相同的包名

//@XmlType(name = "User") //只对 <jaxws:client 使用
//@XmlAccessorType(XmlAccessType.FIELD)//只对 <jaxws:client 使用
public class User
{	
//	@XmlElement(nillable = false, name = "id") //只对 <jaxws:client 使用
	int id;
	
//	@XmlElement(nillable = false, name = "name") //只对 <jaxws:client 使用
	String name;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}

}
