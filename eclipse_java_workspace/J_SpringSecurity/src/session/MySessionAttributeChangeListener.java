package session;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

//HttpSessionListener ,sessionCreated,sessionDestroyed
//没办法监听 session.getAttribute();
public class MySessionAttributeChangeListener implements HttpSessionAttributeListener
{

	@Override
	public void attributeAdded(HttpSessionBindingEvent se) {
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent se) {
	}
}
