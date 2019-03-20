package neo4j;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.*;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.repository.query.Param;


@EnableNeo4jRepositories
public interface Neo4jMovieRepository extends Neo4jRepository<Movie, Long> {
	
	/**
	 * findOne 必须有一个结果，如无报错 
	 */
	Optional<Movie> findOneByTitle(@Param("one_title")String title);
	
	@Query("MATCH (e: lbl_Movie) WHERE e.one_title =~ ('.*'+{name}+'.*')  RETURN e")
    Collection<Movie> findByNameContaining(@Param("name") String name);
 
}