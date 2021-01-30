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
	            "\"_source\":{\"name\": \"Java编程思想（第4版）\",\"author\": \"[美] Bruce Eckel\",\"category\": \"编程语言\"," +
	            "\"price\": 109.0,\"publisher\": \"机械工业出版社\",\"date\": \"2007-06-01\",\"tags\": [ \"Java\", \"编程语言\" ]}}";
		
		String pretty = formatJSONByGoogle(jsonString);
		
		System.out.println(pretty);
	}
	
	/**
	 * 格式化输出JSON字符串
	 * @return 格式化后的JSON字符串
	 */
	public static String formatJSONByGoogle(String json) {
		JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(jsonObject);
	}
}

