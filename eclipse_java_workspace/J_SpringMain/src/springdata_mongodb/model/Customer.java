package springdata_mongodb.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="mo_customer")
@CompoundIndexes({
    @CompoundIndex(name = "inx_com_time", def = "{'createTime': 1, 'sqlTime': -1}")
})
public class Customer {

    @Id
    private String id;

    @Field(value="first_name")
    @Indexed//(unique = true) 
    /*老版本如这里加会在第二次启时报索引已经存在的Warning
    db.mo_customer.createIndex( { "first_name": 1 }, {name: "first_name", unique: true } )
    db.mo_customer.getIndexes()
	db.mo_customer.dropIndex("inx_com_time" )
 	db.mo_customer.dropIndex("first_name")
 	db.mo_customer.dropIndexes() 从MongoDB 4.2 开始 可以删所有非 _id 索引
    */
    private String firstName;
   
    @Transient
    private Long dbUserId;
    
    private String lastName;
    
    private int  age;
    
    private Date createTime;
 
    private Timestamp sqlTime;
    
    private List<String> hobby;
    
    public Long getDbUserId() {
		return dbUserId;
	}
    
	public void setDbUserId(Long dbUserId) {
		this.dbUserId = dbUserId;
	}

	public List<String> getHobby() {
		return hobby;
	}

	public void setHobby(List<String> hobby) {
		this.hobby = hobby;
	}

	public Customer() {}

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

    public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Timestamp getSqlTime() {
		return sqlTime;
	}

	public void setSqlTime(Timestamp sqlTime) {
		this.sqlTime = sqlTime;
	}

	@Override
    public String toString() {
        return String.format(
                "Customer[id=%s, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }

}