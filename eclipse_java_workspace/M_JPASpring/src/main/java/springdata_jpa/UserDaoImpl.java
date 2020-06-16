package springdata_jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
	@PersistenceContext
	private EntityManager em;

	//@Transactional
	public Long save(AccountInfo accountInfo) 
	{
		em.persist(accountInfo);
		 Long accId=accountInfo.getAccountId();
		 return accId;
	}

}