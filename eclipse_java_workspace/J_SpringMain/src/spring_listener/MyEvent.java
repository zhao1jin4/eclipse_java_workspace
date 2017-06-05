package spring_listener;
import org.springframework.context.ApplicationEvent;

public class MyEvent extends  ApplicationEvent {
	public MyEvent(String obj) {
		super(obj);
		System.out.println("hello"+obj);
	}
}
