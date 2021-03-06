package jpa.one2many;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "orders")
public class Order
{
	private String orderid;
	private Float amount = 0f;
	private Set<OrderItem> items = new HashSet<OrderItem>();

	@Id
	@Column(length = 12)
	public String getOrderid()
	{
		return orderid;
	}

	public void setOrderid(String orderid)
	{
		this.orderid = orderid;
	}

	@Column(nullable = false)
	public Float getAmount()
	{
		return amount;
	}

	public void setAmount(Float amount)
	{
		this.amount = amount;
	}

	/*
	 * OnToMany 一对多,一方 cascade 级连方式 fetch 抓取策略 mappedBy 由指定属性维护关系
	 */
	@Where(clause = "sellPrice > 0") //java属性,FetchType.EAGER时，同一事务中，如有有更新为符合@Where条件的数据，不会立即应用条件，只是在新事务第一次取数据时应用@Where条件
	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.EAGER, mappedBy = "order")
	public Set<OrderItem> getItems()
	{
		return items;
	}

	public void setItems(Set<OrderItem> items)
	{
		this.items = items;
	}

	public void addOrderItems(OrderItem item)
	{
		item.setOrder(this);
		this.items.add(item);
	}

}