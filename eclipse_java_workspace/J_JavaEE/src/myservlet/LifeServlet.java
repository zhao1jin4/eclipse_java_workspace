package myservlet;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//@WebInitParam(name="debug",value="true")
@WebServlet(urlPatterns="/life",
	//loadOnStartup=1,
	initParams={
		@WebInitParam(name="location",value="/WEB-INF/"),//和类级的@WebInitParam只可有一个出现
		@WebInitParam(name="debug",value="true")
	}
)
public class LifeServlet extends HttpServlet//单实例 
{
	 private int count=0;//最好不要加属性,必须加 synchroized等确保线程安全, final的常量
	public void init(ServletConfig config) throws ServletException//如不加load-on-startup,第一次请求时初始化 
	{
		System.out.println("-----------init");
		
		ServletContext contex=config.getServletContext();//OK
		String contexParam=contex.getInitParameter("life-name");
		System.out.println("life-name="+contexParam);
		
		String debug=config.getInitParameter("debug");
		System.out.println("debug="+debug);

		//debug=getInitParameter("debug");//Null		
		//contex=getServletContext();//这里报NullPointer
	}
	public void destroy() {
		System.out.println("-----------destroy");
	}
	
	//service方法默认实现是来决定调用doGet还是doPost方法
//	protected void service(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		System.out.println("-----------service");
//	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("-----------doGet");
		doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("-----------doPost");
		System.out.println("this--------:" + this);//看 hashcode是一个对象
		System.out.println("thread-------:" +Thread.currentThread());//是不同的线程运行,如加属性不是线程安全的,
		for(int i=0;i<2;i++)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("this:"+this +",thread:"+Thread.currentThread()+",count:" + count++);
		}
		
		if("POST".equals(request.getMethod()))
			request.setCharacterEncoding("UTF-8");//对<form method="post"生效,如是get无效,
		
		String username=request.getParameter("username");
		if("GET".equals(request.getMethod()))
			username=new String(username.getBytes("iso8859-1"),"UTF-8");
		//javac -encoding GBK XX.java
		
		
//		response.addHeader("Content-Type","text/html;charset=UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		
		
		//禁用Cookie,IE中tools->internet options->privacy->advanced->选中override automatic cookie handling,再选两个block
		//对localhost无效,要使用本机IP
		HttpSession session=request.getSession();
		Object sessionObj=session.getAttribute("sessionObj");
		if(sessionObj==null)
		{
			MySessionUser u=new MySessionUser();
			session.setAttribute("sessionObj", u);
		}
		//如有禁用Cookie使用response.encodeURL("");会自动加jsessionid的参数
		request.getRequestDispatcher(response.encodeURL("/ok.jsp")).forward(request, response);
		
		session.isNew();
		session.getId();//jsessionid为键的值 ,<form action="abc;jsessionid=xxx"
		session.getCreationTime();
		session.getLastAccessedTime();
		session.getMaxInactiveInterval();
		session.invalidate();//注销
		
		
//		ServletContext contex=getServletContext();//null
//		ServletConfig config=getServletConfig();//null
//		ServletContext contex=config.getServletContext();
//		InputStream input=contex.getResourceAsStream("freemarker.properties");
//		input.close();
//		contex.getResource("/ok.jsp");
		
		//-------
//		response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE,"系统繁忙");//SC=Server Code
//		response.sendError(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION,"未授权");
//		response.sendError(HttpServletResponse.SC_NOT_FOUND,"找不到页");
//		response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"出现错误");
//		response.sendRedirect("/ok.jsp");
//		request.getRequestDispatcher("/ok.jsp").forward(request, response);//后面的不会被执行
//		request.getRequestDispatcher("/ok.jsp").include(request, response);
		
		//-------
		
		PrintWriter writer =response.getWriter();
		//response.getOutputStream();
		//request.getInputStream();
		writer.println("<h2>中国</h2>");
		writer.close();//如是被其它Servlet,调用request.getRequestDispatcher("/ok.jsp").include(),就不要关闭
	}

}
