package springdata_neo4j;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import springdata_neo4j.repo.MyNeo4jCrudRepository;

public class MainSpringDataNeo4j {

	public static void main(String[] args) {
//Î´³É¹¦????
		 ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/springdata_neo4j/spring_neo4j.xml");
//		 ApplicationContext context = new AnnotationConfigApplicationContext(Neo4jConfig.class);
		 
		MyNeo4jCrudRepository repository =  context.getBean(MyNeo4jCrudRepository.class);
				
		 Page<Person> persons = repository.findAll(PageRequest.of(1, 20));

	}

}
