package rest;

 
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class MainRestTemplate {

	public static void main(String[] args) {
		
	 RestTemplate template=new RestTemplate();
	 Map<String,String> headers=new HashMap<>();
	 headers.put("Content-Type", "application/json");
	 HttpEntity<?> entity=new HttpEntity<>(headers);
	 ResponseEntity< Greeting> res= template.exchange("http://127.0.0.1:8080/J_SpringMVC/reset?name=lisi",HttpMethod.GET, entity,Greeting.class);
	
	
	
	
	}

}
