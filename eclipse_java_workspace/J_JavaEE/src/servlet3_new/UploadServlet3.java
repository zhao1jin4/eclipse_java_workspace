package servlet3_new;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


@WebServlet(urlPatterns="/uploadServlet3")
@MultipartConfig(maxFileSize=50*1024*1024) //用于支持文件上传 enctype="multipart/form-data"
public class UploadServlet3 extends HttpServlet{

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("UploadServlet3 in doGet");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		Part file1=req.getPart("attache1");//是<input type=file name="attache1"
		String name=file1.getName();//attache1 
//		Collection headers=file1.getHeaderNames();
//		System.out.println(headers);
		String pairs=file1.getHeader("content-disposition");
//		System.out.println(pairs);
		
		//form-data; name="attache1"; filename="中文测试.txt"  
		//form-data; name="attache2"; filename=""
		String nameHeader="filename=";
		String path=pairs.substring(pairs.indexOf(nameHeader)+nameHeader.length()+1,pairs.lastIndexOf("\""));
		if(!"".equals(path))
		{
			String filename=path.substring(path.lastIndexOf("\\")+1);//只IE是带C:\,和req.setCharacterEncoding("UTF-8")中文 OK
			file1.write("d:/temp/"+filename);//一个Part要调用一次write
		}
	
		String type=file1.getContentType();
		long  size=file1.getSize();
		
		
		Part file2=req.getPart("attache2");
		String pairs2=file2.getHeader("content-disposition");
		System.out.println(pairs2);
		
		InputStream input=file2.getInputStream();
		String param=req.getParameter("username");
		System.out.println(new String(param.getBytes("iso8859-1"),"UTF-8"));//中文 OK
		
	}

}
