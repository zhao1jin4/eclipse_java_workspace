package springdata_neo4j.repo;

import org.springframework.data.repository.Repository;
import org.springframework.data.repository.RepositoryDefinition;

import springdata_neo4j.Person;


 
public interface MyRepository extends  Repository <Person, Long>
{
	
	 
}
