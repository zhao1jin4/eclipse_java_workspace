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
		//��ʵNoSQL���� ���� �� ֻ�������ֶ�  ,һ��_id����Ϊvarbinary(32) ,һ��doc ����ΪJSON
		{
			Schema db=mySession.getSchema("mydb");
			Table my_collection = db.getCollectionAsTable("my_collection");
			//my_collection.insert("doc").values("{\"username\": \"Ana\"}").execute();//����??
		}
		
		nosql(mySession);
		
		//����   ʹ��mysqlx://Э��ͬnosql�ͱ�
		transaction(mySession);
		
		mySession.close();
	}
	public static void nosql(	Session mySession )  
	{
		Schema  db= mySession.getSchema("mydb");
		Collection myColl = db.getCollection("my_collection");
		//Collection myColl = db.getCollection("my_collection",true);//�ڶ�������requireExists���粻���ڱ��쳣
		if(DatabaseObject.DbObjectStatus.NOT_EXISTS == myColl.existsInDatabase())
		{
			myColl = db.createCollection("my_collection");
			DbDocImpl doc=new DbDocImpl()  ;
			doc.add("age",  new JsonNumber().setValue("18"));
			doc.add("name",  new JsonString().setValue("��"));
			myColl.add(doc).execute();

			myColl.add("{\"name\":\"Laurie\", \"age\":19}").execute();
			myColl.add("{\"name\":\"Nadya\", \"age\":54}" ,"{\"name\":\"Lukas\", \"age\":32}").execute();
			
		}
		
		
		DocResult docs = myColl.find("name like :name or age < :age")
		        .bind("name", "L%").bind("age", 20).execute();//ͨ���%��MySQLһ��
//		while(docs.hasNext())//ͬJDBC
//		{
//			DbDoc doc=docs.next();
//			JsonValue val=new JsonString().setValue("2020");//��JsonDate
//			System.out.println(doc.get("name") + doc.getOrDefault("birdthday",val ).toString());
//		}
		

		myColl.modify("true").set("age", 19).execute(); //expr( "age + 1") ���д���ʽ����
		
		Map<String, Object> params = new HashMap<>();
		params.put("name", "Nadya");
		myColl.modify("name = :name").set(".age", 25).bind(params).execute();//.age
		
		DbDoc  ds=	docs.fetchOne();//docs.fetchAll()
		String id=ds.get("_id").toFormattedString().replace("\"", "");//��β��"
		
		DbDoc doc2 = myColl.getOne(id); //��ͬ��  myColl.find("_id = :id").bind("id", id).execute().fetchOne()
		System.out.println(doc2);//��������??
		myColl.replaceOne(id, "{\"name\":\"��1\", \"age\":11}");
		//myColl.addOrReplaceOne("101", "{\"name\":\"��\", \"age\":33}");//����ʱ�����ֹ�ָ��id
		myColl.removeOne(id);//�൱�� myColl.remove("_id = :id").bind("id", id).execute()
		
		
		//�������� 
		myColl.createIndex("age", "{\"fields\":[{\"field\": \"$.age\", \"type\":\"INT\", \"required\":true}]}");
		// {fields: [{field: '$.age', type: 'INT'},{field: '$.username', type: 'TEXT(10)'}]}
		SqlResult myResult =mySession.sql("SHOW INDEX FROM mydb.my_collection").execute();
		for(Row row : myResult.fetchAll() )
		{
			System.out.println(row.getString("Key_name")+","+row.getString(2));
			
		}
		//����������Ҫ��8.0.17�汾�Ժ�
//		collection.createIndex("emails_idx",  
//			    {fields: [{"field": "$.emails", "type":"CHAR(128)", "array": true}]});
		myColl.dropIndex("age");
		
		db.dropCollection("my_collection");
	}
	//��������NoSQL�͹����ͱ� ��һ��������
	public static void transaction(Session mySession) {
		Schema  db= mySession.getSchema("mydb");
		Table employeeTable = db.getTable("employee");
		Collection myColl = db.getCollection("my_collection");
		
		mySession.startTransaction(); 
		
		employeeTable.insert("id", "username","age")
		  .values(2004, "��2",22)
		  .execute();
		
		mySession.setSavepoint("level1");

		DbDocImpl doc=new DbDocImpl()  ;
		doc.add("age",  new JsonNumber().setValue("28"));
		doc.add("name",  new JsonString().setValue("��"));
		myColl.add(doc).execute();
		
		mySession.setSavepoint("level2");
		
		myColl.add("{\"name\":\"lisi\", \"age\":29}").execute();
		
		mySession.rollbackTo("level1");
		mySession.commit();
	}
}
