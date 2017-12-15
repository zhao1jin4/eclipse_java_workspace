package spring_jmx;

public class MyBean{//¼òµ¥Bean,·ÇJMX¹æ·¶
	
	private int pageSize;
	private String logLevel;
	
	public MyBean()
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
