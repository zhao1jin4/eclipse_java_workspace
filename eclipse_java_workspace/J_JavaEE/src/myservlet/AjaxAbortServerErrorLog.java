package myservlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/ajaxAbortServerErrorLog.ser")
public class AjaxAbortServerErrorLog extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet"); 
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		
		//jquery .abort()方法
		
				//Servlet OutputStream 在全部响应前，浏览器关闭不会出错
			
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json;charset=UTF-8");
				OutputStream output=response.getOutputStream();
				output.write("{one:1,".getBytes());
				output.write("two:2,".getBytes());
				
				output.flush();
				response.flushBuffer();
				try {
					System.out.println("sleep 3");
					Thread.sleep(3000);
				} catch (InterruptedException e) { 
					e.printStackTrace();
				}
				output.write("three:3}".getBytes());//ajax，调用了abort()这里并没有报错
				System.out.println("done");
				//Servlet Writer 在全部响应前，浏览器关闭不会出错
//				Writer writer=response.getWriter();
//				writer.write("one");
//				writer.write("two");
//				writer.flush();				
//				response.flushBuffer();
//				writer.write("three");  
				
				
	}

}
