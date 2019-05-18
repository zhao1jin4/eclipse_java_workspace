package rabbitmq;

import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.tools.json.JSONReader;
import com.rabbitmq.tools.json.JSONWriter;

import net.sf.json.JSONObject;

public class RabbitMQJSON {
	public static String map2json( )
	{
		Map<String,Object> para=new HashMap<>();
		para.put("username","李四");
		para.put("ageame",20);
		String jsonStr = new com.rabbitmq.tools.json.JSONWriter().write(para);//Object(可Map)->JSON
		System.out.println(jsonStr);
		return jsonStr;
	}
	public static void json2Obj(String jsonStr )
	{  
		Object obj = new com.rabbitmq.tools.json.JSONReader().read(jsonStr);//返回Object是一个HashMap
		JSONObject jsonObj=JSONObject.fromObject(obj);//net.sf
		System.out.println(jsonObj);
	}
	public static void main(String[] args)
	{
		String jsonStr=map2json( );
		json2Obj(jsonStr );
		   
	}

}
