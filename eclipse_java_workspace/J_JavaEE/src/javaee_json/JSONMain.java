package javaee_json;



import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonBuilderFactory;
import javax.json.JsonStructure;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

public class JSONMain {
	public static void generateJSON() 
	{
		JsonBuilderFactory bf = Json.createBuilderFactory(null);
		JsonStructure struct =bf.createObjectBuilder()
		            .add("firstName", "John")
		            .add("age", 25)
		            .add("address", bf.createObjectBuilder()
		                .add("streetAddress", "21 2nd Street")
		                .add("city", "New York")
					).build();
					
					
		JsonArray array=bf.createArrayBuilder()
			.add(bf.createObjectBuilder()
				.add("type", "home")
				.add("number", "212 555-1234"))
			.add(bf.createObjectBuilder()
				.add("type", "fax")
				.add("number", "646 555-4567"))
			.build();
		System.out.println(struct.toString());
		System.out.println(array.toString());
	}
	
	public static void parseJSON() throws Exception 
	{
		//String inStr="{\"firstName\":\"John\",\"age\":25,\"address\":{\"streetAddress\":\"21 2nd Street\",\"city\":\"New York\"}}";
		String inStr="[{\"type\":\"home\",\"number\":\"212 555-1234\"},{\"type\":\"fax\",\"number\":\"646 555-4567\"}]";
		JsonParser parser = Json.createParser(new StringReader(inStr));
		while(parser.hasNext()) 
		{
			Event e = parser.next();
			if (e == Event.KEY_NAME)
			{
				if (parser.getString().equals("number")) 
				{
					parser.next();
					System.out.println(parser.getString());
				}  
			}
		}
	}
	public static void main(String[] args) throws Exception 
	{
		//generateJSON();
		parseJSON();

	}

}
