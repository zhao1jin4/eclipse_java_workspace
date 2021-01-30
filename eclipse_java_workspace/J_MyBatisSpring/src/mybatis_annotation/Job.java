package mybatis_annotation;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
enum LevelEnum{
	ONE,TWO
}
public class Job implements Serializable
{
	private int id;
	List<String> requirement;
	Date startDate;
	Date endDate;
	String jobTitle;
	
	LevelEnum level;
	
//	private int user_id;
	
	//为@one,@Many
	private User user;
	
	
	public LevelEnum getLevel() {
		return level;
	}

	public void setLevel(LevelEnum level) {
		this.level = level;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public List<String> getRequirement()
	{
		return requirement;
	}

	public void setRequirement(List<String> requirement)
	{
		this.requirement = requirement;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public String getJobTitle()
	{
		return jobTitle;
	}

	public void setJobTitle(String jobTitle)
	{
		this.jobTitle = jobTitle;
	}
//
//	public int getUser_id()
//	{
//		return user_id;
//	}
//
//	public void setUser_id(int user_id)
//	{
//		this.user_id = user_id;
//	}


}