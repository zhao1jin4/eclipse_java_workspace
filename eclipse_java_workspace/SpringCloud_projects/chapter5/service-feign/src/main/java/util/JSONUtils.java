package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JSONUtils {
    /**
     * 不安全
     * @param strJson
     * @return
     */
	public static String formatJSONByAli(String strJson) {
		JSONObject object = JSONObject.parseObject(strJson);
		String pretty = JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteDateUseDateFormat);
		return pretty;
	}
	public static String formatJSONByGoogle(String strJson) {
		JsonObject jsonObject = JsonParser.parseString(strJson).getAsJsonObject();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(jsonObject);
	}
	
	public static String formatJSONByJackson(String strJson) throws JsonProcessingException {
		ObjectMapper mapper = new  ObjectMapper();
		JsonNode node=mapper.readTree( strJson);   
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
	}
}