package jpa.one2many;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrderItem
{
	private Integer id;
	private String productName;
	private Float sellProce = 0f;
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

	@Column(nullable = false)
	public Float getSellProce()
	{
		return sellProce;
	}

	public void setSellProce(Float sellProce)
	{
		this.sellProce = sellProce;
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

}