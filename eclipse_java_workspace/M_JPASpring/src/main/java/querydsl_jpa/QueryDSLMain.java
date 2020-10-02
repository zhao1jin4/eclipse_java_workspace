package querydsl_jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jpa.one2many.OrderItem;
import jpa.single.UserBean;
import querydsl_jpa.gen.QOrder;
import querydsl_jpa.gen.QOrderItem;
import querydsl_jpa.gen.QUserBean;

public class QueryDSLMain {
	public static void main(String[] args)
	{
		//mvn clean install 将 在指定的目录下 生成 所有@Entity对应的 代码
		 EntityManagerFactory emf   = Persistence.createEntityManagerFactory("MyJPA");
		 EntityManager entityManager = emf.createEntityManager();
		
		 //singleQuery(entityManager);
		 //multiQuery(entityManager);
		//update(entityManager);
		 queryUseDSLupdateUseSQL(entityManager);
	}
	public static void singleQuery(EntityManager entityManager  )
	{
		 //这个示例是对应于包下的jpa.single 的UserBean生成的QUserBean
		QUserBean userBean = QUserBean.userBean;
		JPAQuery<?> query = new JPAQuery<Void>(entityManager);
		UserBean user = query.select(userBean)
		  .from(userBean)
				.where(userBean.name.startsWith("叶"))
		  .fetchOne();
		System.out.println(user);
		
		
		//JPAQueryFactory
		JPAQueryFactory factory=new JPAQueryFactory(entityManager);
		UserBean user2 =factory.select(QUserBean.userBean).from(userBean)//from可以放多表
		.where(userBean.id.eq(
				//子查询
				JPAExpressions.select(QUserBean.userBean.id).from(QUserBean.userBean).where(userBean.name.startsWith("叶"))
			))
		  .fetchOne();
		System.out.println(user2);
		
	}
	public static void multiQuery(EntityManager entityManager  )
	{
		JPAQueryFactory factory=new JPAQueryFactory(entityManager);
		List<OrderItem>  allItem=factory.select(QOrderItem.orderItem).from(QOrder.order,QOrderItem.orderItem)//from可以放多表
		.where(
				QOrder.order.orderid.eq(QOrderItem.orderItem.order.orderid)
				.and(QOrder.order.amount.goe(20))
				).fetch();
		System.out.println(allItem);
	}
	public static void update(EntityManager entityManager  )
	{
		 JPAQueryFactory factory=new JPAQueryFactory(entityManager);
		 entityManager.getTransaction().begin();
		 //必须在事务中
		 factory.update(QUserBean.userBean).where(QUserBean.userBean.name.startsWith("叶"))
		 .set(QUserBean.userBean.password, "123").execute();
		 entityManager.getTransaction().commit();
	}
	public   static void queryUseDSLupdateUseSQL( EntityManager entityManager )
	{ 
		entityManager.getTransaction().begin();
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
		entityManager.getTransaction().commit();
	}
}
