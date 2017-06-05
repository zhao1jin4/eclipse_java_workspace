package servlet_download;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/download")
public class DownLoadServlet extends HttpServlet
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("download filename:"+request.getParameter("filename"));
		
		response.setContentType("application/x-msdownload");
		//response.setContentType("application/octet-stream");//OK
		String filename=new String("中文名.txt".getBytes("GBK"), "ISO-8859-1");//Tomcat默认情况
		response.addHeader("Content-Disposition","attachment; filename="+filename);
		OutputStream out =response.getOutputStream();
		for(int i=0;i<10;i++)
		{
			out.write(  (i+"\r\n").getBytes() );//linux 是\n ,windows 是\r\n
			try{
	    		Thread.sleep(500);//for progress bar
	    	}catch(Exception e) {e.printStackTrace();}
		}
		String cn=new String("OK you download is successfully!中文");
		out.write(cn.getBytes());
		out.close();
	}

}
