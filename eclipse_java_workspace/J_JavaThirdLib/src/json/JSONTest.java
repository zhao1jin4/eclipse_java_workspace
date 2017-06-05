package json;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class JSONTest
{
	public static void main(String[] args) throws Exception
	{
		JSONArray array = new JSONArray();
		
		JSONObject obj = new JSONObject();
		obj.put("id", 100);
		obj.put("username",URLEncoder.encode("李","UTF-8"));
		obj.put("password", 123);
		array.add(obj);
		
		JSONObject obj1 = new JSONObject();
		obj1.put("id", 101);
		obj1.put("username",true);
		obj1.put("password", 123);
		array.add(obj1);
		System.out.println(array.toString());
		
		
		//---任何对象转JSON
		UserModel[] objArray=new UserModel[2]; 
		
		UserModel ua=new UserModel();
		ua.setId(11);
		ua.setName("JSON传输 ");
		objArray[0]=ua;
		
		JSONObject jsonObject = JSONObject.fromObject(ua);
		System.out.println("java Object to json : "+ jsonObject); 
		
		JSONArray jsonArrasy = JSONArray.fromObject(ua);
		System.out.println("java Array to json : "+ jsonArrasy); 
		
		Map map=new HashMap();
		map.put("name", "lisi");
		map.put("id", 123);
		jsonObject = JSONObject.fromObject(ua);
		System.out.println("Map Object to json : "+ jsonObject); 
	}
}
