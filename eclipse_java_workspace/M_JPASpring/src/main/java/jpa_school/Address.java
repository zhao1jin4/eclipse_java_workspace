package jpa_school;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="JPA_ADRESS") 
public class Address 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@OneToOne(cascade={CascadeType.MERGE,CascadeType.PERSIST},optional=true,fetch=FetchType.EAGER)//从方的optional=true,address必须要有对应的student
	@JoinColumn(name="stu_id",nullable=false,foreignKey =@ForeignKey(name="FK_JPA_ADDRESS_STUDENT") )//和optional=true类似
 
	//在从表如何加设计为一列是主外键方式??????????
	private Student stu;

	private String country;
	private String province;
	private String city;
	private String street;
	private int no;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public Student getStu() {
		return stu;
	}
	public void setStu(Student stu) {
		this.stu = stu;
	}
	

}
