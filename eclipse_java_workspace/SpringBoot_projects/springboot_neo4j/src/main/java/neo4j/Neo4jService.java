package neo4j;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Neo4jService
{

	@Autowired
	private org.neo4j.ogm.session.Session session;

	 public void helloNeo4j()
	 {
		 System.out.println("in helloNeo4j  "  );
		Movie movie = new Movie("The Matrix", 1999);
		Actor keanu = new Actor("Keanu Reeves");
		keanu.actsIn(movie);
		
		session.save(movie); 
		Movie matrix = session.load(Movie.class, movie.getId()); 
		for(Actor actor : matrix.getActors()) {
			System.out.println("Actor: " + actor.getName());
		}

	 }
}