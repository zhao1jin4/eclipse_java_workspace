package spring3_el;

import org.springframework.beans.factory.annotation.Value;

public class User {
	private String username;
	private String password;
	private float salary;
	
	
	private String homeDir;
	
	@Value("#{systemProperties['user.dir'] }")//user.name
	public String getHomeDir() {
		return homeDir;
	}
	public void setHomeDir(String homeDir) {
		this.homeDir = homeDir;
	}
	public float getSalary() {
		return salary;
	}
	public void setSalary(float salary) {
		this.salary = salary;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
