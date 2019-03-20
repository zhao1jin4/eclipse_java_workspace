package nosql_mongodb_querydsl;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.EntityInterceptor;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.mapping.MappedClass;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.mapping.cache.EntityCache;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.querydsl.mongodb.morphia.MorphiaQuery;

import static com.querydsl.collections.CollQueryFactory.*; //delete,update,from
import static com.querydsl.core.alias.Alias.$;
import static com.querydsl.core.alias.Alias.alias;

public class MainDSLMongo {
	/*
	 <dependency>
	    <groupId>com.querydsl</groupId>
	    <artifactId>querydsl-mongodb</artifactId>
	    <version>4.2.1</version>
	</dependency>
	<dependency>
	    <groupId>com.querydsl</groupId>
	     <artifactId>querydsl-core</artifactId>
	    <version>4.2.1</version>
	</dependency> 
	<dependency>
	  <groupId>com.mysema.commons</groupId>
	  <artifactId>mysema-commons-lang</artifactId>
	  <version>0.2.4</version>
	</dependency>
	<dependency>
	    <groupId>org.mongodb.morphia</groupId>
	    <artifactId>morphia</artifactId>
	    <version>1.3.2</version>
	</dependency>


	生成代码才用的
	<dependency>
	  <groupId>com.querydsl</groupId>
	  <artifactId>querydsl-apt</artifactId>
	  <version>4.2.1</version> 
	</dependency>
	<dependency>
	  <groupId>javax.annotation</groupId>
	  <artifactId>javax.annotation-api</artifactId>
	  <version>1.3.2</version>
	</dependency>
	<plugin>
	    <groupId>com.mysema.maven</groupId>
	    <artifactId>apt-maven-plugin</artifactId>
	    <version>1.1.3</version>
	    <executions>
	      <execution>
	        <goals>
	          <goal>process</goal>
	        </goals>
	        <configuration>
	          <outputDirectory>target/generated-sources/java</outputDirectory>
	          <processor>com.querydsl.apt.morphia.MorphiaAnnotationProcessor</processor>
	        </configuration>
	      </execution>
	    </executions>
	  </plugin>
	 

	import org.mongodb.morphia.annotations.Entity;
	@Entity  
	public class Customer {
	  //...
	}

	mvn clean install 在 target/generated-sources/java 目录生成 QCustomer.java
	 
	*/
	public static void main(String[] args) {
  
		MongoCredential credential = MongoCredential.createCredential("zh", "reporting", "123".toCharArray());
	    ServerAddress[] addrs=	new ServerAddress[] { new ServerAddress("127.0.0.1", 27017) };
		MongoClientOptions opts= new MongoClientOptions.Builder().build();
	 	MongoClient mongoClient  = new MongoClient(Arrays.asList(addrs),  credential,opts );  
	 	
	 	Morphia morphia= new Morphia();
		Datastore datastore = morphia.createDatastore(mongoClient,"reporting");
	 
//        morphia.map(Customer.class);
//        morphia.mapPackage("com.hoo.entity");
		  
		Customer cust=	new Customer("li","si_dsl_morphie"); 
		datastore.save(cust);//会存一个className的字段
		
		DBObject dbObj=morphia.toDBObject(cust) ;
		System.out.println(dbObj);
		System.out.println("fromDBObject: " + morphia.fromDBObject(datastore,Customer.class, BasicDBObjectBuilder.start("lastName", "abc").get()));
		System.out.println("getMapper: " + morphia.getMapper());
		System.out.println("isMapped: " + morphia.isMapped(Customer.class));
		
		//QCustomer customer = new QCustomer("customer");
		QCustomer customer =   QCustomer.customer;
		//依赖于 mysema-commons-lang-0.2.4.jar
		MorphiaQuery<Customer> query = new MorphiaQuery<Customer>(morphia, datastore, customer);  
		List<Customer> list = query
			    .where(customer.firstName.eq("li"))
			    .limit(5).offset(1)//跳过一个
			    .fetch();
		
		System.out.println(list);  
		 
	/* 
		Customer c = alias(Customer.class, "cust");
		for (String name : select($(c.getFirstName())).from($(c),cats)
		  .where($(c.getKittens()).size().gt(0))
		  .fetch()) {
		    System.out.println(name);
		}
		*/
		
		
		testMapper(morphia);
	}
	
	 
    public static void testMapper(Morphia morphia) {
        Mapper mapper = morphia.getMapper();
        // 添加对象映射
        System.out.println("addMappedClass: " + mapper.addMappedClass(Customer.class));
  
        System.out.println("createEntityCache: " + mapper.createEntityCache());
        System.out.println(mapper.getCollectionName("myTestDB"));
        System.out.println(mapper.getConverters());
    

        for (EntityInterceptor ei : mapper.getInterceptors()) {
            System.out.println("EntityInterceptor: " + ei);
        } 
        // 所有已经映射的class
        for (MappedClass mc : mapper.getMappedClasses()) {
            System.out.println("getMappedClasses: " + mc);
        }
//
//        System.out.println("mcMap: " + mapper.getMCMap());
//        System.out.println("getOptions: " + mapper.getOptions());
//        System.out.println("keyToRef: " + mapper.keyToRef(mapper.getKey(user)));
//        System.out.println("refToKey: " + mapper.refToKey(mapper.keyToRef(mapper.getKey(user))));
//        System.out.println(mapper.getId(cust)); 
//        System.out.println("getKey: " + mapper.getKey(cust));
    }

    
 /*
    public void testEntityCache(Morphia morphia) {
        EntityCache ec = morphia.getMapper().createEntityCache();
        System.out.println("EntityCache: " + ec);
        Datastore ds = morphia.createDatastore(mongo, "myTestDB");
        User user = new User(System.currentTimeMillis(), "jackson", true, 22, null);
        user.setId(1306814012734L);
        // 添加实体
        ec.putEntity(ds.getKey(user), user);

        // 代理
        ec.putProxy(ds.getKey(user), user);
        System.out.println("getKey: " + ds.getKey(user));
        System.out.println("getProxy: " + ec.getProxy(ds.getKey(user)));
        System.out.println("getEntity: " + ec.getEntity(ds.getKey(user)));
        System.out.println(ec.exists(ds.getKey(user)));
        System.out.println("stats: " + ec.stats());

    }*/
    
}
