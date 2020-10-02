 package data_jpa_mapping_class;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

 
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
	@Query(nativeQuery = true)
	List<SubscriptionSummary> findAllSubscriptionSummaries(); //count(*) 可以对Long类型
 
	@Query(nativeQuery = true)
	List<SubscriptionProjection> findAllSubscriptionProjections(int lessId);//匹配:lessId参数
	
}
