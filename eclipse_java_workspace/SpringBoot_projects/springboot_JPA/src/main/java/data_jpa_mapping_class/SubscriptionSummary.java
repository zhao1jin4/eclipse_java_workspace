 package data_jpa_mapping_class;

 
public class SubscriptionSummary {
	String product;
	Long usageCount;
	
	public SubscriptionSummary(String productCon, Long usageCon) {
		this.product = productCon;
		this.usageCount = usageCon;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public Long getUsageCount() {
		return usageCount;
	}
	public void setUsageCount(Long usageCount) {
		this.usageCount = usageCount;
	}
	
}
