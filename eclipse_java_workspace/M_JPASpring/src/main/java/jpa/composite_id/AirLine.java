package jpa.composite_id;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="T_AIR_LINE")
public class AirLine 
{
	@EmbeddedId //复合主键
	private AirLinePk pk; 
	
	private String name;
	public AirLinePk getPk() {
		return pk;
	}
	public void setPk(AirLinePk pk) {
		this.pk = pk;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
