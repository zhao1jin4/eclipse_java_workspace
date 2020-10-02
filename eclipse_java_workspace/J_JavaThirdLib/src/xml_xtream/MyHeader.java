package xml_xtream;

import com.thoughtworks.xstream.annotations.XStreamAlias;
 
public class MyHeader   { 
 
	@XStreamAlias("version")
	private String version;
 
	@XStreamAlias("signature")
	private String signature;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
 

}
