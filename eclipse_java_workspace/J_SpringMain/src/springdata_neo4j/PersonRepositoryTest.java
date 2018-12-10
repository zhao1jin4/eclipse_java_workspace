package springdata_neo4j;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springdata_neo4j.repo.MyNeo4jCrudRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "classpath*:/spring_data_neo4j/spring_neo4j.xml" })
public class PersonRepositoryTest
{

	@Autowired
	private MyNeo4jCrudRepository personRepository;

	@Test
	public void testCRUDPerson()
	{
		Person person = new Person();
		person = personRepository.save(person);
		assertNotNull(person);
		assertNotNull(person.getId());

		Long personId = person.getId();
		personRepository.delete(person);
		// person = personRepository.findOne(personId);
		assertNull(person);
	}
}