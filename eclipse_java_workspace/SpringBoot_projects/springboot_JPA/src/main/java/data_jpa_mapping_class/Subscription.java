package data_jpa_mapping_class;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

@NamedNativeQueries({
		@NamedNativeQuery(name = "Subscription.findAllSubscriptionSummaries", //模板类名.方法名
				query = "select product_name as productName, count(user_id) as subscriptions from subscription group by product_name order by productName",  
				resultSetMapping = "subscriptionSummary"),

		@NamedNativeQuery(name = "Subscription.findAllSubscriptionProjections", 
				query = "select product_name as product, count(user_id) as usageCount from subscription where user_id < :lessId "//:lessId传参数 
						+ " group by product_name order by product") })
@SqlResultSetMapping( 
		name = "subscriptionSummary", 
		classes = @ConstructorResult(targetClass = SubscriptionSummary.class, 
				columns = { 
						@ColumnResult(name = "productName", type = String.class),
						@ColumnResult(name = "subscriptions", type = long.class) 
				}))
@Entity 
public class Subscription {

	private   @Id @GeneratedValue Long id = null;
	private String productName;
	private long userId;
	
	public Subscription(String productName, long userId) {
		super();
		this.productName = productName;
		this.userId = userId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
}
