package spring_jmx;

public class Standard implements StandardMBean{ //JMX�淶,Ҫʵ�ֽӿ�,������MBean��β
	
	private int pageSize;
	private String logLevel;
	
	public Standard()
	{
		pageSize=20;
		logLevel="WARNING";
	}
	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public void reset()
	{
		pageSize=20;
		logLevel="WARNING";
	}
}
