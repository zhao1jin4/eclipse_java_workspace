package elastic_search;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonStructure;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClient.FailureListener;
import org.elasticsearch.client.RestClientBuilder;
public class ElasticSearchRest {
	public static void main(String[] args) throws Exception {
		RestClientBuilder builder =RestClient.builder(
				new HttpHost("rhel7",9200,"http")
//				,new HttpHost("rhel7kube",9200,"http")
			);
	builder.setFailureListener(new FailureListener() {
		@Override
		public void onFailure(Node node) {
			System.out.println("出错"+node);
		}
	});
	RestClient restClient=builder.build();
	
	//status(restClient); //OK
	save(restClient);//不行？
	//search(restClient);// OK

	restClient.close();

	}
	public static void status(RestClient restClient) throws Exception
	{
		Request request=new Request("GET","/_cluster/state");
		request.addParameter("pretty", "true");
		Response response=restClient.performRequest(request);
		System.out.println("response status="+response.getStatusLine());
		System.out.println(EntityUtils.toString(response.getEntity()));
		
	}
	public static void save(RestClient restClient) throws Exception
	{
		//可能先建立索引才能增加文档数据
		/*
		 
curl -X PUT -H 'Content-Type: application/json' -i 'http://localhost:9200/books' -d '
{
   "settings":
   {
    "index":
    {
      "number_of_shards":2,
      "number_of_replicas":2    
    }   
   },
   "mappings":
   { 
       "properties":
      {
       "id":
        {
          "type":"long"        
        },
        "title":
        {
          "type":"text"        
        },
        "price":
        {
          "type":"double"        
        }
      }   
   }
}'
		 */
		Request request=new Request("PUT","/books");
		Map<String,Object> data=new HashMap<>();
		data.put("id", 1009);
		data.put("title", "elastic 书");
		data.put("price", 18.8);
		String jsonStr = new com.rabbitmq.tools.json.JSONWriter().write(data);//Object(可Map)->JSON
		request.setJsonEntity(jsonStr); //设置JSON
		
		Response response=restClient.performRequest(request);
		System.out.println("response status="+response.getStatusLine());
		System.out.println(EntityUtils.toString(response.getEntity()));
	}
	public static void search(RestClient restClient) throws Exception
	{
		Request request=new Request("POST","/books/_search");
		request.addParameter("pretty", "true");
		//javax.json
		JsonBuilderFactory bf = Json.createBuilderFactory(null);
		//JsonArray array=bf.createArrayBuilder()
		JsonStructure struct =bf.createObjectBuilder()
            .add("query", bf.createObjectBuilder()
                    .add("match", bf.createObjectBuilder()
                            .add("title", "java")
                     )
                 ).build();
			
		request.setJsonEntity(struct.toString());  
		Response response=restClient.performRequest(request);
		System.out.println("response status="+response.getStatusLine());
		String jsonStr=EntityUtils.toString(response.getEntity());
		System.out.println(jsonStr);
		/*
		//javax.json
		JsonParser parser = Json.createParser(new StringReader(jsonStr));
		while(parser.hasNext()) 
		{
			Event e = parser.next();
			if (e == Event.KEY_NAME)
			{
				if (parser.getString().equals("total")) 
				{
					parser.next();
					System.out.println(parser.getString());
				}  
			}
		} 
		*/
	}
}
