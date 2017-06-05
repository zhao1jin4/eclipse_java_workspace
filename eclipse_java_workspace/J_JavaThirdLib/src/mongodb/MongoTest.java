package mongodb;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.CreateCollectionOptions;
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
		System.out.println("-- bios collection count:"+coll.count());
		
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
	public static void main(String[] args) throws Exception
	{
		//MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		
	    List<MongoCredential> credentialList = new ArrayList<MongoCredential>();  
      //  MongoCredential credential = MongoCredential.createCredential("siteUserAdmin", "admin", "password".toCharArray());  
	    MongoCredential credential = MongoCredential.createCredential("zh", "reporting", "123".toCharArray());
        credentialList.add(credential);  
        MongoClient mongoClient  = new MongoClient(Arrays.asList(new ServerAddress("10.1.5.226", 27017)), credentialList);  
	        
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
