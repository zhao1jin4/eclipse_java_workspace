package myservlet.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MySessionListener implements HttpSessionListener
{
	public void sessionCreated(HttpSessionEvent event) {
		System.out.println("MySessionListener_sessionCreated: ");
	}
	public void sessionDestroyed(HttpSessionEvent event) {//session.invalidate()
		System.out.println("MySessionListener_sessionDestroyed: ");
	}
}
