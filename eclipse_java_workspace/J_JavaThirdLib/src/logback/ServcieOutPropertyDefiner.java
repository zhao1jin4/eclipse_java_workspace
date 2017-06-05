package logback;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.PropertyDefiner;
import ch.qos.logback.core.status.Status;

public class ServcieOutPropertyDefiner implements  PropertyDefiner {
	private String logHome;
	public void setLogHome(String logHome) {
		this.logHome = logHome;
	}
	//这个方法返回值,作为配置
	@Override
	public String getPropertyValue() {
		return logHome+"/performance/";
	}
	@Override
	public void addError(String arg0) {
		
	}

	@Override
	public void addError(String arg0, Throwable arg1) {
		
	}

	@Override
	public void addInfo(String arg0) {
		
	}

	@Override
	public void addInfo(String arg0, Throwable arg1) {
		
	}

	@Override
	public void addStatus(Status arg0) {
		
	}

	@Override
	public void addWarn(String arg0) {
		
	}

	@Override
	public void addWarn(String arg0, Throwable arg1) {
		
	}

	@Override
	public Context getContext() {
		return null;
	}

	@Override
	public void setContext(Context arg0) {
		
	}
}
