package jpa.many2many;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil
{

	private static EntityManager entityManager;

	public static EntityManager getInstance()
	{
		if (entityManager != null)
		{
			return entityManager;
		} else
		{
			return makeInstance();
		}
	}

	private static synchronized EntityManager makeInstance()
	{
		if (entityManager == null)
		{
			EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("MyJPA");
			return entityManagerFactory.createEntityManager();
		}
		return null;
	}
}
