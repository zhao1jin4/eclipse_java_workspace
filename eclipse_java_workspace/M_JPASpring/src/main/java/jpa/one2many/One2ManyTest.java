package jpa.one2many;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.Test;
 

public class One2ManyTest
{

	 
	@Test
	public void save()
	{
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("MyJPA");
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		Order order = new Order();
		order.setAmount(34f);
		order.setOrderid("haha");
		OrderItem item1 = new OrderItem();
		item1.setProductName("football");
		item1.setSellProce(90f);

		OrderItem item2 = new OrderItem();
		item2.setProductName("basketball");
		item2.setSellProce(70f);

		order.addOrderItems(item1);
		order.addOrderItems(item2);
		em.persist(order);
		em.getTransaction().commit();
		factory.close();

	}

}