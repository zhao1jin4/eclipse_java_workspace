package jpa_school;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
//方式一SINGLE_TABLE
//@DiscriminatorValue("G")//相当于<subclass discriminator-value="G"

//方式二 JOINED
@PrimaryKeyJoinColumn(name="student_id",foreignKey =@ForeignKey(name="FK_GOOD_STUDENT") )//相当于<joined-subclass
@Table(name="JPA_GOOD_STUDENT")

public class GoodStudent extends Student {//三好学生
	private Date joinDate;

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

}
