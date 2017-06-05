package myservlet.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

public class MyServletRequestListener implements ServletRequestListener{
	public void requestDestroyed(ServletRequestEvent event) {
		System.out.println("MySessionListener_requestDestroyed ");
	}
	public void requestInitialized(ServletRequestEvent event) {
		System.out.println("MySessionListener_requestInitialized");
	}
}
