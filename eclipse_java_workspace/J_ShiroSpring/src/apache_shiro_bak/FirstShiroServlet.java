package apache_shiro_bak;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject; 

@WebServlet(urlPatterns="/firstShiro")
public class FirstShiroServlet extends HttpServlet{ 
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Subject currentUser = SecurityUtils.getSubject();//得到当前用户
		System.out.println(currentUser);
		
		Session session = currentUser.getSession();
		session.setAttribute( "someKey", "aValue" );
		
	}
 

}
