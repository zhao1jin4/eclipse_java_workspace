package elastic;

import org.elasticsearch.client.transport.TransportClient;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyBean {

	private final ElasticsearchTemplate template;
	private TransportClient transportClient;
			
	public MyBean(ElasticsearchTemplate template) {
		this.template = template;
	}

	// ...

}