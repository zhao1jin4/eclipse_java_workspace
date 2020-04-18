package  nosql_mysql_xdevapi;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.cj.xdevapi.Collection;
import com.mysql.cj.xdevapi.DatabaseObject;
import com.mysql.cj.xdevapi.DbDoc;
import com.mysql.cj.xdevapi.DbDocImpl;
import com.mysql.cj.xdevapi.DocResult;
import com.mysql.cj.xdevapi.JsonNumber;
import com.mysql.cj.xdevapi.JsonString;
import com.mysql.cj.xdevapi.JsonValue;
import com.mysql.cj.xdevapi.Row;
import com.mysql.cj.xdevapi.Schema;
import com.mysql.cj.xdevapi.Session;
import com.mysql.cj.xdevapi.SessionFactory;
import com.mysql.cj.xdevapi.SqlResult;
import com.mysql.cj.xdevapi.Table;
public class XDevApiCollectionMain {
	public static void main(String[] args) {
		
		Session mySession = new SessionFactory().getSession("mysqlx://localhost:33060/mydb?user=zh&password=123");
		//其实NoSQL集合 就是 表 只有两个字段  ,一个_id类型为varbinary(32) ,一个doc 类型为JSON
		{
			Schema db=mySession.getSchema("mydb");
			Table my_collection = db.getCollectionAsTable("my_collection");
			//my_collection.insert("doc").values("{\"username\": \"Ana\"}").execute();//不行??
		}
		
		nosql(mySession);
		
		//事务   使用mysqlx://协议同nosql和表
		transaction(mySession);
		
		mySession.close();
	}
	public static void nosql(	Session mySession )  
	{
		Schema  db= mySession.getSchema("mydb");
		Collection myColl = db.getCollection("my_collection");
		//Collection myColl = db.getCollection("my_collection",true);//第二个参数requireExists，如不存在报异常
		if(DatabaseObject.DbObjectStatus.NOT_EXISTS == myColl.existsInDatabase())
		{
			myColl = db.createCollection("my_collection");
			DbDocImpl doc=new DbDocImpl()  ;
			doc.add("age",  new JsonNumber().setValue("18"));
			doc.add("name",  new JsonString().setValue("王"));
			myColl.add(doc).execute();

			myColl.add("{\"name\":\"Laurie\", \"age\":19}").execute();
			myColl.add("{\"name\":\"Nadya\", \"age\":54}" ,"{\"name\":\"Lukas\", \"age\":32}").execute();
			
		}
		
		
		DocResult docs = myColl.find("name like :name or age < :age")
		        .bind("name", "L%").bind("age", 20).execute();//通配符%和MySQL一样
//		while(docs.hasNext())//同JDBC
//		{
//			DbDoc doc=docs.next();
//			JsonValue val=new JsonString().setValue("2020");//无JsonDate
//			System.out.println(doc.get("name") + doc.getOrDefault("birdthday",val ).toString());
//		}
		

		myColl.modify("true").set("age", 19).execute(); //expr( "age + 1") 如何写表达式？？
		
		Map<String, Object> params = new HashMap<>();
		params.put("name", "Nadya");
		myColl.modify("name = :name").set(".age", 25).bind(params).execute();//.age
		
		DbDoc  ds=	docs.fetchOne();//docs.fetchAll()
		String id=ds.get("_id").toFormattedString().replace("\"", "");//首尾带"
		
		DbDoc doc2 = myColl.getOne(id); //等同于  myColl.find("_id = :id").bind("id", id).execute().fetchOne()
		System.out.println(doc2);//中文乱码??
		myColl.replaceOne(id, "{\"name\":\"中1\", \"age\":11}");
		//myColl.addOrReplaceOne("101", "{\"name\":\"国\", \"age\":33}");//增加时可以手工指定id
		myColl.removeOne(id);//相当于 myColl.remove("_id = :id").bind("id", id).execute()
		
		
		//创建索引 
		myColl.createIndex("age", "{\"fields\":[{\"field\": \"$.age\", \"type\":\"INT\", \"required\":true}]}");
		// {fields: [{field: '$.age', type: 'INT'},{field: '$.username', type: 'TEXT(10)'}]}
		SqlResult myResult =mySession.sql("SHOW INDEX FROM mydb.my_collection").execute();
		for(Row row : myResult.fetchAll() )
		{
			System.out.println(row.getString("Key_name")+","+row.getString(2));
			
		}
		//数组索引，要求8.0.17版本以后
//		collection.createIndex("emails_idx",  
//			    {fields: [{"field": "$.emails", "type":"CHAR(128)", "array": true}]});
		myColl.dropIndex("age");
		
		db.dropCollection("my_collection");
	}
	//两种数据NoSQL和关联型表 在一个事务中
	public static void transaction(Session mySession) {
		Schema  db= mySession.getSchema("mydb");
		Table employeeTable = db.getTable("employee");
		Collection myColl = db.getCollection("my_collection");
		
		mySession.startTransaction(); 
		
		employeeTable.insert("id", "username","age")
		  .values(2004, "陈2",22)
		  .execute();
		
		mySession.setSavepoint("level1");

		DbDocImpl doc=new DbDocImpl()  ;
		doc.add("age",  new JsonNumber().setValue("28"));
		doc.add("name",  new JsonString().setValue("赵"));
		myColl.add(doc).execute();
		
		mySession.setSavepoint("level2");
		
		myColl.add("{\"name\":\"lisi\", \"age\":29}").execute();
		
		mySession.rollbackTo("level1");
		mySession.commit();
	}
}
