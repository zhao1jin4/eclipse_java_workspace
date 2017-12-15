package zkclient;

import java.io.Serializable;
import java.util.Date;

public class UserLogin implements Serializable {
	
	 
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private Date lastLogin;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	@Override
	public String toString() {
		return "UserLogin [userName=" + userName + ", lastLogin=" + lastLogin + "]";
	}
	
}
