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
		
//		em.getTransaction().begin();
//		Order order = new Order();
//		order.setAmount(34f);
//		order.setOrderid("haha");
//		OrderItem item1 = new OrderItem();
//		item1.setProductName("football");
//		item1.setSellPrice(90f);
//
//		OrderItem item2 = new OrderItem();
//		item2.setProductName("basketball");
//		item2.setSellPrice(-1f);
//
//		order.addOrderItems(item1);
//		order.addOrderItems(item2);
//		em.persist(order);
//		em.getTransaction().commit();
//		
		em.getTransaction().begin();
		Order dbSessionOrder=em.find(Order.class, "haha");
		dbSessionOrder.getItems().stream().forEach(i->i.setSellPrice(-2f));
		
		//order的orderItem ,FetchType.EAGER时，同一事务中，如有有更新为符合@Where条件的数据，不会立即应用条件，只是在新事务第一次取数据时应用@Where条件
		Order changedOrder=em.find(Order.class, "haha");
		System.out.println(changedOrder.getItems());
		
		em.getTransaction().commit();
		factory.close();

	}

}