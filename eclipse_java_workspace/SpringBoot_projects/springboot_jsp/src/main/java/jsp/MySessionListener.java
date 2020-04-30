package jsp;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class MySessionListener implements HttpSessionListener
{
	public void sessionCreated(HttpSessionEvent event) {
		System.out.println("MySessionListener_sessionCreated: ");
	}
	public void sessionDestroyed(HttpSessionEvent event) {//session.invalidate()
		System.out.println("MySessionListener_sessionDestroyed: ");
	}
}
