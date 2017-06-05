package alibaba.dubbo;

import java.io.Serializable;

public class QueryRes implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private   String resultCode;
	private  String resultDescription;
	//---
	private  String data;
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultDescription() {
		return resultDescription;
	}
	public void setResultDescription(String resultDescription) {
		this.resultDescription = resultDescription;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
}
