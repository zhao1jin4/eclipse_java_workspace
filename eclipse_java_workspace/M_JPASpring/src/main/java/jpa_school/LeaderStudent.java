package jpa_school;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity

//方式一SINGLE_TABLE
//@DiscriminatorValue("L")

//方式二 JOINED
@PrimaryKeyJoinColumn(name="student_id",foreignKey =@ForeignKey(name="FK_LEAD_STUDENT") )//相当于<joined-subclass
@Table(name="JPA_LEADER_STUDENT")

public class LeaderStudent extends Student {//班干
	private String jobPosition;//职位

	public String getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}
	
}
