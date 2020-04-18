package mybatis_annotation;

import org.apache.ibatis.type.Alias;

//@Alias("joinData")//何用？？？
public class JoinData {
	private String username;
	private int works;
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public int getWorks()
	{
		return works;
	}
	public void setWorks(int works)
	{
		this.works = works;
	}
	 
	
}