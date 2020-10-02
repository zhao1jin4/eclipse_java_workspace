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
	     
	    //���book[0]��authorֵ
	    String author = JsonPath.read(json, "$.store.book[0].author"); 
	    //���� net.minidev.json.writer.JsonReaderI
	    //���� net.minidev.asm.FieldFilter
	    System.out.println(author);
	     
	    //���ȫ��author��ֵ��ʹ��Iterator����
	    List<String> authors = JsonPath.read(json, "$.store.book[*].author");
	    System.out.println(authors);
	    
	    //���book[*]��category == 'reference'��book
	    List<Object> books = JsonPath.read(json, "$.store.book[?(@.category == 'reference')]");               
	    System.out.println(books); 
	    
	    //���book[*]��price>10��book
	     books = JsonPath.read(json, "$.store.book[?(@.price>10)]");
	    System.out.println(books); 
	    
	    //���book[*]�к���isbnԪ�ص�book
	     books = JsonPath.read(json, "$.store.book[?(@.isbn)]");
	     System.out.println(books); 
	     
	    //�����json������price��ֵ
	    List<Double> prices = JsonPath.read(json, "$..price");
	    System.out.println(prices);  
	    
	    //������ǰ�༭һ��·���������ʹ����
	    JsonPath path = JsonPath.compile("$.store.book[*]"); 
	    books = path.read(json); 
	    System.out.println(books); 
	}

}
