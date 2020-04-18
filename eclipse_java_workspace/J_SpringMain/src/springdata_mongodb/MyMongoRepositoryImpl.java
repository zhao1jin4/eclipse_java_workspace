package springdata_mongodb;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;
import com.mongodb.client.result.UpdateResult;

import springdata_mongodb.model.Customer;

public class MyMongoRepositoryImpl 
{
	//MongoTemplate mongoTemplate;
	MongoOperations mongoTemplate;// MongoTemplate implements MongoOperations
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public List<Customer> getAllObjects() {
		return mongoTemplate.findAll(Customer.class);
	}

	public void saveObject(Customer c) {
		mongoTemplate.insert(c);
	}

	public Customer getObject(String id) {
		return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)),
				Customer.class);
		
	}

	public <T  > List<T> query(T obj,String likeWord,Class<T> objClass)  
	{
		List<Criteria> listCriteria=new ArrayList<Criteria>();
		for(Field field:obj.getClass().getDeclaredFields())
		{
			listCriteria.add(Criteria.where(field.getName()).regex(likeWord));
		}
		Criteria c=new Criteria();
		c.orOperator(listCriteria.toArray(new Criteria[listCriteria.size()]));
		
		Query  query =new   Query(c);
		query.skip(0);
		query.limit(10); 
		//Sort sort=new Sort(Sort.Direction.ASC,"createTime");//�ϰ汾
		List<Order> orders=new ArrayList<>();
		orders.add(new Order(Sort.Direction.ASC,"createTime"));
		Sort sort=Sort.by(orders);
		
		query.with(sort);
		List<T> res=mongoTemplate.find(query, objClass);
		long count=mongoTemplate.count(query, objClass);
		return res;
	}

	public UpdateResult updateObject(String id, String lastName) {
		return mongoTemplate.updateFirst(
				new Query(Criteria.where("id").is(id)),
				Update.update("lastName", lastName), Customer.class);
	}

	public void deleteObject(String id) {
		mongoTemplate.remove(new Query(Criteria.where("id").is(id)),
				Customer.class);
	}

	public void createCollection() {
		if (!mongoTemplate.collectionExists(Customer.class)) {
			mongoTemplate.createCollection(Customer.class);
		}
	}

	public void dropCollection() {
		if (mongoTemplate.collectionExists(Customer.class)) {
			mongoTemplate.dropCollection(Customer.class);
		}
	}
}