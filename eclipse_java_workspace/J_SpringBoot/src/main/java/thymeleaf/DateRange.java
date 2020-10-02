package thymeleaf;

import java.io.Serializable;
import java.util.Date;

 
public class DateRange implements Serializable {
	//是实现Serializable

    private Date beginTime;
    private Date endTime;

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

	@Override
	public String toString() {
		return "DateRange [beginTime=" + beginTime + ", endTime=" + endTime + "]";
	}
    
}
