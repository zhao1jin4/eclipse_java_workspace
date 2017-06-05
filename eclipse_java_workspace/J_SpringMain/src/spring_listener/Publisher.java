package spring_listener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
public class Publisher implements ApplicationContextAware
{

	ApplicationContext context=null;
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context=context;
	}
	public void publish()
	{
		context.publishEvent(new MyEvent("good"));
	}
}
