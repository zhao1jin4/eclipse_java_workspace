package nosql_mongodb;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.ClientSession;
import com.mongodb.client.FindIterable;
import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
public class MongoTest
{
	public static void insert(MongoDatabase db)
	{
		MongoCollection<Document> coll = db.getCollection("bios");
		Document doc = new Document("name", "MongoDB").
                append("type", "database").
                append("count", 1).
                append("info", new BasicDBObject("x", 203).append("y", 102));
		coll.insertOne(doc);

		coll.createIndex(new Document("name", 1));
		 
		db.createCollection("cappedCollection",
				  new CreateCollectionOptions().capped(true).sizeInBytes(0x100000));
		
		MongoCollection<Document> collection =db.getCollection("cappedCollection");
		 
		// Insert some documents
		collection.insertOne(new Document("_id", 0).append("content", "textual content"));
		collection.insertOne(new Document("_id", 1).append("content", "additional content"));
		collection.insertOne(new Document("_id", 2).append("content", "irrelevant content"));
		
	}
	
	public static void query(MongoDatabase db)
	{

		System.out.println("-- bios collection   all Index");
		MongoCollection<Document> coll = db.getCollection("bios");
		ListIndexesIterable<Document> list=coll.listIndexes();
		for (Document o : list) {
		   System.out.println(o);
		}
		System.out.println("-- bios collection count:"+coll.countDocuments());
		System.out.println("-- bios collection count:"+coll.estimatedDocumentCount());
		
//		FindIterable<Document> cursor = coll.find();
//		Bson query = new BasicDBObject("name", "MongoDB");
		Bson query = new BasicDBObject("info.x",new BasicDBObject("$lte", 300) ).
										append("name", "MongoDB" );
		FindIterable<Document> iter = coll.find(query);
		MongoCursor<Document> cursor=iter.iterator();
		try {
		   while(cursor.hasNext()) {
		       System.out.println(cursor.next());
		   }
		} finally {
		   cursor.close();
		}
	}
	 
	public static void update(MongoDatabase db ) throws Exception
	{
		MongoCollection<Document> collection =db.getCollection("bios");
		UpdateResult rs=collection.updateOne(new Document("name", "MongoDB"), new Document("$set", new Document("count", 2)));
		System.out.println("update effect rows:"+rs.getModifiedCount());
	}
	
	public static void delete(MongoDatabase db) throws Exception
	{
		MongoCollection<Document> collection=db.getCollection("bios");
		DeleteResult rs=collection.deleteMany(new Document("name", "MongoDB")); //new ObjectId("");
		System.out.println("deleted rows:"+rs.getDeletedCount());
		
	}	
	
	public static void transaction(  )//测试OK
	{

		/*
		 use reporting
		db.createUser(
			    {
			      user: "zh",
			      pwd: "123",
			      roles: [
				     { role: "readWrite", db: "reporting" } ,
					 { role: "readAnyDatabase", db: "admin" } //有权限可以show dbs 即listDatabases
				 ]
			    }
			)  
			*/
//		Multi-document transactions are available for replica sets only. 
//		Transactions for sharded clusters are scheduled for MongoDB 4.2
	
		MongoCredential credential = MongoCredential.createCredential("zh", "reporting", "123".toCharArray());
		
		
		//replication set 事务OK
		ServerAddress[] addrs=	new ServerAddress[] {
				new ServerAddress("127.0.0.1", 37017),
				new ServerAddress("127.0.0.1", 37018),
				new ServerAddress("127.0.0.1", 37019)};
		
		//单机不支持事务
//		ServerAddress[] addrs=	new ServerAddress[] { new ServerAddress("127.0.0.1", 27017) };
		
		MongoClientOptions opts= new MongoClientOptions.Builder().build();
		MongoClient mongoClient  = new MongoClient(Arrays.asList(addrs), credential,opts );  
	
	 
//		db.employees.insert({employee:3,status:'none'})
//		db.events.insert({})
		MongoCollection<Document> employeesCollection = mongoClient.getDatabase("reporting").getCollection("employees");
		employeesCollection.drop();
		employeesCollection.insertOne(new Document("employee",3).append("status", "none"));
	    MongoCollection<Document> eventsCollection = mongoClient.getDatabase("reporting").getCollection("events");
	    eventsCollection.drop();
	    eventsCollection.insertOne(new Document());
		
	    ClientSession clientSession = mongoClient.startSession();
	    try   {
	        clientSession.startTransaction();

	        employeesCollection.updateOne(clientSession,
	                Filters.eq("employee", 3),
	                Updates.set("status", "Inactive"));
	        eventsCollection.insertOne(clientSession,
	                new Document("employee", 3).append("status", new Document("new", "Inactive").append("old", "Active")));

	        clientSession.commitTransaction();
	    
	    }catch(Exception e)
		{
	    	e.printStackTrace();
	    	clientSession.abortTransaction();
		}finally {
			clientSession.close();
		}
	}
	 
	public static void main(String[] args) throws Exception
	{

		transaction();
		
		
		//MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		
//        MongoCredential credential = MongoCredential.createCredential("siteUserAdmin", "admin", "password".toCharArray());  
	    MongoCredential credential = MongoCredential.createCredential("zh", "reporting", "123".toCharArray());
	    MongoClientOptions opts= new MongoClientOptions.Builder().build();
        MongoClient mongoClient  = new MongoClient(Arrays.asList(new ServerAddress("localhost", 27017)), credential,opts );  
	        
//		MongoClient mongoClient = new MongoClient(Arrays.asList(new ServerAddress("localhost", 27017),
//		                                      new ServerAddress("localhost", 27018),
//		                                      new ServerAddress("localhost", 27019)));
		
		MongoDatabase db = mongoClient.getDatabase( "reporting" );//= use reporting
		 System.out.println("--all db");
		for (String s : mongoClient.listDatabaseNames()) {//= show dbs    ,如服务端打开 --auth 这里验证权限 
			   System.out.println(s);
		}
		System.out.println("--test db all collection");
		MongoIterable<String> colls = db.listCollectionNames();//= show collections
		for (String s : colls) {
		    System.out.println(s);
		}
		
		db.drop();
		
		   
		insert(db);
	   update(db);
	   query(db);
	   delete(db);
		

		 mongoClient.close();
	}
}
