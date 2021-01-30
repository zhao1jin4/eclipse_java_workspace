package json_google;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class GoogleJSONFormat {
	/**
 <dependency>
  <groupId>com.google.code.gson</groupId>
  <artifactId>gson</artifactId>
  <version>2.8.6</version>
</dependency>
	 */
	public static void main(String[] args) {
	    
	    String jsonString = "{\"_index\":\"book_shop\",\"_type\":\"it_book\",\"_id\":\"1\",\"_score\":1.0," +
	            "\"_source\":{\"name\": \"Java���˼�루��4�棩\",\"author\": \"[��] Bruce Eckel\",\"category\": \"�������\"," +
	            "\"price\": 109.0,\"publisher\": \"��е��ҵ������\",\"date\": \"2007-06-01\",\"tags\": [ \"Java\", \"�������\" ]}}";
		
		String pretty = formatJSONByGoogle(jsonString);
		
		System.out.println(pretty);
	}
	
	/**
	 * ��ʽ�����JSON�ַ���
	 * @return ��ʽ�����JSON�ַ���
	 */
	public static String formatJSONByGoogle(String json) {
		JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(jsonObject);
	}
}

