package servlet4_new;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class Servlet4NewOther extends HttpServlet   {

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 int sessionTimeout=req.getServletContext().getSessionTimeout();
		 req.getServletContext().setSessionTimeout(sessionTimeout+10);
		 
		 String enc=req.getServletContext().getRequestCharacterEncoding();
		 req.getServletContext().setRequestCharacterEncoding("UTF-8");
		 
		 req.getServletContext().addJspFile("servletName", "/jspFile");
		 ServletRegistration reg= req.getServletContext().getServletRegistration("servletName");//servlet 3
		 reg.getInitParameters();
		 reg.getMappings();
		 
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPut(req, resp);
	}

	 

}
