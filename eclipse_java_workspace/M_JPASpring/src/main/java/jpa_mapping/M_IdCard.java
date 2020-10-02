package jpa_mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "M_IDCARD")
public class M_IdCard
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(length = 18, nullable = false)
	private String cardno;

	public M_IdCard()
	{
	}
	public M_IdCard(String cardno)
	{
		this.cardno = cardno;
	}

	
	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	
	public String getCardno()
	{
		return cardno;
	}

	public void setCardno(String cardno)
	{
		this.cardno = cardno;
	}
 
}