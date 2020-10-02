package apache_commons;

import java.sql.Timestamp;
import java.util.Date;

public class PersonBean {
 
    private Integer age;
    private String  mN;
    private Date  birthday;
//    private Timestamp  createTime;
//    
//    public Timestamp getCreateTime() {
//		return createTime;
//	}
//	public void setCreateTime(Timestamp createTime) {
//		this.createTime = createTime;
//	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
     * @return the mN
     */
    public String getmN() {
        return mN;
    }

    /**
     * @param mN the mN to set
     */
    public void setmN(String mN) {
        this.mN = mN;
    }


     

    /**
     * @return the age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(Integer age) {
        this.age = age;
    }

}