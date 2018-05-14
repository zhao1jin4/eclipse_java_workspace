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
		String strJsonObj=jsonObject.toString();
		System.out.println("java Object to json : "+ strJsonObj); 
		
		JSONArray jsonArrasy = JSONArray.fromObject(ua);
		System.out.println("java Array to json : "+ jsonArrasy); 
		
		//Map->String
		Map<String,Object> map=new HashMap<>();
		map.put("name", "lisi");
		map.put("id", 123);
		JSONObject jsonMapObject = JSONObject.fromObject(map);
		String strJsonMap=jsonMapObject.toString();
		System.out.println("Map Object to json : "+ strJsonMap); 
		
		
		//String->Object
		UserModel userModel = (UserModel) JSONObject.toBean(JSONObject.fromObject(strJsonObj), UserModel.class);
		System.out.println("userModel: "+ userModel); 
		
		//String->Map
		Map userMap = (Map) JSONObject.toBean(JSONObject.fromObject(strJsonMap), Map.class);
		System.out.println("userMap: "+ userMap); 
		  
	}
}
