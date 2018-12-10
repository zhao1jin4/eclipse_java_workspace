package nosql_mongodb; 

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.Block;
import com.mongodb.ConnectionString;
import com.mongodb.ServerAddress;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.DeleteOneModel;
import   com.mongodb.client.model.Filters ;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.ReplaceOneModel;
import   com.mongodb.client.model.Sorts ;
import com.mongodb.client.model.TextSearchOptions;
import com.mongodb.client.model.UpdateOneModel;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.*;
import static com.mongodb.client.model.Projections.*;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.connection.ClusterSettings;
import com.mongodb.connection.ClusterSettings.Builder;

public class MongoAsyncTest
{
	
	
	public static void main(String[] args) throws Exception
	{
//		MongoClient client = MongoClients.create();//default port 27017, default connection string "mongodb://localhost" 
//		MongoClient client = MongoClients.create("mongodb://localhost");
//		MongoClient client = MongoClients.create(new ConnectionString("mongodb://localhost"));
		
		ClusterSettings clusterSettings = ClusterSettings.builder().hosts(Collections.singletonList((new ServerAddress("localhost",27017)))).build();
		Block<Builder> block=new Block<Builder>(){
			@Override
			public void apply(Builder builder) {
				builder.hosts(Collections.singletonList((new ServerAddress("localhost",27017))));
			}
		};
		MongoClientSettings settings = MongoClientSettings.builder().applyToClusterSettings(block).build();
		MongoClient client = MongoClients.create(settings);

		
		MongoDatabase database = client.getDatabase("mydb");
		MongoCollection<Document> collection = database.getCollection("mycoll");
		collection.drop(new SingleResultCallback<Void>() {
				@Override
				public void onResult(Void res, Throwable throwable) {
					   System.out.println("droped");
				}
			});

		Document doc = new Document("name", "MongoDB")
        .append("type", "database")
        .append("count", 1)
        .append("info", new Document("x", 203).append("y", 102));
		
		//支持JDK8
		collection.insertOne(doc, (Void result, final Throwable t) -> System.out.println("Inserted!"));
		
		Document document = new Document("x", 1);
		collection.insertOne(document, new SingleResultCallback<Void>() {
		   @Override
		   public void onResult(final Void result, final Throwable t) {
		       System.out.println("Inserted!");
		   }
		});
		
		document.append("x", 2).append("y", 3);
		//模板是  UpdateResult
		collection.replaceOne(Filters.eq("_id", document.get("_id")), document, 
		    new SingleResultCallback<UpdateResult>() {
		       @Override
		       public void onResult(final UpdateResult result, final Throwable t) {
		           System.out.println(result.getModifiedCount());
		       }
		   });
		
		SingleResultCallback<Document> printDocument = new SingleResultCallback<Document>() {
		    @Override
		    public void onResult(final Document document, final Throwable t) {
		        System.out.println(document.toJson());
		    }
		};
		collection.find().first(printDocument);
		
		
		//模板是  List<Document>
		collection.find().into(new ArrayList<Document>(), 
		    new SingleResultCallback<List<Document>>() {
		        @Override
		        public void onResult(final List<Document> result, final Throwable t) {
		            System.out.println("Found Documents: #" + result.size());
		        }
		    });
		
		
		List<Document> documents = new ArrayList<Document>();
		for (int i = 0; i < 100; i++) {
		    documents.add(new Document("i", i));
		}
		//一次插入多条
		collection.insertMany(documents, new SingleResultCallback<Void>() {
		    @Override
		    public void onResult(final Void result, final Throwable t) {
		        System.out.println("Documents inserted!");
		    }
		});
		
		//总记录数 
		collection.estimatedDocumentCount( new SingleResultCallback<Long>() {
		      @Override
		      public void onResult(final Long count, final Throwable t) {
		          System.out.println(count);
		      }
		  });
		collection.countDocuments(new SingleResultCallback<Long>() {
		      @Override
		      public void onResult(final Long count, final Throwable t) {
		          System.out.println(count);
		      }
		  });
		
		Block<Document> printDocumentBlock = new Block<Document>() {
		    @Override
		    public void apply(final Document document) {//查询到的每个Document调用一次
		        System.out.println(document.toJson());
		    }
		};
		SingleResultCallback<Void> callbackWhenFinished = new SingleResultCallback<Void>() {
		    @Override
		    public void onResult(final Void result, final Throwable t) {//只在完成时调用一次
		        System.out.println("Operation Finished!");
		    }
		};
		collection.find().forEach(printDocumentBlock, callbackWhenFinished);
		
		collection.find(Filters.gt("i", 50)).forEach(printDocumentBlock, callbackWhenFinished);
		collection.find(Filters.exists("i")).sort(Sorts.descending("i")).first(printDocument);
		collection.find().projection(excludeId()).first(printDocument);//Projections.excludeId
		
		collection.updateOne(eq("i", 10), new Document("$set", new Document("i", 110)),
			    new SingleResultCallback<UpdateResult>() {
			        @Override
			        public void onResult(final UpdateResult result, final Throwable t) {
			            System.out.println(result.getModifiedCount());
			        }
			    });
		collection.updateMany(lt("i", 100), new Document("$inc", new Document("i", 100)),
			    new SingleResultCallback<UpdateResult>() {
			        @Override
			        public void onResult(final UpdateResult result, final Throwable t) {
			            System.out.println(result.getModifiedCount());
			        }
			    });
		
		
		SingleResultCallback<BulkWriteResult> printBatchResult = new SingleResultCallback<BulkWriteResult>() 
		{
		    @Override
		    public void onResult(final BulkWriteResult result, final Throwable t) {
		    	if(result !=null  && result.getMatchedCount()>0 )
		    	{
		    		  System.out.println("共"+result.getMatchedCount()+"条记录match,delete数:" +result.getDeletedCount() + 
		    				  	",insert数:"+result.getInsertedCount() +
		    				  	",modified数:"+result.getModifiedCount());
		    	}else 
		    	{
		    		  System.out.println( "BulkWriteResult is null or result.getMatchedCount()=0");
		    	}
		    }
		};

		//批量按顺序做
		collection.bulkWrite(
		  Arrays.asList(new InsertOneModel<>(new Document("myid", 4)),
		                new InsertOneModel<>(new Document("myid", 5)),
		                new InsertOneModel<>(new Document("myid", 6)),
		                new UpdateOneModel<>(new Document("myid", 1),
		                                     new Document("$set", new Document("x", 2))),
		                new DeleteOneModel<>(new Document("myid", 2)),
		                new ReplaceOneModel<>(new Document("myid", 3),
		                                      new Document("myid", 3).append("x", 4))),
		  printBatchResult 
		);


		collection.bulkWrite(
			  Arrays.asList(new InsertOneModel<>(new Document("myid", 4)),
			                new InsertOneModel<>(new Document("myid", 5)),
			                new InsertOneModel<>(new Document("myid", 6)),
			                new UpdateOneModel<>(new Document("myid", 1),
			                                     new Document("$set", new Document("x", 2))),
			                new DeleteOneModel<>(new Document("myid", 2)),
			                new ReplaceOneModel<>(new Document("myid", 3),
			                                      new Document("myid", 3).append("x", 4))),
			  new BulkWriteOptions().ordered(false),//批量不按顺序做,不加这个参数默认是按顺序做的
			  printBatchResult
		);
	
		
		//模板是DeleteResult
				collection.deleteOne(eq("i", 110), new SingleResultCallback<DeleteResult>() {
				    @Override
				    public void onResult(final DeleteResult result, final Throwable t) {
				        System.out.println(result.getDeletedCount());
				    }
				});
				
				collection.deleteMany(gte("i", 100), new SingleResultCallback<DeleteResult>() {
				    @Override
				    public void onResult(final DeleteResult result, final Throwable t) {
				        System.out.println(result.getDeletedCount());
				    }
				});
		
		  //-----------------------------------------------------
				
			client.listDatabaseNames().forEach(new Block<String>() {
				    @Override
				    public void apply(final String s) {
				        System.out.println(s);
				    }
				}, callbackWhenFinished);
			 
			database.createCollection("cappedCollection",
					  new CreateCollectionOptions().capped(true).sizeInBytes(0x100000),
					  callbackWhenFinished);
				
			database.listCollectionNames().forEach(new Block<String>() {
			    @Override
			    public void apply(final String databaseName) {
			        System.out.println(databaseName);
			    }
			}, callbackWhenFinished);
			
			collection.drop(callbackWhenFinished);
			collection.createIndex(new Document("i", 1), new SingleResultCallback<String>() {
				    @Override
				    public void onResult(final String result, final Throwable t) { 
				        System.out.println("Operation Finished!");
				    }
				});
			
			collection.listIndexes().forEach(printDocumentBlock, callbackWhenFinished);
			// Insert some documents
			collection.insertOne(new Document("_id", 0).append("content", "textual content"), callbackWhenFinished);
			collection.insertOne(new Document("_id", 1).append("content", "additional content"), callbackWhenFinished);
			collection.insertOne(new Document("_id", 2).append("content", "irrelevant content"), callbackWhenFinished);
			
			// Find using the text index
			  collection.countDocuments(text("textual content -irrelevant"), new SingleResultCallback<Long>() {
				    @Override
				    public void onResult(final Long result, final Throwable t) { 
				        System.out.println("count is "+result);
				    }
				});

			// Find the highest scoring match
			// Find using the text index
			collection.countDocuments(text("textual content -irrelevant"), new SingleResultCallback<Long>() {
			    @Override
			    public void onResult(final Long matchCount, final Throwable t) {
			        System.out.println("Text search matches: " + matchCount);
			    }
			});


			// Find using the $language operator
			Bson textSearch = Filters.text("textual content -irrelevant", new TextSearchOptions().language("english"));
			collection.countDocuments(textSearch, new SingleResultCallback<Long>() {
			    @Override
			    public void onResult(final Long matchCount, final Throwable t) {
			        System.out.println("Text search matches (english): " + matchCount);
			    }
			});

			// Find the highest scoring match
			Document projection = new Document("score", new Document("$meta", "textScore"));
			collection.find(textSearch).projection(projection).first(new SingleResultCallback<Document>() {
			    @Override
			    public void onResult(final Document highest, final Throwable t) {
			        System.out.println("Highest scoring document: " + highest.toJson());

			    }
			});
			
			database.runCommand(new Document("buildInfo", 1), new SingleResultCallback<Document>() {
			    @Override
			    public void onResult(final Document buildInfo, final Throwable t) {
			        System.out.println(buildInfo);
			    }
			});
			
		Thread.sleep(10*1000);
	}
}
