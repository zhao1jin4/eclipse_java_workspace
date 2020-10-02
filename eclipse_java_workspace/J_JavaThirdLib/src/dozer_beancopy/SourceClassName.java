package dozer_beancopy;

import java.util.Date;

public class SourceClassName {
	private String name;
	private Date birthday;
	private boolean gender;
	private NestPair pair;
	
	public NestPair getPair() {
		return pair;
	}
	public void setPair(NestPair pair) {
		this.pair = pair;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public boolean isGender() {
		return gender;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	
}
