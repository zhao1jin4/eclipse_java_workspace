package json_fasterxml;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MainJson {
	public static void main(String ... args  ) throws Exception {
		obj2Json();
//		json2Obj();
	}
	public static String obj2Json() throws Exception {
		UserJson user=new UserJson();
		user.setId(new ObjectId());
		user.setJoinDate(new Date());
		user.setUserName("����");
		user.setPassword("123");//@JsonIgnore
//		List<String> favorite=new ArrayList<>();
//		favorite.add("����");
//		favorite.add("����");
//		user.setFavorite(favorite);

		OrderJson order =new OrderJson ();
		order.setAddress("�Ϻ�");
		order.setAmount(3);
		order.setPrice(BigDecimal.valueOf(3.4));
		user.setOrder(order);
		
		ObjectMapper mapper = new ObjectMapper(); 

		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);//ȫ�ַ�Χ�����������ΪNULL,����josonû�и��ֶ� 
		//mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	    String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
	    System.out.println(jsonString);
	    return jsonString;
	}
	public static void json2Obj() throws Exception {
		ObjectMapper mapper = new  ObjectMapper();
		//mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);//�����л�����δ֪���Բ����쳣
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);//����ʹ��δ�����ŵ��ֶ���
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true); //����ʹ�õ�����
		String str= obj2Json();
		UserJson user=mapper.readValue(str, UserJson.class);
		System.out.println(user.getUserName());
		System.out.println(user.getJoinDate());
	}

}
