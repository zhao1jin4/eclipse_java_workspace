package jpa.one2one;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.ForeignKey;

@Entity
@Table(name = "T_IDCARD")
public class IdCard
{
	private Integer id;
	private String cardno;
	private Person person;

	public IdCard()
	{
	}

	public IdCard(String cardno)
	{
		this.cardno = cardno;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	@Column(length = 18, nullable = false)
	public String getCardno()
	{
		return cardno;
	}

	public void setCardno(String cardno)
	{
		this.cardno = cardno;
	}

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH },
			  optional = false, fetch = FetchType.EAGER)//从方来加 optional = false
	@JoinColumn(name = "PERSON_ID",nullable=false,foreignKey =@ForeignKey(name="FK_IDCARD_PERSON"))
	//产生的索引名是随机的???(唯一约束)
	public Person getPerson()
	{
		return person;
	}

	public void setPerson(Person person)
	{
		this.person = person;
	}

}