package springdata_mongodb.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import springdata_mongodb.model.Customer;
/*
@Repository //√ª”√£ø£ø
public interface  MyCustomerCrudRepository extends   CrudRepository<Customer, Long>
{
	  long deleteByLastName(String lastname); 
	  List<Customer> removeByLastName(String lastname); 
	  long countByLastName(String lastname);
}
*/