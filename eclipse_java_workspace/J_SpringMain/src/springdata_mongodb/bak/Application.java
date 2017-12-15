package springdata_mongodb.bak;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springdata_mongodb.Customer;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;


//spring ¹Ù·½Ê¾Àý
@Configuration
public class Application  {

	@Autowired
	private static CustomerRepository repository;
	  @Bean
	  public Mongo mongo() throws Exception 
	  {
		  return new MongoClient(Collections.singletonList(new ServerAddress("127.0.0.1", 27017)),
	    		 Collections.singletonList(MongoCredential.createCredential("name", "db", "pwd".toCharArray())));
	  }
	  
	  
	public static void main(String[] args) 
	{
		 
	    
		repository.deleteAll();

		// save a couple of customers
		repository.save(new Customer("Alice", "Smith"));
		repository.save(new Customer("Bob", "Smith"));

		// fetch all customers
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Customer customer : repository.findAll()) {
			System.out.println(customer);
		}
		
		
//		try (Stream<Customer> stream = repository.findAllByCustomQueryAndStream()) {
//			  stream.forEach( );
//			}
		
	}
}