package ftp.url;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLHttpUtil
{
	
	public static String request(String strUrl,String requestStr) throws Exception
	{
		  StringBuilder builder=new StringBuilder();
	        OutputStream out =null;
	        InputStream input=null;

	        try
	        {
	            URL url= new URL(strUrl);
	            HttpURLConnection http=(HttpURLConnection)url.openConnection();
	            http.setRequestMethod("POST");
	            http.setRequestProperty("Content-type","application/json;charset=UTF-8");
	            //������
	            http.setDoOutput(true);//��Ҫ��дҪ�������
	            out = http.getOutputStream();
	            out.write(requestStr.getBytes("UTF-8"));
	            out.flush();

	            //������
	            long  code = http.getResponseCode();
	            if(code!=200)
	                throw new Exception("����  ���طǳɹ�״̬");

	            input=http.getInputStream();
	            BufferedReader reader=new BufferedReader(new InputStreamReader(input));
	            String line=null;
	            while ((line=reader.readLine())!=null)
	            {
	                builder.append(line);
	            }

	        }catch(Exception e)
	        {
	        	e.printStackTrace(); 
	            throw e;
	        }finally
	        {
	            if(input!=null) input.close();
	            if(out!=null)  out.close();
	        }
	        
	        System.out.println("����  ���ؽ��:" + builder.toString());
	        return builder.toString();

	}
	
	public static void downloadURL(String remoteURL,String localPath)
	{
		 
	}
	public static void uploadURL(String localPath,String remoteURL)
	{
		 
	}
	public static void main(String[] args) throws Exception
	{
//		URL url=new URL("http://127.0.0.1");
//		HttpURLConnection http=(HttpURLConnection)url.openConnection();
//		http.getInputStream();
//		http.getOutputStream();
		
		String responseStr= request("http://www.baidu.com/s","");
		System.out.println(responseStr); 
		
		
	}
	
}
