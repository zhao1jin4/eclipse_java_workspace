package jsp;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletRequestListener implements ServletRequestListener{
	public void requestDestroyed(ServletRequestEvent event) {
		System.out.println("MyServletRequestListener_requestDestroyed ");
	}
	public void requestInitialized(ServletRequestEvent event) {
		System.out.println("MyServletRequestListenerr_requestInitialized");
	}
}
