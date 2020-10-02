package nosql_neo4j_ogm;

import java.io.InputStream;
import java.util.Properties;

import org.neo4j.ogm.config.ClasspathConfigurationSource;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.config.ConfigurationSource;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import nosql_neo4j_ogm.domain.Actor;
import nosql_neo4j_ogm.domain.Movie;
public class MainNeo4jOGM {

	public static void main(String[] args) throws Exception {
		String classpathFile="nosql_neo4j_ogm/neo4j-ogm.properties";//JDK 8 不能以/开头
		Properties properties = new Properties();
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(classpathFile)   ;
        properties.load(is);
        is.close();
		 /*		 
		Configuration configuration = new Configuration.Builder()
			    //.uri("bolt://localhost")
			    //.credentials("neo4j", "myneo4j")
		 //--
			     // .uri("http://neo4j:myneo4j@localhost:7474")
		 //--
			     //.uri("file:///D:/software/neo4j/neo4j-community-3.4.8-windows/neo4j-community-3.4.8/data/databases/graph.db")
	             //ClassNotFoundException: org.neo4j.graphdb.GraphDatabaseService ???
			      
		 .build();
		 */
		//--------
		ConfigurationSource props = new ClasspathConfigurationSource(classpathFile);
		//ConfigurationSource props = new FileConfigurationSource("D:\\NEW_\\eclipse_java_workspace/J_JavaThirdLib/src/nosql_neo4j_ogm/neo4j-ogm.properties");
		Configuration configuration = new Configuration.Builder(props).build();
		SessionFactory sessionFactory = new SessionFactory(configuration, "nosql_neo4j_ogm.domain");//packages
		
		
		/*
		org.neo4j.driver.v1.Driver nativeDriver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "myneo4j") );
		Driver ogmDriver = new BoltDriver(nativeDriver);
		SessionFactory sessionFactory = new SessionFactory(ogmDriver, "nosql_neo4j_ogm.domain");//packages
		
		 */		
		
		 Session session = sessionFactory.openSession();

		 
		 //(:Actor:DomainObject {name:'Tom Cruise'})-[:ACTED_IN]->(:Film {title:'Mission Impossible'})
	 
		Movie movie = new Movie("The Matrix", 1999);

		Actor keanu = new Actor("Keanu Reeves");
		keanu.actsIn(movie);

		Actor carrie = new Actor("Carrie-Ann Moss");
		carrie.actsIn(movie);

		//Persist the movie. This persists the actors as well.
		session.save(movie); 

		//Load a movie
		Movie matrix = session.load(Movie.class, movie.getId()); 
		for(Actor actor : matrix.getActors()) {
		    System.out.println("Actor: " + actor.getName());
		}

		
		
		
	}

	
	
	
}
