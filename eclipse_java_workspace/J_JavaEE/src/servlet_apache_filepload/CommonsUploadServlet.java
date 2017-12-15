package servlet_apache_filepload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/upload")
public class CommonsUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		
		System.out.println("服务收到的请求头:");
		Enumeration<String> enumeration=  request.getHeaderNames();
		while(enumeration.hasMoreElements())
		{
			String name=enumeration.nextElement();
			System.out.println(name+"="+ request.getHeader(name));
		}
		
		
		int yourMaxMemorySize=20*1024*1024;//20M
		File  yourTempDirectory=new File("C:/temp/");
		int yourMaxRequestSize=50*1024*1024;//50M
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(yourMaxMemorySize);
		factory.setRepository(yourTempDirectory);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(yourMaxRequestSize);
		try {
			List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) 
			{
			    FileItem item =  iter.next();
			    if (item.isFormField())
			    {
			    	//有一个Filename=xxx的对
			    	
		    	   String name = item.getFieldName();
		    	   String value = item.getString(); //浏览器对request.setCharacterEncoding("UTF-8");的设置无效
		    	   
		    	   System.out.println(name+"="+new String(value.getBytes("iso8859-1"),"UTF-8"));//对浏览器 OK
		    	   System.out.println(name+"="+value);//对HttpClient UTF-8 OK
			    } else {
			    	String fieldName = item.getFieldName();//form 的名字
			        String pathName = item.getName();// 只有IE 带C:/  浏览器可以和request.setCharacterEncoding("UTF-8");一起使用
			        String correctName=pathName.substring(pathName.lastIndexOf(File.separator)+1);
			        
			        System.out.println("file:_brow_="+correctName);//对浏览器 OK
			        System.out.println("file:_client_="+new String(correctName.getBytes("iso8859-1"),"UTF-8"));//--ALL  NO
			        
			        
			        long sizeInBytes = item.getSize();
			        String contentType = item.getContentType();
			        
			        boolean isInMemory = item.isInMemory();
			     
			      //快速方式
			        File uploadedFile = new File("d:/temp/"+correctName);
			        item.write(uploadedFile);
			        
			        //为进度条方式
//			        FileOutputStream output=new FileOutputStream(new File("d:/temp/"+correctName));
//			        InputStream input = item.getInputStream();//得到输入流
//		    	    byte[] buffer=new byte[10*1024];
//		    	    float tota=0;
//		    	    int len=0;
//		    	    while((len=input.read(buffer))!=-1 )
//		    	    {
//		    	    	tota+=len;
//		    	    	output.write(buffer,0,len);
//		    	    	try{
//		    	    		System.out.println((tota/sizeInBytes)*100 +"%");
//		    	    		Thread.sleep(200);//for progress bar
//		    	    	}catch(Exception e) {e.printStackTrace();}
//		    	    }
//		    	    output.close();
//		    	    input.close();
		    	    
		    	    
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/* web.xml 配置     清除临时文件
	 <listener>
	    <listener-class>
	      org.apache.commons.fileupload.servlet.FileCleanerCleanup
	    </listener-class>
	  </listener>
	*/
}
