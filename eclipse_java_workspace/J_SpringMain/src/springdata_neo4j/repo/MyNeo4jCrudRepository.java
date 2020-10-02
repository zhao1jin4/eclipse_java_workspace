package springdata_neo4j.repo;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import springdata_neo4j.Person;

public interface MyNeo4jCrudRepository  extends CrudRepository<Person, Long> {
	
	  Iterable<Person> findAll(Sort sort);

	  Page<Person> findAll(Pageable pageable);
	  
	  long countByLastname(String lastname);
	  
	  long deleteByLastname(String lastname);

	  List<Person> removeByLastname(String lastname);
}

