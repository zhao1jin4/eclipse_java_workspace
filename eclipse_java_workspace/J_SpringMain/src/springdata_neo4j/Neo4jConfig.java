package springdata_neo4j;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories
@EnableTransactionManagement
public class Neo4jConfig
{
	@Bean
	public org.neo4j.ogm.config.Configuration getConfiguration()
	{

		org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration.Builder()
				.uri("bolt://localhost").credentials("neo4j", "myneo4j").build();
		return configuration;
	}

	@Bean
	public SessionFactory getSessionFactory()
	{
		return new SessionFactory(getConfiguration(), "samples.neo4j.entities");
	}
}





