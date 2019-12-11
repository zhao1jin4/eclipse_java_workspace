package elastic_search;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonStructure;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClient.FailureListener;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

public class ElasticSearchRestHigh 
{
	/*
	 elasticsearch-rest-high-level-client-7.4.2.jar
		elasticsearch-7.4.2.jar
		elasticsearch-x-content-7.4.2.jar
		lang-mustache-client-7.4.2.jar  //elasticsearch plugin
		rank-eval-client-7.4.2.jar		//elasticsearch plugin
		elasticsearch-core-7.4.2.jar
		hppc-0.8.1.jar
		queries/lucene-queries-8.3.0.jar
	
	 */
	public static void main(String[] args) throws Exception 
	{
		RestClientBuilder builder =RestClient.builder(
				new HttpHost("rhel7",9200,"http")
//				,new HttpHost("rhel7kube",9200,"http")
			); 
	RestHighLevelClient restClient= new RestHighLevelClient(builder);
//	save(restClient);  
//	saveAsync(restClient);  
//	search(restClient);
//	getExist(restClient);
//	update(restClient); 
//	deleteDoc(restClient);
	
//	deleteIndex(restClient);//不行？？ curl -X DELETE 'http://localhost:9200/books' 
	
	restClient.close();
	} 
	public static void save(RestHighLevelClient restClient) throws Exception
	{
		Map<String,Object> data=new HashMap<>();
		data.put("title", "java 书");
		data.put("price", 38.8);
		//会自建立索引
		IndexRequest request=new IndexRequest("books").id("1").source(data);
		 
		IndexResponse response=restClient.index(request,RequestOptions.DEFAULT);
		System.out.println("_id="+response.getId());
		System.out.println("index="+response.getIndex());
		System.out.println("shardInfo="+response.getShardInfo());
	}
	public static void saveAsync(RestHighLevelClient restClient) throws Exception
	{
		Map<String,Object> data=new HashMap<>();
		data.put("title", "java Async书");
		data.put("price", 48.8);
		IndexRequest request=new IndexRequest("books").id("2").source(data);
		restClient.indexAsync(request,RequestOptions.DEFAULT,new ActionListener<IndexResponse>() {
			@Override
			public void onResponse(IndexResponse response) {
				System.out.println("_id="+response.getId());
				System.out.println("index="+response.getIndex());
				System.out.println("shardInfo="+response.getShardInfo());
			}
			@Override
			public void onFailure(Exception exception) {
				System.err.println("error="+exception);
			}
		});
	}
	public static void search(RestHighLevelClient restClient) throws Exception
	{
		SearchRequest request=new SearchRequest("books");
		SearchSourceBuilder builder=new SearchSourceBuilder();
		builder.query(QueryBuilders.matchQuery("title", "java"));
		builder.from(1);
		builder.size(1);
		builder.timeout(new TimeValue(1,TimeUnit.SECONDS));
		request.source(builder);
		SearchResponse response=restClient.search(request, RequestOptions.DEFAULT);
		System.out.println("hit="+response.getHits().getTotalHits());
		SearchHits hits=response.getHits();
		for(SearchHit hit: hits)
		{
			System.out.println("json ="+hit.getSourceAsString());
		} 
	}
	public static void getExist(RestHighLevelClient restClient) throws Exception
	{
		GetRequest request=new GetRequest("books","1");
		request.fetchSourceContext(new FetchSourceContext(false));
		boolean isExists=restClient.exists(request,  RequestOptions.DEFAULT);
		System.out.println("isExists ="+isExists);
	}
	public static void update(RestHighLevelClient restClient) throws Exception
	{
		UpdateRequest request=new UpdateRequest("books","1");
		Map<String,Object> data=new HashMap<>();
		data.put("title", "java 书update");
//		data.put("price", 38.8);
		request.doc(data);
		UpdateResponse response=restClient.update(request,  RequestOptions.DEFAULT);
		System.out.println("update version="+response.getVersion());
	}
	public static void deleteDoc(RestHighLevelClient restClient) throws Exception
	{
		DeleteRequest request=new DeleteRequest("books","2");
		restClient.delete( request,  RequestOptions.DEFAULT);
		System.out.println("delete _id=2");
	}
	
	
   //不行？？？
	public static void deleteIndex(RestHighLevelClient restClient) throws Exception
	{
		DeleteRequest request=new DeleteRequest("books");
		restClient.delete( request,  RequestOptions.DEFAULT);
		System.out.println("delete books");
	}
}
