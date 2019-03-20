package myservlet.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


//使用spring session如何支持 HttpSessionAttributeListener ？？？？
@WebListener
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
