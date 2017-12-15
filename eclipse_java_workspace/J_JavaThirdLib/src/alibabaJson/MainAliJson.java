package alibabaJson;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import fasterxml_json.OrderJson;
import fasterxml_json.UserJson;

public class MainAliJson {
 
	public static void main(String ... args  ) throws Exception {
		 //用 FasterXml
		//obj2Json();
		json2Obj();
	}
	public static String obj2Json() throws Exception {
		UserJson user=new UserJson();
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
		String jsonString=JSON.toJSONString(user);
	    System.out.println(jsonString);
	    return jsonString;
	}
	public static void json2Obj() throws Exception {
		String str= obj2Json();
		UserJson user=JSON.parseObject(str,UserJson.class);
		System.out.println(user.getUserName());
	}

}