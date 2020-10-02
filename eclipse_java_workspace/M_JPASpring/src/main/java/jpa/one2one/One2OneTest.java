package jpa.one2one;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

 

public class One2OneTest
{

	static EntityManagerFactory factory=null;
	static EntityManager em =null;
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception
	{
		factory = Persistence.createEntityManagerFactory("MyJPA");
		em = factory.createEntityManager();
		em.getTransaction().begin();
	}

	@Test
	public void save()
	{
		Person person = new Person("haha");
		IdCard idcard = new IdCard("93938402");
		idcard.setPerson(person);
		person.setIdCard(idcard);
		em.persist(person);
	}
 
	@AfterAll
	public static void afterClass() throws Exception
	{
		em.getTransaction().commit();
		factory.close();
	}
}