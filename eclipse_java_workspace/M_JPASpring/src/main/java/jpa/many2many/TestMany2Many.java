package jpa.many2many;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.Test;


public class TestMany2Many
{
	@Test
	public void insert()
	{
		EntityManager entityManager = JPAUtil.getInstance();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		try
		{
			entityTransaction.begin();

			User user = new User();
			user.setName("张三");

			Role role = new Role();
			role.setName("管理员");

			Set<User> users = new HashSet<User>();
			users.add(user);

			role.setUsers(users);

			entityManager.persist(role);

			entityTransaction.commit();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void select()
	{
		EntityManager entityManager = JPAUtil.getInstance();
		Role role = entityManager.find(Role.class, 1);
		System.out.println(role.getName());
		Set<User> set = role.getUsers();
		for (Iterator<User> iterator = set.iterator(); iterator.hasNext();)
		{
			User user = (User) iterator.next();
			System.out.println(user.getName());
		}
	}
}
