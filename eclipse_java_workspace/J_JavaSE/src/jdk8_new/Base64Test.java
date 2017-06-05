package jdk8_new;

import java.net.URL;
import java.util.Base64;
public class Base64Test {

	public static void main(String[] args) throws Exception
	{
 
		//JDK
		byte[] encoded=Base64.getEncoder().encode("这是一个中文".getBytes());
		System.out.println(new String(encoded));
		
		byte[] decoded=Base64.getDecoder().decode(encoded);
		System.out.println(new String(decoded));
		
		//URL 一样的 
		{
			 URL myurl = new URL("http://example.com");
	         byte[] urlEncoded = Base64.getUrlEncoder().encode(myurl.toString().getBytes("UTF8"));
	         System.out.println("Base64 Encoded URL : " + new String(urlEncoded,"UTF-8"));
	         
	         byte[] temp=Base64.getEncoder().encode(myurl.toString().getBytes("UTF8"));
	         System.out.println("Base64 Encoded Str : " + new String(temp,"UTF-8"));
		}
		
          
      
	}

}
