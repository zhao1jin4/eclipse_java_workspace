package org.zh.activiti.other;

import java.io.Serializable;
import java.util.Date;
//报销
public class ReImbursement implements Serializable
{
	private static final long serialVersionUID = 1L;
	private float amount;
	private String type;
	private String reason;
	private String projectName;

	public ReImbursement(float amount, String reason) {
		super();
		this.amount = amount;
		this.reason = reason;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public float getAmount() {
		return amount;
	}


	public void setAmount(float amount) {
		this.amount = amount;
	}


	public String getReason() {
		return reason;
	}


	public void setReason(String reason) {
		this.reason = reason;
	}


	@Override
	public String toString() {
		return "ReImbursement [amount=" + amount + ", reason=" + reason + ", projectName=" + projectName + "]";
	}

 
}
