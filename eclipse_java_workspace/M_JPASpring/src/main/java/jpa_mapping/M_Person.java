package jpa_mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

@NamedNativeQueries({
	@NamedNativeQuery(name = "find_person_card", 
			query = "select p.id as pid ,p.full_name,i.id as carid ,i.cardno  from t_idcard i,t_person p  where i.person_id=p.id and p.full_name like :name", 
			resultSetMapping = "person_with_card")
})
@SqlResultSetMapping(name = "person_with_card", entities = 
		{
				@EntityResult(entityClass = M_Person.class, 
				fields = 
				{
				  @FieldResult(name = "id", column = "pid"),
				  @FieldResult(name = "name", column = "full_name"), 
				}),
				@EntityResult(entityClass = M_IdCard.class, fields =
				{ @FieldResult(name = "id", column = "carid"),
				  @FieldResult(name = "cardno", column = "cardno"), 
				}) 
		})//如有一对一关系，测试下来不行的

@Entity
@Table(name = "M_PERSON")
public class M_Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private Integer id;
	
	@Column(length = 10, nullable = false, name = "FULL_NAME")
	private String name;

	public M_Person() {
	}

	public M_Person(String name) {
		this.name = name;
	}

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
 
}