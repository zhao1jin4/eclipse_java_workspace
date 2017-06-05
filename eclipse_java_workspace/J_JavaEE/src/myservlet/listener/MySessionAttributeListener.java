package myservlet.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MySessionAttributeListener implements HttpSessionAttributeListener{
	public void attributeAdded(HttpSessionBindingEvent event) {
		System.out.println("MySessionAttributeListener___attributeAdded ");
		System.out.println(event.getName()+"="+event.getValue());
		event.getValue();
	}
	public void attributeRemoved(HttpSessionBindingEvent event) {
		System.out.println("MySessionAttributeListener___attributeRemoved ");
		System.out.println(event.getName()+"="+event.getValue());
	}
	public void attributeReplaced(HttpSessionBindingEvent event) {
		System.out.println("MySessionAttributeListener___attributeReplaced");
		System.out.println(event.getName()+"="+event.getValue());
	}
}
