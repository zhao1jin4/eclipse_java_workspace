package util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class MyIOUtils 
{
	public void closeQuietly(InputStream input)
	{
		if(input!=null)
		{
			try {
				input.close();
			} catch (IOException e) {
			}
		}
	}
	public void closeQuietly(OutputStream output)
	{
		if(output!=null)
		{
			try {
				output.close();
			} catch (IOException e) {
			}
		}
	}
	public static  String readFully(InputStream input)
	{
		int maxLen=10*1024;
		return readFully(input,maxLen);
	}
	
	public static  String readFully(InputStream input,int maxLen)
	{
		byte[] buffer=new byte[maxLen]; 
		int readLen=0;
		int offset=0;
		
		String requestBody=null;
		try 
		{
			while((readLen=input.read(buffer,offset,maxLen-offset))!=-1)
			{
				offset+=readLen; 
			}
			requestBody=new String(buffer,0,offset,Charset.forName("UTF-8"));
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			if(input!=null)
				try { input.close(); } catch (IOException e) { }	
		}
		return requestBody;
	}
}
