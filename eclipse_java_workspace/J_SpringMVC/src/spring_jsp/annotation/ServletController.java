package spring_jsp.annotation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

@Component("servletController")
@WebServlet(name="servletController",urlPatterns="/servletControl")
public class ServletController extends HttpRequestHandlerServlet implements HttpRequestHandler{

	@Autowired 
	ApplicationContext ctx;
	
	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		System.out.println("in ServletController");
	}

}
