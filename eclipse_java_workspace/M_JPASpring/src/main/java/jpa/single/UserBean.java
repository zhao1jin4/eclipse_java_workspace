package jpa.single;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "T_USER",indexes = {@Index(name = "IDX_user_name",  columnList="name", unique = true)})
@GenericGenerator(name="myuuid",strategy="uuid")//只有Hibernate的方式
public class UserBean
{
	@Id
	@GeneratedValue(generator="myuuid")//只有Hibernate的方式
	private String id;//为uuid的String类型
	
	@Column(name = "name")
	private String name;

	@Column(name = "password")
	private String password;


	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

}