package springdata_mongodb;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;
import static org.springframework.data.mongodb.core.query.Criteria.where;
public class MainApp {

	public static void main(String[] args) //≤‚ ‘OK
	{
		 ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(
	                "classpath:/springdata_mongodb/spring_mongo.xml");
	 
		 MyMongoRepositoryImpl repository = context.getBean(MyMongoRepositoryImpl.class);
	 
	    repository.dropCollection();
		repository.createCollection();

		Customer lisi = new Customer("lisi", "¿ÓÀƒ");
		lisi.setId("2");
		repository.saveObject(lisi);
		System.out.println("with id 2 " + repository.getObject("2"));

		repository.updateObject("2", "ŒÂ");
		System.out.println(repository.getAllObjects());

		repository.deleteObject("2");
		
		
		
	}

}
