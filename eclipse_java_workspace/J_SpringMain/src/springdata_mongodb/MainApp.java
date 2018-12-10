package springdata_mongodb;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.DbCallback;
import org.springframework.data.mongodb.core.ExecutableUpdateOperation;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.mongodb.ClientSessionOptions;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

import springdata_mongodb.model.Customer;
import springdata_mongodb.model.OperHistory;
import springdata_mongodb.repo.MyCustomerRepository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Date;
import java.util.List;
public class MainApp {

	public static void main(String[] args) //测试OK
	{
		 ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(
	                "classpath:/springdata_mongodb/spring_mongo.xml");
		 MongoTemplate mongoTemplate =  context.getBean(MongoTemplate.class);
			
			
		 MyMongoRepositoryImpl repository = context.getBean(MyMongoRepositoryImpl.class);
		 
	    repository.dropCollection();
		repository.createCollection();
		
		if (mongoTemplate.collectionExists(OperHistory.class)) {
			mongoTemplate.dropCollection(OperHistory.class);
		}
		mongoTemplate.createCollection(OperHistory.class);
//		transactionSpring(context);//事务OK
		transactionManual(context);//Testing

		
		
		Customer c1 = new Customer("lisi", "李四");
		repository.saveObject(c1);
		List<Customer>  cList=repository.query(c1, "lisi", Customer.class);
		
		//方式二  extends MongoRepository<Customer,String> //<Bean,ID>
		MyCustomerRepository customerRepository = context.getBean(MyCustomerRepository.class);
		for(int i=0;i<20;i++)
		{
			Customer item = new Customer("lisi"+i, "李四"+i);
			customerRepository.save(item);  
		}
		 Customer  cu=customerRepository.findByFirstNameAndLastName("lisi0" , "李四0");
		System.out.println(cu);
		
		List<Customer>  cus=customerRepository.findCustomersByTwoParam("lisi0" , "李四0");
		System.out.println(cus);
		//--
		Customer lisi = new Customer("li", "四");
		lisi.setId("102");
		repository.saveObject(lisi);
		System.out.println("with id 102 " + repository.getObject("102"));

		

	 
		//repository.updateObject("102", "五");
		UpdateResult updateRes= mongoTemplate.updateFirst(
					new Query(Criteria.where("id").is("102")),
					Update.update("lastName", "五"), Customer.class);
		 
		System.out.println(repository.getAllObjects());

		//repository.deleteObject("2");
		  
		mongoTemplate.update(Customer.class).matching(query(where("first_name").is("li"))).
		apply(Update.update("lastName", "四22")).all() ; 
		
		Criteria c=new Criteria();
		c.orOperator(new Criteria[] {Criteria.where("first_name").regex("lisi")});
		Query  query =new   Query(c); 
		
		query.skip(2);
		query.limit(10);//分页
		Sort sort=new Sort(Sort.Direction.ASC,"createTime");
		query.with(sort);
		
		List<Customer> list=mongoTemplate.find(query, Customer.class);
		long cnt=mongoTemplate.count(query, Customer.class);
		 
		
	}
	
	//OK
	public static void transactionSpring(ConfigurableApplicationContext context)
	{ 
		
		MongoDbFactory  dbFactory=context.getBean(MongoDbFactory.class);
		MongoTemplate mongoTemplate =  context.getBean(MongoTemplate.class);
		
		MongoTransactionManager txManager=new MongoTransactionManager(dbFactory);
		TransactionTemplate txTemplate = new TransactionTemplate(txManager);  
		try {
			txTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {     
					 
					Customer lisi = new Customer("li", "四"); 
					lisi.setId("2");
					mongoTemplate.insert(lisi); 
					
					if(1==1)
						 throw new RuntimeException("模拟回滚" ) ; //OK
						 
					OperHistory his = new OperHistory();
					his.setCreateTime(new Date());
					his.setDescription("登录操作");
					his.setIp("127.0.0.1");
					his.setModule("用户模块");
					his.setOperType("SELECT");
					his.setUserId("2");
					
					mongoTemplate.insert(his);  
				 
				}
			});
		
		}catch(Exception e)
		{
			System.out.println("出错事务回滚");	
		}
	}
	

//Testing
	public static void transactionManual(ConfigurableApplicationContext context)
	{
	 
				MongoClient mongoClient =  context.getBean(MongoClient.class);
				MongoTemplate mongoTemplate =  context.getBean(MongoTemplate.class);
				
				ClientSessionOptions.Builder builder=  ClientSessionOptions.builder();
//				builder.causallyConsistent(false);
//				builder.defaultTransactionOptions(defaultTransactionOptions)
				ClientSessionOptions options=builder.build();
				
				ClientSession session = mongoClient.startSession(options);                   
				mongoTemplate.withSession(session)
				    .execute(new DbCallback<Integer>() 
				    {
						@Override
						public Integer doInDB(MongoDatabase db) throws MongoException, DataAccessException {
							 
							session.startTransaction();//Mongo 的session
							  
							try {
								Customer lisi = new Customer("li", "四"); 
								lisi.setId("2");
								mongoTemplate.insert(lisi); //立即生效，没用？？？？？？？？？
								
								if(1==1)
									 throw new RuntimeException("模拟回滚" ) ;  
								OperHistory his = new OperHistory();
								his.setCreateTime(new Date());
								his.setDescription("登录操作");
								his.setIp("127.0.0.1");
								his.setModule("用户模块");
								his.setOperType("SELECT");
								his.setUserId("2");
								
								mongoTemplate.insert(his);  

								session.commitTransaction();               
							}catch(Exception e)
							{
								System.out.println("出错事务回滚");	
								session.abortTransaction();
							}
				           
							return 0;
						}
					});
//				mongoTemplate.withSession(session)
//			    .execute(db->{
//			    	
//			    });
				session.close(); 
	}
	
	
	
}
