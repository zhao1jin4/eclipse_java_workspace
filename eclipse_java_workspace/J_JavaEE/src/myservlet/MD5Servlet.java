package myservlet;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns="/md5Servlet")
public class MD5Servlet extends HttpServlet {

	// byte[] tool
	public static String byteToHexString(byte bytes[]) 
	{
		//char HEX[] = { '0', '1', '2', '3', '4', '5', '6','7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		int len=bytes.length;
		StringBuffer buffer = new StringBuffer(2 * len);
		for (int i = 0;i < len; i++) 
		{	
			String c0 =Integer.toHexString( (bytes[i] & 0xf0) >> 4 );  //>>>是无符号右移,左补0
			String c1 =Integer.toHexString(  bytes[i] & 0x0f       );
			//或者用
			//char c0 = HEX[ (bytes[i] & 0xf0) >> 4 ];
			//char c1 = HEX[  bytes[i] &  0x0f ];
			buffer.append(c0);
			buffer.append(c1);
		}
		return buffer.toString();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try
		{
			String password=req.getParameter("password");
			MessageDigest md5 = MessageDigest.getInstance("MD5"); // 确定计算方法
			md5.update(password.getBytes("UTF-8"));
			byte[] md5UTF8Byte=md5.digest();
			String md5Str=byteToHexString(md5UTF8Byte);
			String md5JSPassword=req.getParameter("md5Password");
			System.out.println("Server MD5 equls JS md5 is:"+md5Str.equals(md5JSPassword));
			
		}catch (Exception e) 
		{
			e.printStackTrace(); 
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		
		
		super.doPost(req, resp);
	}
	
}
