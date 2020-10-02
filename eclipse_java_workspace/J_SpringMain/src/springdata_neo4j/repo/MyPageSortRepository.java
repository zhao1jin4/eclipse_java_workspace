package springdata_neo4j.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import springdata_neo4j.Person;

public interface MyPageSortRepository extends PagingAndSortingRepository <Person, Long>
{
	
	 
}
