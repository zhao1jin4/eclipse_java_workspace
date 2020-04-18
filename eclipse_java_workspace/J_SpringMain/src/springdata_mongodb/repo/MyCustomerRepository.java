package springdata_mongodb.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import springdata_mongodb.model.Customer;

@Repository
public interface  MyCustomerRepository extends MongoRepository<Customer,String>//<Bean,ID>
{
	//JPA 命名规范   findBy (eclipse,idea都会提示)开头 关键字 And 相当于 where firstName= ? and lastName =?
	//官方文档搜索  Supported keywords for query methods
	public  Customer  findByFirstNameAndLastName(String firstName,String lastName);

	@Query("{'first_name' : ?0 , 'lastName' : ?1}") //?0表示第一个参数
	public List<Customer> findCustomersByTwoParam(String first,String last);

	 //delete返回
	 List <Customer> deleteByLastName(String lastname);
	 
	 Long deletePersonByLastName(String lastname);//delete…By or remove…By
	 Long removeDataByLastName(String lastname);//delete…By or remove…By
	 
	
}
