package mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Component;


import com.mongodb.client.MongoDatabase;

@Component
public class MyBean
{

	private final MongoDbFactory mongo;

	@Autowired
	public MyBean(MongoDbFactory mongo) {
		this.mongo = mongo;
	}

	// ...

	public void example() {
		MongoDatabase db = mongo.getDb();
		System.out.println(db);
		// ...
	}

}