package myservlet.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MyContextAttributeListener implements ServletContextAttributeListener{
	public void attributeAdded(ServletContextAttributeEvent event) {
		System.out.println("MyContextAttributeListener___attributeAdded");
		System.out.println(event.getName()+"="+event.getValue());
	}
	public void attributeRemoved(ServletContextAttributeEvent event) {
		System.out.println("MyContextAttributeListener___attributeRemoved");
		System.out.println(event.getName()+"="+event.getValue());
	}
	public void attributeReplaced(ServletContextAttributeEvent event) {
		System.out.println("MyContextAttributeListener___attributeReplaced");
		System.out.println(event.getName()+"="+event.getValue());
	}
}
