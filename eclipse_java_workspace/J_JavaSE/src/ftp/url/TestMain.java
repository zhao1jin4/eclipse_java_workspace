package ftp.url;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class TestMain
{

	public static void upload_2()
	{
		try
		{
			URL url = new URL("ftp://bea:bea@135.251.218.79/testURL.jpg;type=b"); //type=i for ascii,b=binary
			//URL url = new URL("ftp://lzj:123@127.0.0.1/DM/AppList/testURL.jpg;type=b"); //type=i for ascii,b=binary
			URLConnection urlc = url.openConnection();
			OutputStream os = urlc.getOutputStream(); // ÉÏ´«
			File file_in = new File("c:/temp/Winter_small.jpg");
			FileInputStream is = new FileInputStream(file_in);
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
	public static void download_2()
	{
		try
		{
			URL url = new URL("ftp://bea:bea@135.251.218.79/DM/AppList/test.jpg;type=b"); //type=i for ascii,b=binary
		//	URL url = new URL("ftp://lzj:123@127.0.0.1/DM/AppList/test.jpg;type=b"); //type=i for ascii,b=binary
			URLConnection urlc = url.openConnection();
			InputStream is = urlc.getInputStream(); // ÏÂÔØ
			FileOutputStream os = new FileOutputStream("c:/temp/getFromFTP.jpg");
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
	public static void main(String[] args)
	{
		upload_2();
		download_2();
	}

}
