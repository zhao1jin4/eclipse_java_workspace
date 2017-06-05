package spring_jmx;

public interface StandardMBean{
	
	public String getLogLevel();
	public void setLogLevel(String logLevel) ;

	public int getPageSize();
	public void setPageSize(int pageSize);
	
	public void reset();
}
