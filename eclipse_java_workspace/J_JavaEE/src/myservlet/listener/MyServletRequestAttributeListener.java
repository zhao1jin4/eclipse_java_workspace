package myservlet.listener;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;

public class MyServletRequestAttributeListener implements ServletRequestAttributeListener{
	public void attributeAdded(ServletRequestAttributeEvent event) {
		System.out.println("MyServletRequestAttributeListener___attributeAdded ");
		System.out.println(event.getName()+"="+event.getValue());		
	}
	@Override
	public void attributeRemoved(ServletRequestAttributeEvent event) {
		System.out.println("MyServletRequestAttributeListener___attributeRemoved ");
		System.out.println(event.getName()+"="+event.getValue());			
	}
	@Override
	public void attributeReplaced(ServletRequestAttributeEvent event) {
		System.out.println("MyServletRequestAttributeListener___attributeReplaced ");
		System.out.println(event.getName()+"="+event.getValue());			
	}
}
