package jpa_mapping;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import jpa.one2one.IdCard;
import jpa.one2one.Person;

public class MainMapping {
public static void main(String[] args) {
	
	 
	EntityManagerFactory emf =  Persistence.createEntityManagerFactory("MyJPA");
	EntityManager entityManager = emf.createEntityManager();
	List<Object[]> tuples = entityManager.createNamedQuery(
			"find_person_card" )
		.setParameter("name", "h%")
		.getResultList();

		for(Object[] tuple : tuples) {
			M_Person person = (M_Person) tuple[0];
			M_IdCard idcard = (M_IdCard) tuple[1];
			System.out.println(person.getName()+"--"+idcard.getCardno());
		}
}
}
