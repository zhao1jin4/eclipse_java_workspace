package test_mockmvc;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Product {
	private int id;
	private String name;
	private String type;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		return  EqualsBuilder.reflectionEquals( this , obj);
		
//		if(this==obj)
//			return true;
//		if(! (obj instanceof Product)) //obj not null
//			return false;
//		Product p=(Product)obj;
//		return new EqualsBuilder().append(this.id ,p.id)
//		.append(this.name ,p.name)
//		.append(this.type ,p.type).isEquals();
		
		
//		if(this.id == p.id && 
//				(this.name== p.name || (this.name!=null && this.name.equals(p.name))  ) &&
//				(this.type== p.type || (this.type!=null && this.type.equals(p.type))  )
//		  )
//		{
//			return true;
//		}else
//			return false;
	}
	@Override
	public int hashCode() 
	{
		  return  HashCodeBuilder.reflectionHashCode( this );
//		 return new HashCodeBuilder()  
//         .append(this.id)  
//         .append(this.name)
//         .append(this.type)
//         .toHashCode();  
		 
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
		 //return ReflectionToStringBuilder.toString( this );
//		return 	new  ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
//		.append("id",this.id)
//		.append("name",this.name)
//		.append("type",this.type)
//		.toString();
	}
 
	
}
