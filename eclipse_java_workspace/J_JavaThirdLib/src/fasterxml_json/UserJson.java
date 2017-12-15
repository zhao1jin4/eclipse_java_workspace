package fasterxml_json;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserJson {

    @JsonProperty("userName")
    private String userName;
    
    @JsonProperty("joinDate")
    private Date joinDate;

    @JsonIgnore
    private String password;
    
    @JsonProperty("favorite")
    private List<String> favorite;

    @JsonProperty("order")
    private OrderJson order;

	public List<String> getFavorite() {
		return favorite;
	}

	public void setFavorite(List<String> favorite) {
		this.favorite = favorite;
	}

	public String getUserName() {
		return userName;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
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

	public OrderJson getOrder() {
		return order;
	}

	public void setOrder(OrderJson order) {
		this.order = order;
	}
    
}
