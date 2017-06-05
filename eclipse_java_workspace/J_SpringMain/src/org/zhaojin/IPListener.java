package org.zhaojin;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;


public class IPListener implements ApplicationListener
{
	public void onApplicationEvent(ApplicationEvent event )
	{
		if( event instanceof IPEvent)
		{
			System.out.println("["+event.getSource()+"]is try to login");
		}
	}
}
