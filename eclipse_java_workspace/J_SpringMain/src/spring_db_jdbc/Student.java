package spring_db_jdbc;

import java.io.Serializable;
import java.sql.Date;

public class Student implements Serializable
{
	private int id;
	private String name;
	private int age;
	private Date birthday;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	public Date getBirthday()
	{
		return birthday;
	}

	public void setBirthday(Date birthday)
	{
		this.birthday = birthday;
	}

	public Student()
	{
	}

	public Student(int id, String name, int age, Date birthday)
	{
		this.birthday = birthday;
		this.id = id;
		this.name = name;
		this.age = age;

	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

}
