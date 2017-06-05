package util.thread;
public abstract class NamedRunnable implements Runnable
{	
	private String name;
	public String getName() {
		return name;
	}
	public NamedRunnable(String name)
	{
		this.name=name;
	}
	public NamedRunnable()
	{
		
	}
}