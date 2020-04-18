package ftp.url;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLHttpUtil
{
	
	public static String request(String strUrl,String requestBody) throws Exception
	{
		  StringBuilder builder=new StringBuilder();
	        OutputStream out =null;
	        InputStream input=null;
	        BufferedReader reader=null;
	        try
	        {
	            URL url= new URL(strUrl);
	            HttpURLConnection http=(HttpURLConnection)url.openConnection();
	            //http.setReadTimeout(10000);//设置读取超时时间          
	            //http.setConnectTimeout(10000);//设置连接超时时间    
				
	            http.setRequestMethod("POST");
	            http.setRequestProperty("Content-type","application/json;charset=UTF-8");
	            //传参数
	            http.setDoOutput(true);//如要先写要调用这个
	            out = http.getOutputStream();
	            out.write(requestBody.getBytes("UTF-8"));
	            out.flush();

	            http.connect();//这个可有，可无
	            //读返回
	            long  code = http.getResponseCode();//这里才真正的发起请求
	            if(code!=200)
	                throw new Exception("请求  返回非成功状态");

	            input=http.getInputStream();
	            reader=new BufferedReader(new InputStreamReader(input));
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
	            if(reader!=null)  reader.close();
	        }
	        
	        System.out.println("调用  返回结果:" + builder.toString());
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
		
//		String responseStr= request("http://www.baidu.com","");
//		System.out.println(responseStr); 
		
		String responseStr= request("http://localhost:8080/S_HTML5CSS3/jsonGet","{\"name\":\"李四\"}");
		System.out.println(responseStr); 
		
		
		
	}
	
}
