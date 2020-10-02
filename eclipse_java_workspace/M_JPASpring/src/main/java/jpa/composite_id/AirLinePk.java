package jpa.composite_id;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
//复合主键要重写equals,hashCode
public class AirLinePk implements Serializable //做复合主键@EmbeddedId时,必须实现Serializable
{
	private String fromCity;
	private String toCity;
	public String getFromCity() {
		return fromCity;
	}
	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}
	public String getToCity() {
		return toCity;
	}
	public void setToCity(String toCity) {
		this.toCity = toCity;
	}
	@Override
	public int hashCode() {
		//都不能为空吧
		return fromCity.hashCode()+toCity.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof AirLinePk )
		{
			AirLinePk dest=(AirLinePk)obj;
			//都不能为空吧
			return this.fromCity.equals(dest.toCity) &&  this.fromCity.equals(dest.toCity);
		}
		return false;
	}
	
}
