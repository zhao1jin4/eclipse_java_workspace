package alibabaJson;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class MyIgnoreObject {
	@JSONField(name="_userName")
    private String userName;
    
	@JSONField(name="_joinDate",format="yyyy-MM-dd HH:mm:ss") 
    private Date joinDate;

    @JSONField(serialize=false,deserialize=false)
    private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
}
