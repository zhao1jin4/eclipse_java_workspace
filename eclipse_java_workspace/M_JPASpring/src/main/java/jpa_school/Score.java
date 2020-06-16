package jpa_school;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.ForeignKey;

//--------------多对多 方式二  两个一对多,有Sore实体类及映射,多score字段
@Entity
@Table(name="JPA_SCORE")
public class Score implements Serializable 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne(optional=false,cascade={CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(name="student_id",nullable=false,foreignKey =@ForeignKey(name="FK_JPA_score_student") ) 
	private Student student;
	
	@ManyToOne(optional=false,cascade={CascadeType.PERSIST,CascadeType.MERGE})
	@JoinColumn(name="corse_id",nullable=false,foreignKey =@ForeignKey(name="FK_JPA_score_course") )
	private Course course;
	
	private float score;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

}
