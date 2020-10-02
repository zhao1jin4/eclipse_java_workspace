package springdata_jpa.repo;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jpa.single.UserBean;
import querydsl_jpa.gen.QUserBean;


@NoRepositoryBean
//@javax.transaction.Transactional
@org.springframework.transaction.annotation.Transactional
//用JPA无论是spring的@Transactional还是javax的@Transactinal都是有效的
//SimpleJpaRepository 源码 带 @Transactional(readOnly=true) ,是可以被子类覆盖的
public class NotRepositoryImpl extends ReadOnlyRepositoryImpl  // implements NotRepository
{
	@Autowired
	private EntityManager entityManager;
	
	public   void queryUseDSLupdateUseSQL( )
	{ 
		QUserBean userBean = QUserBean.userBean;
		JPAQuery<?> query = new JPAQuery<Void>(entityManager);
		UserBean user = query.select(userBean)
		  .from(userBean).where(userBean.name.startsWith("叶"))
		  .fetchOne();
		System.out.println(user.getPassword());//abc123
		
		entityManager.createQuery("update UserBean u set u.password='123'").executeUpdate();//这SQL的修改不会更新缓存
		entityManager.flush();
		query = new JPAQuery<Void>(entityManager);
		  user = query.select(userBean)
		  .from(userBean).where(userBean.name.startsWith("叶"))
		  .fetchOne();
		System.out.println(user.getPassword());//abc123这还是老值
	}
	 
}
