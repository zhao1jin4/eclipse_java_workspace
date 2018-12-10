package springdata_mongodb.bak;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import springdata_mongodb.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

}