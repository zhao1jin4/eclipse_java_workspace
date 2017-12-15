package alibaba.dubbo.server;

import java.io.Serializable;

public class QueryReq  implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String ipAddr;
	private String systemId;
	private String version;
	//---
	private String queryId;
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
