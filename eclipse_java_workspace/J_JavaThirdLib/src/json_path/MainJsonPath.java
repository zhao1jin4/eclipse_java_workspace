package json_path;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import com.jayway.jsonpath.JsonPath;

import net.sf.json.JSONObject;

public class MainJsonPath {

	public static void main(String[] args) throws Exception {
		BufferedReader reader =new BufferedReader(new InputStreamReader(MainJsonPath.class.getResourceAsStream("/json_path/my.json")));
		String line=null;
		StringBuilder builder=new StringBuilder();
		while((line=reader.readLine())!=null)
			builder.append(line);
		
		jsonPathTest(builder.toString());
	}
	
	private static void jsonPathTest(String strJson) {
	    //JSONObject json = (JSONObject)JSONObject.toBean(JSONObject.fromObject(strJson) ,JSONObject.class);
	     String json=strJson;
	     
	    //输出book[0]的author值
	    String author = JsonPath.read(json, "$.store.book[0].author"); 
	    //依赖 net.minidev.json.writer.JsonReaderI
	    //依赖 net.minidev.asm.FieldFilter
	    System.out.println(author);
	     
	    //输出全部author的值，使用Iterator迭代
	    List<String> authors = JsonPath.read(json, "$.store.book[*].author");
	    System.out.println(authors);
	    
	    //输出book[*]中category == 'reference'的book
	    List<Object> books = JsonPath.read(json, "$.store.book[?(@.category == 'reference')]");               
	    System.out.println(books); 
	    
	    //输出book[*]中price>10的book
	     books = JsonPath.read(json, "$.store.book[?(@.price>10)]");
	    System.out.println(books); 
	    
	    //输出book[*]中含有isbn元素的book
	     books = JsonPath.read(json, "$.store.book[?(@.isbn)]");
	     System.out.println(books); 
	     
	    //输出该json中所有price的值
	    List<Double> prices = JsonPath.read(json, "$..price");
	    System.out.println(prices);  
	    
	    //可以提前编辑一个路径，并多次使用它
	    JsonPath path = JsonPath.compile("$.store.book[*]"); 
	    books = path.read(json); 
	    System.out.println(books); 
	}

}
