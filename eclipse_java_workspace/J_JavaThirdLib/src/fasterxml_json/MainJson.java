package fasterxml_json;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MainJson {
	public static void main(String ... args  ) throws Exception {
		//json2String();
		json2Obj();
	}
	public static String obj2Json() throws Exception {
		UserJson user=new UserJson();
		user.setJoinDate(new Date());
		user.setUserName("李四");
		List<String> favorite=new ArrayList<>();
		favorite.add("足球");
		favorite.add("音乐");
		user.setFavorite(favorite);

		OrderJson order =new OrderJson ();
		order.setAddress("上海");
		order.setAmount(3);
		order.setPrice(BigDecimal.valueOf(3.4));
		user.setOrder(order);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	    String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
	    System.out.println(jsonString);
	    return jsonString;
	}
	public static void json2Obj() throws Exception {
		ObjectMapper mapper = new  ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);//反序列化遇到未知属性不报异常
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);//允许使用未带引号的字段名
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true); //允许使用单引号
		String str= obj2Json();
		UserJson user=mapper.readValue(str, UserJson.class);
		System.out.println(user.getUserName());
		System.out.println(user.getJoinDate());
	}

}
