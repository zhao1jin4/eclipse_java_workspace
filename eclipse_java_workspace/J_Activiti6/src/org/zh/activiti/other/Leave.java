package org.zh.activiti.other;

import java.io.Serializable;
import java.util.Date;

public class Leave implements Serializable
{
	private static final long serialVersionUID = 1L;
	private float days;
	private String type;
	private String reason;
	private Date fromDate;
	private Date toDate;
	
	public Leave(float days, String reason) {
		this.days = days;
		this.reason = reason;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public float getDays() {
		return days;
	}
	public void setDays(float days) {
		this.days = days;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "Leave [days=" + days + ", reason=" + reason + ", fromDate=" + fromDate + ", toDate=" + toDate + "]";
	}
	
}
