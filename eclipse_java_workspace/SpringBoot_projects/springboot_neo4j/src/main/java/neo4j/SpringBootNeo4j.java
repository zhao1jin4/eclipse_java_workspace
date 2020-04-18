package neo4j;

import java.util.Collection;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class SpringBootNeo4j 
{

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringBootNeo4j.class, args);
		Neo4jService myBean = context.getBean(Neo4jService.class);
		//myBean.helloNeo4j();//save 报错
		 
		
		Neo4jMovieRepository repostiory = context.getBean(Neo4jMovieRepository.class);
		Collection<Movie> coll=repostiory.findByNameContaining("Matrix");//OK
		System.out.println(coll);
		
		//Optional<Movie> opt=repostiory.findOneByTitle("The Matrix");
		//System.out.print(opt.get());
		
//		org.apache.log.Logger l;
	}

}
