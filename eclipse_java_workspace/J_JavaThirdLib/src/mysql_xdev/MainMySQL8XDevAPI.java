package mysql_xdev;
import com.mysql.cj.xdevapi.*;
public class MainMySQL8XDevAPI {

	public static void main(String[] args)
	{
		//X DevAPI  异步API 基于 X Protocol,依赖于com.google.protobuf
//		sql();//OK
		 nosql( ) ;
	}
	
	public static void sql( ) 
	{
		// Connect to server on localhost
		Session mySession = new SessionFactory().getSession("mysqlx://localhost:33060/mydb?user=user1&password=user1");
		// Switch to use schema 'test'
		mySession.sql("USE mydb").execute();
/*
		// In a Session context the full SQL language can be used
		mySession.sql("CREATE PROCEDURE my_add_one_procedure " + " (INOUT incr_param INT) " + "BEGIN " + "  SET incr_param = incr_param + 1;" + "END")
		        .execute();
		mySession.sql("SET @my_var = ?").bind(10).execute();
		mySession.sql("CALL my_add_one_procedure(@my_var)").execute();
		mySession.sql("DROP PROCEDURE my_add_one_procedure").execute();

		// Use an SQL query to get the result
		SqlResult myResult = mySession.sql("SELECT @my_var").execute();
*/
		SqlResult myResult = mySession.sql("SELECT 1+1").execute();
		// Gets the row and prints the first column
		Row row = myResult.fetchOne();
		System.out.println(row.getInt(0));

		mySession.close();
	}
	public static void nosql( ) 
	{
		//mysqlx:协议,端口是33060
		Session mySession = new SessionFactory().getSession("mysqlx://localhost:33060/mydb?user=user1&password=user1");
		Schema myDb = mySession.getSchema("mydb");
		Collection myColl = myDb.createCollection("my_collection");
		myColl.add("{\"name\":\"Sakila\", \"age\":15}").execute();
		myColl.add("{\"name\":\"Susanne\", \"age\":24}").execute();
		myColl.add("{\"name\":\"User\", \"age\":39}").execute();
		// Find a document
		DocResult docs = myColl.find("name like :name AND age < :age")
		        .bind("name", "S%").bind("age", 20).execute();
		DbDoc doc = docs.fetchOne();
		System.out.println(doc);
		 
		
		  
		
		
		Collection myColl2 = myDb.getCollection("my_collection"); 
		// Specify which document to find with Collection.find() and
		// fetch it from the database with .execute()
		DocResult myDocs = myColl2.find("name like :param").limit(1).bind("param", "S%").execute();
		System.out.println(myDocs.fetchOne()); 
		
		myDb.dropCollection("my_collection");
		mySession.close();
		 
		
	}
}
