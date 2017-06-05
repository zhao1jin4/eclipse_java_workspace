package hessian;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class MyRequest  implements  Serializable
{
	private static final long serialVersionUID = 1L;
	private BigDecimal amt;
	private Date startDate;
	
	private String systemId;
	private String systemName;
	
	private String desc; //测试 有相同的属性,好像没啥问题
	private  DataObj  mydata; //测试 有相同的属性,好像没啥问题
	//private List<DataObj> mydata;
	
	public String getDesc() {
		return desc;
	}
	public DataObj getMydata() {
		return mydata;
	}
	public void setMydata(DataObj mydata) {
		this.mydata = mydata;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public BigDecimal getAmt()
	{
		return amt;
	}
	public void setAmt(BigDecimal amt)
	{
		this.amt = amt;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
}
