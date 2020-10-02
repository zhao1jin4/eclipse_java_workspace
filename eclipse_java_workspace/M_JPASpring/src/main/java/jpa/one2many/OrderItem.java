package jpa.one2many;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Where;

@Entity
public class OrderItem
{
	private Integer id;
	private String productName;
	
	@Column(name="SELL_PRICE",nullable = false)
	private Float sellPrice = 0f;
	
	private Order order;

	@Id
	@GeneratedValue
	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	@Column(length = 40, nullable = false)
	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public Float getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Float sellPrice) {
		this.sellPrice = sellPrice;
	}

	/*
	 * OnToMany 一对多,多方 cascade 级连方式 optional 是否可先(是否为必填字段) JoinColumn 关联表字段
	 */
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "order_id")
	public Order getOrder()
	{
		return order;
	}

	public void setOrder(Order order)
	{
		this.order = order;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", productName=" + productName + ", sellPrice=" + sellPrice + "]";
	}

}