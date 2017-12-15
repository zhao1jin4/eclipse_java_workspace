package cxf_video.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name="myroot" ,namespace="http://mynamespace") //根节点,必须的
@XmlAccessorType(XmlAccessType.FIELD)
//FIELD   自动把  非static,非transient属性 行转换 ,包括 private 属性 ,也可显示指定其它地方@
//PROPERTY 自动把  getter/setter 行转换 ,也可显示指定其它地方@

public class Boy
{
    @XmlElement(name="theName")
	private  String name; //没有初始化开始为null不会在XML中显示  

	@XmlElement  
	private int age=10;  
	
	@XmlElementWrapper(name="favorites")
    @XmlElement(name="favorite")  
    private List<String> favorites;  
	
    @XmlJavaTypeAdapter(MyXMLAdapter.class)   // 是对接口的,如是类就不用了
	private Address addr; 
	   
    @XmlElement(name="theFamily")  
    private Family family; 
    
	public String getName() {
		return name;
	}
 	
	public Address getAddr() {
		return addr;
	}
 	
 	
	public List<String> getFavorites()
    {
        return favorites;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFavorites(List<String> favorites)
    {
        this.favorites = favorites;
    }

    public void setAddr(Address addr) {
		this.addr = addr;
	}

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public Family getFamily()
    {
        return family;
    }

    public void setFamily(Family family)
    {
        this.family = family;
    }
	
}
