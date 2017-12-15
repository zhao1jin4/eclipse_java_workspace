package jax_rs;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class MyClient {

	public static void main(String[] args) {
		
		
		Client client = ClientBuilder.newBuilder().newClient();
		WebTarget target = client.target("http://localhost:8080/rs");
		target = target.path("service").queryParam("a", "avalue");
		 
		Invocation.Builder builder = target.request();
		Response response = builder.get();
		//Book book = builder.get(Book.class);

	}

}
