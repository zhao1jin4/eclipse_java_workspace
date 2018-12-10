package springdata_mongodb.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import springdata_mongodb.model.Customer;

@Repository
public interface  MyCustomerRepository extends MongoRepository<Customer,String>//<Bean,ID>
{
	//JPA �����淶   findBy (eclipse,idea������ʾ)��ͷ �ؼ��� And �൱�� where firstName= ? and lastName =?
	public  Customer  findByFirstNameAndLastName(String firstName,String lastName);

	@Query("{'first_name' : ?0 , 'lastName' : ?1}") //?0��ʾ��һ������
	public List<Customer> findCustomersByTwoParam(String first,String last);
}
