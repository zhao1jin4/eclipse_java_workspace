package dozer_beancopy;

import java.sql.Date;

public class DestinationClassName {
	private Date birthday;
	private float score;
	private boolean gender;
	private NestPair pair;
	
	public NestPair getPair() {
		return pair;
	}
	public void setPair(NestPair pair) {
		this.pair = pair;
	}
	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
}
