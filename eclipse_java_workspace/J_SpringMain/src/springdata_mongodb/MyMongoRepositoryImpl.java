package springdata_mongodb;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

public class MyMongoRepositoryImpl {
	MongoTemplate mongoTemplate;

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

	public WriteResult updateObject(String id, String lastName) {
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