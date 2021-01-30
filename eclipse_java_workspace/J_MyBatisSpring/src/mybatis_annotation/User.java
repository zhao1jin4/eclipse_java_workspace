package mybatis_annotation;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable{
	 
	private static final long serialVersionUID = 1L;
	
	private int userId=-1;
    private String userName;
    private String password;
    
    private boolean gender; //boolean -> bit 0/1
    private boolean manager; //boolean -> varchar Y/N (TypeHandler)
    private Color likeColor; //enum -> varchar 
    private String comment;
    
    //为@one,@Many
    private List<Job> jobs;
    
    
    public boolean isGender() {
		return gender;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	public boolean isManager() {
		return manager;
	}
	public void setManager(boolean manager) {
		this.manager = manager;
	}
	public Color getLikeColor() {
		return likeColor;
	}
	public void setLikeColor(Color likeColor) {
		this.likeColor = likeColor;
	}
	public List<Job> getJobs() {
		return jobs;
	}
	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}
	public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
}