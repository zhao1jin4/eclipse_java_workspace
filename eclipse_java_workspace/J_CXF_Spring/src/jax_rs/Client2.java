package jax_rs;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class Client2 {
public static void main(String[] args) {
	  RestTemplate restTemplate = new RestTemplate();
	  restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
	  ResponseEntity<String> response = null;
	  
			  //restTemplate.exchange(null,HttpMethod.GET, null, String.class);
	  String msgBody = response.getBody();

	 
	 System.out.println(msgBody);
}
}
