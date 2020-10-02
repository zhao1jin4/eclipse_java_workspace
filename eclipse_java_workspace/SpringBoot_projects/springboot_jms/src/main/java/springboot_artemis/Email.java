package springboot_artemis;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Email //implements Serializable //activemq 要求  implements Serializable
{
	//@JsonProperty("email")
	private String email;

	//@JsonProperty("titile")
	private String titile;

	public Email() {
	}

	public Email(String email, String titile) 
	{
		super();
		this.email = email;
		this.titile = titile;
	}

}
