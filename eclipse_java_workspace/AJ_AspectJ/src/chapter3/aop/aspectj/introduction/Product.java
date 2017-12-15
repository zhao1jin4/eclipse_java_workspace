package chapter3.aop.aspectj.introduction;

public class Product {
  private String productId;
  private String name;
  private String description;
  private int price;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

  public String toString() {
    return "name="+getName() + ",price=" + getPrice();
  }
}
