package springmvc_hessian;

import java.io.Serializable;

public class MyRequest  implements  Serializable{
	private String systemId;
	private String systemName;
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
	
}
