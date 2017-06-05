package myservlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class MySessionUser implements HttpSessionBindingListener{
	private String name;
	private Object source;
	private Object value;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private static List<MySessionUser> online=new ArrayList<>();
	
	
	public void valueBound(HttpSessionBindingEvent event) {//session.setAttribute(MySessionUser)时调用这个方法
		System.out.println("MySessionListener_valueBound: ");
		this.name=event.getName();
		this.source=event.getSource();
		this.value=event.getValue();
		online.add(this);
		
		System.out.println("source="+event.getSource()+",name="+event.getName()+",value="+event.getValue());
		event.getSession();
	}
	public void valueUnbound(HttpSessionBindingEvent event) {
		System.out.println("MySessionListener_valueUnbound");
		online.remove(this);
	}
}
