package jpa_school;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="JPA_COURSE")
public class Course implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String name;
    
    //-------------- 多对多 方式一 ,没有Score实体类,没有其它字段 ,
    //@ManyToMany(mappedBy="courses",fetch=FetchType.LAZY,targetEntity=Student.class,cascade={CascadeType.PERSIST,CascadeType.MERGE})
    //@ForeignKey(name="FK_JPA_score_course")
    @Transient //不与数据库交互
    private Set<Student>students;
    //--------------多对多 方式二  两个一对多,有Sore实体类及映射,多score字段
    @OneToMany(mappedBy="course",cascade={CascadeType.PERSIST,CascadeType.MERGE},targetEntity=Score.class)
    private Set<Score>scores;
    //--------------
    public Set<Score> getScores() {
		return scores;
	}
	public void setScores(Set<Score> scores) {
		this.scores = scores;
	}
    public Set<Student>getStudents() {
        return this.students;
    }
    public void setStudents(Set<Student>students) {
        this.students = students;
    }
	public Course() {
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
		return "Course [id=" + id + ", name=" + name + ", students=" + students + ", scores=" + scores + "]";
	}
 

}
