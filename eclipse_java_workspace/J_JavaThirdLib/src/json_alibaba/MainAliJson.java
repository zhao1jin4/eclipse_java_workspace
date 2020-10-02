package json_alibaba;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

import json_fasterxml.OrderJson;
import json_fasterxml.UserJson;

public class MainAliJson {
 
	public static void main(String ... args  ) throws Exception {
		 
		//obj2JsonIgnore();
		json2Obj();

		//用 FasterXml
		//obj2Json();
		
		//json2Map();
		//map2Json();
		
		//map->object，object-> map要间接来一次？
		
		//json2List();
	}
	public static String obj2JsonIgnore() throws Exception {
		MyIgnoreObject obj=new MyIgnoreObject();
		obj.setUserName("李"); //@JSONField(name="_userName")才有用
		obj.setPassword("123");//@JSONField(serialize=false,deserialize=false)
		obj.setJoinDate(new Date());
		String jsonString=JSON.toJSONString(obj);
	    System.out.println(jsonString);
	    return jsonString;
	}
	public static void json2Obj() throws Exception {
		String str= obj2JsonIgnore();
		MyIgnoreObject obj=JSON.parseObject(str,MyIgnoreObject.class);
		System.out.println(obj.getUserName());
	}
	
	public static String obj2Json() throws Exception {
		UserJson user=new UserJson();
		user.setUserName("李四"); 
		user.setPassword("123");//alibaba JSON 用fasterxml的@JsonIgnore没用
		List<String> favorite=new ArrayList<>();
		favorite.add("足球");
		favorite.add("音乐");
		user.setFavorite(favorite);

		OrderJson order =new OrderJson ();
		order.setAddress("上海");
		order.setAmount(3);
		order.setPrice(BigDecimal.valueOf(3.4));
		user.setOrder(order);
		String jsonString=JSON.toJSONString(user);
	    System.out.println(jsonString);
	    return jsonString;
	}
	
	public static Map<String,Object> json2Map() throws Exception 
	{
		String jsonStr=obj2Json();
		JSONObject  jsonObject = JSONObject.parseObject(jsonStr);
		 Map<String,Object> map = (Map<String,Object>)jsonObject;
	    System.out.println("map对象是：" + map);
	    return map;
		
	}
	public static String map2Json() throws Exception 
	{
		Map<String,Object> map=json2Map();
		JSONObject json = new JSONObject(map);
		System.out.println("Json对象是：" + json); 
		return json.toJSONString();
	}
	public static  void json2List() throws Exception {
		
		List<OrderJson> orders=new ArrayList<>();
		
		OrderJson order =new OrderJson ();
		order.setAddress("上海");
		order.setAmount(3);
		orders.add(order);
		
		OrderJson order1 =new OrderJson ();
		order1.setAddress("上海1");
		order1.setAmount(31); 
		orders.add(order1);
		
		String jsonString=JSON.toJSONString(orders );
		System.out.println(jsonString);
		List<OrderJson> res=JSON.parseArray(jsonString, OrderJson.class);
		System.out.println(res);

	}

}