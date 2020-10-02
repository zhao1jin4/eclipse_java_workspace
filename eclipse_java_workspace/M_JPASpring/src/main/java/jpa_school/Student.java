package jpa_school;



import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.FieldResult;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

import jpa.one2one.Person;


//@NamedNativeQuery(name="getStudentPhotoByName",query="select photo from jpa_student where name=:stu_name)")//Hibernate 4.1 JPA 还不支持
@NamedQuery(name="getStudentByTeacher",query="from Student s where s.teacher.id=:teacher_id")

@Entity
@Table(name = "JPA_STUDENT",indexes = {@Index(name = "IDX_JPA_Student_name",  columnList="name", unique = true)})

//方式一SINGLE_TABLE
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="type",discriminatorType=DiscriminatorType.CHAR) //对于SINGLE_TABLE
//@DiscriminatorValue("C")//对于SINGLE_TABLE 

//方式二 JOINED
@Inheritance(strategy=InheritanceType.JOINED)
public class Student implements Serializable 
{


/*
生成下面的样子
create table all_table_id_gen
( 
	table_name varchar(255),
	seq_val bigint 
);
insert into  all_table_id_gen (table_name,seq_val)values('jpa_student',101);
第一批次是102,103,第二次就是203，204，不是按顺序来的
*/
	@Id
	@TableGenerator(name="myGen" , table="all_table_id_gen",pkColumnName = "table_name",valueColumnName = "seql_val",pkColumnValue = "jpa_student",initialValue = 101 )
	@GeneratedValue(strategy=GenerationType.TABLE,generator = "myGen")
	//@GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
	 
	private String name;
	    
	 
    @Enumerated(EnumType.STRING)//ORDINAL 是枚举索引值,STRING是枚举名字
    @Column(length=6,nullable=false)//enum应该不能为空的
    private Gender gender=Gender.MALE;//默认值 ,是enum类型的
    
    @Temporal(TemporalType.DATE)//日期格式
    private Date birthday;
    
    @Formula("floor((current_date()-birthday)/365)")//只Hibernate有,如birthday为null,会报错,Null给age,如是Oracle使用nvl(floor((current_date-birthday)/365),0) 
    //floor((sysdate-birthday)/365) 可Oracle和H2,如H2函数要加(),可是sysdate()或者current_date())  ,floor(MONTHS_BETWEEN(sysdate,birthday)/12) 只Oracle 
    private int age;
    
    //@Embedded 可以不加
    private Favorite fav;
    
    @ManyToOne( cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
    		optional=true)//从方optional=true表示一个学生可以没有老师
    @JoinColumn(name="TEACHER_ID",nullable=false,foreignKey =@ForeignKey(name="FK_JPA_STUDENT_TEACHER"))
    private  Teacher teacher;
    
    @OneToOne(mappedBy="stu",fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.MERGE})//有mappedBy是主方
    private Address addr;
    
    //--------------多对多 方式一 ,没有Score实体类,没有其它字段 ,
//    @ManyToMany(fetch=FetchType.LAZY,targetEntity=Course.class,cascade={CascadeType.PERSIST,CascadeType.MERGE})
//    @ForeignKey(name="FK_JPA_score_student")
//    @JoinTable(name="JPA_SCORE",joinColumns=@JoinColumn(name="student_id"),
//    				 	 inverseJoinColumns=@JoinColumn(name="course_id")  )
    @Transient //不与数据库交互
    private Set<Course> courses;
    //--------------多对多 方式二  两个一对多,有Sore实体类及映射,多score字段
    @OneToMany(mappedBy="student",cascade={CascadeType.PERSIST,CascadeType.MERGE},targetEntity=Score.class)
    private Set<Score>scores;
    //--------------
    
    @Lob
    @Basic(fetch=FetchType.LAZY)//大的字段延迟加载
    private byte[] photo;
   
    @Lob
    @Basic(fetch=FetchType.LAZY)
    private String remark;
    
    @Column(length = 1,nullable = true,name="IS_LEAGUE")
    @Convert(converter = YNConverter.class)
    private Boolean isLeague;
    
    public Boolean getIsLeague() {
		return isLeague;
	}
	public void setIsLeague(Boolean isLeague) {
		this.isLeague = isLeague;
	}
	public Favorite getFav() {
		return fav;
	}
	public void setFav(Favorite fav) {
		this.fav = fav;
	}
	public Set<Course> getCourses() {
        return this.courses;
    }
    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
    public Set<Score> getScores() {
		return scores;
	}
	public void setScores(Set<Score> scores) {
		this.scores = scores;
	}
	public Address getAddr() {
		return addr;
	}
	public void setAddr(Address addr) {
		this.addr = addr;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public Student() {
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", gender=" + gender + ", birthday=" + birthday + ", age=" + age
				+ ", fav=" + fav + ", teacher=" + teacher + ", addr=" + addr + ", courses=" + courses + ", scores="
				+ scores + ", photo=" + Arrays.toString(photo) + ", remark=" + remark + "]";
	}

}
