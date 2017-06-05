package activemq_web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@WebServlet("/sendMessage")
public class SendMessageServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");//只对Post请求
		//ApplicationContext ctx = new ClassPathXmlApplicationContext("spring_jms_beans.xml");//OK
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring_jms_tags.xml");//OK
		Sender sender = (Sender) ctx.getBean("sender");//要在Web里运行,才会使用Tomcat的配置
		String Text = request.getParameter("text");
		sender.send(Text);
	}

}
