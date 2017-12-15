
package javaee_ejb;

import java.io.IOException;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/testEJB")
public class StatelessJavaClient  extends HttpServlet
{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		try {
			InitialContext contex = new InitialContext();
			StatelessSession sless = (StatelessSession) contex.lookup("javaee_ejb.StatelessSession");
			// 全包类名,接口要服务端和客户端都要有,包名相同,(服务端部署.jar ,EJB)
			System.out.println("returnMessage():" + sless.hello());
			
			
			EJBContainer c = EJBContainer.createEJBContainer();
            Context ic = c.getContext();
            Object o= ic.lookup("java:global/ejb-embedded/SimpleEjb");
	            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
