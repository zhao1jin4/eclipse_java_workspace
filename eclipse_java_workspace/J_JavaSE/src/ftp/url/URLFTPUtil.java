package ftp.url;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class URLFTPUtil
{
	
	public static void uploadURL(String host,String username,String password,String localFile,String remoteFile)
	{
		try
		{
			URL url = new URL("ftp://"+username+":"+password+"@"+host+"/"+remoteFile+";type=b"); //type=i for ascii,b=binary
			//URL url = new URL("ftp://lzj:123@127.0.0.1/DM/AppList/testURL.jpg;type=b"); //type=i for ascii,b=binary
			URLConnection urlc = url.openConnection();
			OutputStream os = urlc.getOutputStream(); // ÉÏ´«
			File file_in = new File(localFile);
			FileInputStream is = new FileInputStream(file_in);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) 
			{
				os.write(bytes, 0, c);
			}
			is.close();
			os.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}	
	}
	
	public static void downloadURL(String host,String username,String password,String localFile,String remoteFile)
	{
		try
		{
			URL url = new URL("ftp://"+username+":"+password+"@"+host+"/"+remoteFile+";type=b"); //type=i for ascii,b=binary
			//URL url = new URL("ftp://lzj:123@127.0.0.1/DM/AppList/testURL.jpg;type=b"); //type=i for ascii,b=binary
			
			URLConnection urlc = url.openConnection();
			InputStream is = urlc.getInputStream(); // ÏÂÔØ
			FileOutputStream os = new FileOutputStream(localFile);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
			is.close();
			os.close();
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception
	{
//		URL url=new URL("http://127.0.0.1");
//		HttpURLConnection http=(HttpURLConnection)url.openConnection();
//		http.getInputStream();
//		http.getOutputStream();
		
		
		
		
	}
	
}
