package fasterxml_json;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class UserJson {
	
	@Id
	@JsonSerialize(using=MyJSonSerializer.class) 
	private ObjectId id; //mongo的
	
    @JsonProperty("userName")
    private String userName;
    
    @JsonProperty("joinDate")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")//对Timestamp类型要加 timezone="GMT+8"
    private Date joinDate;

    @JsonIgnore
    private String password;
    
    @JsonInclude(JsonInclude.Include.NON_NULL) 	 //字段范围,如果该属性为NULL,生成joson没有该字段 
    @JsonProperty("favorite")
    private List<String> favorite;

    @JsonProperty("order")
    private OrderJson order;

    
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

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
