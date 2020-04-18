package springdata_elastic;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//ElaticSearch 8将移除 Transport Client，使用新的 Java High Level REST Client
@Configuration
static class Config {

  @Bean
  RestHighLevelClient client() {

    ClientConfiguration clientConfiguration = ClientConfiguration.builder() 
      .connectedTo("localhost:9200", "localhost:9201")
      .build();

    return RestClients.create(clientConfiguration).rest();                  
  }
}

// ...

  @Autowired
  RestHighLevelClient highLevelClient;

  RestClient lowLevelClient = highLevelClient.lowLevelClient();             

// ...

IndexRequest request = new IndexRequest("spring-data", "elasticsearch", randomID())
  .source(singletonMap("feature", "high-level-rest-client"))
  .setRefreshPolicy(IMMEDIATE);

IndexResponse response = highLevelClient.index(request);
public class ElasticSearch8Main {

	public static void main(String[] args) {
	 

	}

}
