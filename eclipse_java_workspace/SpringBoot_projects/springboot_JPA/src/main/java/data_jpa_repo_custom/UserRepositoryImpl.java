package data_jpa_repo_custom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
 
class UserRepositoryImpl implements UserRepositoryCustom {
	@PersistenceContext //JPA包可注入
	private EntityManager em;
 
	public List<User> myCustomBatchOperation() {
		CriteriaQuery<User> criteriaQuery = em.getCriteriaBuilder().createQuery(User.class);
		criteriaQuery.select(criteriaQuery.from(User.class));
		return em.createQuery(criteriaQuery).getResultList();
	}
}
