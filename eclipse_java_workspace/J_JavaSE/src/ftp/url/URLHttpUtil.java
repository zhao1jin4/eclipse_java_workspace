package ftp.url;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


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
	        	/*
	        	//��ʽ��
    			//ÿ����ʹ��HttpURLConnectionʱ,������������http��Ӧͷ�е�Set-Cookie����cookie����һ��ȫ�ֵĵط����Ժ��ֱ�Ӵ�����ȡ				
				CookieManager manager = new CookieManager(); 
				manager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);//����cookie���ԣ�ֻ��������Ի���������cookie����������Internet���������������͵�cookie
				CookieHandler.setDefault(manager);//�⼸�䣬Ҫ��URL .openConnection֮ǰ
	        	*/
	        	
	            URL url= new URL(strUrl);
	            HttpURLConnection http=(HttpURLConnection)url.openConnection();
	            //http.setReadTimeout(10000);//���ö�ȡ��ʱʱ��          
	            //http.setConnectTimeout(10000);//�������ӳ�ʱʱ��    
				
	            http.setRequestMethod("POST");
	            http.setRequestProperty("Content-type","application/json;charset=UTF-8");
	            //������
	            http.setDoOutput(true);//��Ҫ��дҪ�������
	            out = http.getOutputStream();
	            out.write(requestBody.getBytes("UTF-8"));
	            out.flush();

	            http.connect();//������У�����
	            //������
	            long  code = http.getResponseCode();//����������ķ�������
	            if(code!=200)
	                throw new Exception("����  ���طǳɹ�״̬");

	            input=http.getInputStream();
	            reader=new BufferedReader(new InputStreamReader(input));
	            String line=null;
	            while ((line=reader.readLine())!=null)
	            {
	                builder.append(line);
	            }
	            /*
	            //��cookie����Ӧͷ��
	            Map<String, List<String>> maps = http.getHeaderFields();
    			String cookieskey = "Set-Cookie";//��ӦͷSet-Cookie
    			List<String> coolist = maps.get(cookieskey);
    			Iterator<String> it = coolist.iterator();
    			while(it.hasNext()){
    				String val=it.next();//loginId=lisi; Max-Age=1800; Expires=Mon, 20-Apr-2020 14:04:55 GMT 
    			} 
    			
    			//��ʽ��,���Լ������ִ�ǿ����
				CookieStore cookieJar = manager.getCookieStore();
				List<HttpCookie> storeCookies = cookieJar.getCookies();
				for(HttpCookie c:storeCookies){
    				System.out.println("store cookie: "+c);
    			} 
    			
	            */
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
		
//		String responseStr= request("http://www.baidu.com","");
//		System.out.println(responseStr); 
		
		String responseStr= request("http://localhost:8080/S_HTML5CSS3/jsonGet","{\"name\":\"����\"}");
		System.out.println(responseStr); 
		
		
		
	}
	
}
