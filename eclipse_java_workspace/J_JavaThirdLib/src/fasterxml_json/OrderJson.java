package fasterxml_json;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderJson {
	
	 @JsonProperty("orderId")
	private String orderId;
	 
	 @JsonProperty("price")
	private BigDecimal price;
	 
	 @JsonProperty("amount")
	private long amount;
	 
	 @JsonProperty("address")
	private String address;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	 
}
