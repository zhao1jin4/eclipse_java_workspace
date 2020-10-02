package nosql_neo4j;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

public class MainNeo4jMap {

	public static void main(String[] args) {

		// https://github.com/neo4j-examples

		
		String query = "MATCH (:Movie {title:{title}})<-[:ACTED_IN]-(a:Person) RETURN a.name as actor";

		Map<String, Object> param = new HashMap<>();
		param.put("title", "The Matrix");

		Driver driver = GraphDatabase.driver("bolt://localhost",AuthTokens.basic( "neo4j", "myneo4j" ) );

		try (Session session1 = driver.session()) {

			StatementResult result = session1.run(query, param);
			while (result.hasNext()) {
				System.out.println(result.next().get("actor"));
			}
		}
		
		 driver.close();
	}

}
