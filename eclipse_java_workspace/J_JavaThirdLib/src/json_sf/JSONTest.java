package json_sf;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.Morpher;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

/* 
  已经有 javax.json
<dependency>
    <groupId>net.sf.json-lib</groupId>
    <artifactId>json-lib</artifactId>
	<classifier>jdk15</classifier>
    <version>2.4</version>
</dependency> 
 *
 */
public class JSONTest
{
	public static void main(String[] args) throws Exception
	{
		//org.apache.commons.collections.map.ListOrderedMap deps;//就从前的版本
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd HH:mi:ss"}));
		
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
		
		UserModel userObject=new UserModel();
		userObject.setId(11);
		userObject.setName("JSON传输 ");
		userObject.setBirthday(new Date());
		List<Order> orders=new ArrayList<>();
		orders.add(new Order("111","order1"));
		orders.add(new Order("222","order3")); 
		userObject.setOrders(orders);
		objArray[0]=userObject;
		
		JSONObject jsonObject = JSONObject.fromObject(userObject);
		String strJsonObj=jsonObject.toString();
		System.out.println("java Object to json : "+ strJsonObj); //日期还是long类型
		
		JSONArray jsonArrasy = JSONArray.fromObject(userObject);
		System.out.println("java Array to json : "+ jsonArrasy); 
		
		//Map->String
		Map<String,Object> map=new HashMap<>();
		map.put("name", "lisi");
		map.put("id", 123);
		JSONObject jsonMapObject = JSONObject.fromObject(map);
		String strJsonMap=jsonMapObject.toString();
		System.out.println("Map Object to json : "+ strJsonMap); 
		
		
		//String->Object
		//如对象里有一个List<Order>不能正确转换成集合中的对象,Order要有默认构造器  
		Map<String, Class<Order>> classMap = new HashMap<>();
		classMap.put("orders", Order.class);
		UserModel userModel = (UserModel) JSONObject.toBean(JSONObject.fromObject(strJsonObj), UserModel.class,classMap);
		//方式二
		JsonConfig jsonConfig = new JsonConfig();  
		jsonConfig.setRootClass(UserModel.class);  
		jsonConfig.setClassMap(classMap);
		userModel = (UserModel) JSONObject.toBean(JSONObject.fromObject(strJsonObj),jsonConfig);
		System.out.println("userModel: "+ userModel); 
		
		//String->Map
		Map userMap = (Map) JSONObject.toBean(JSONObject.fromObject(strJsonMap), Map.class);
		System.out.println("userMap: "+ userMap); 
		  
	}
}
