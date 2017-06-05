package apache_httpclient4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

public class TestHttpClient4 
{
	public static void sessionTest() throws Exception
	{
		//Cookie
	}
	public static void httpsTest() throws Exception
	{
		
	}
	public static void async() throws Exception
	{
		HttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT,3000);//连接时间
		httpclient.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT,3000);//数据传送时间
		
		//HttpGet httpMehtod = new HttpGet("http://localhost:8080/J_JavaEE/");//POST
		HttpPost httpMehtod=new HttpPost("http://127.0.0.1:8080/J_JavaEE/receiveForm");
		ResponseHandler<byte[]> handler = new ResponseHandler<byte[]>()
			{
			    public byte[] handleResponse(  HttpResponse response) throws ClientProtocolException, IOException 
			    {
			        HttpEntity entity = response.getEntity();
			        if (entity != null) {
			            return EntityUtils.toByteArray(entity);
			        } else {
			            return null;
			        }
			    }
			};
		byte[] response = httpclient.execute(httpMehtod, handler);
		System.out.println("返回="+new String(response,"UTF-8"));
		httpclient.getConnectionManager().shutdown();
	}
	public static void postFormRequest(boolean isUpload) throws Exception //上传不支持中文
	{
		DefaultHttpClient httpClient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(); //POST
	    
	    httppost.setHeader("user-agent","NX_Browser");//header中不能有中文
	    if(isUpload)//要加 httpmime-4.2.1.jar
	    {
			httppost.setURI(URI.create("http://127.0.0.1:8080/J_JavaEE/upload"));
			 //FileBody file = new FileBody(new File("c:/temp/中文名.txt"),"text/plain","UTF-8");//UTF-8,文件名上传带中文不行???????????
			FileBody file = new FileBody(new File("c:/temp/file.txt"),"text/plain","UTF-8");
			
			MultipartEntity reqEntity = new MultipartEntity(null,null,Charset.forName("UTF-8"));//显示在http头中
			reqEntity.addPart("userfile", file);   
			reqEntity.addPart("comment", new StringBody("文件描述",Charset.forName("UTF-8")));//部分OK
			
			httppost.setEntity(reqEntity);//debug时看还是有ASCII的编码
			
	    }else
	    {
	    	httppost.setURI(URI.create("http://127.0.0.1:8080/J_JavaEE/receiveForm"));
	    	//---方式1
//		    String encoded= URLEncoder.encode("李四","UTF-8");//不支持中文,要 URLEncoder.
//		    StringEntity reqEntity = new StringEntity("username="+encoded+"&password=123");
		    //---方式2
		    List<NameValuePair> formparams = new ArrayList<NameValuePair>();
	    	formparams.add(new BasicNameValuePair("username", "李四"));//支持中文
	    	formparams.add(new BasicNameValuePair("password", "value2"));
	    	UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
	    	httppost.setEntity(reqEntity);
	    }
	    HttpResponse response = httpClient.execute(httppost);
	    
	    System.out.println("HttpClient到的请求头:");
	    Header[] allHeader=  response.getAllHeaders();
	    for (int i = 0; i < allHeader.length; i++) 
	    {
	    	  System.out.println(allHeader[i].getName()+"="+allHeader[i].getValue());
		}
	    List<Cookie> cookies = httpClient.getCookieStore().getCookies();//JSESSIONID,Session用
	    
	    HttpEntity respEntity = response.getEntity();
	    System.out.println(response.getStatusLine());
	    if (respEntity != null) {
	      System.out.println("Response content length: " + respEntity.getContentLength());
	    }
	  
	    
	    //System.out.println(EntityUtils.toString(respEntity));// respEntity.getContent()只可调用一个
	    BufferedReader reader = new BufferedReader(new InputStreamReader(respEntity.getContent(),"UTF-8"));
	    //对应Server端response.getOutputStream().write("doPost的响应".getBytes("UTF-8"));
	    String line = null;
	    while ((line = reader.readLine()) != null)
	    {
	    	System.out.println(line); 
	    }
	    reader.close();
	   
	    httpClient.getConnectionManager().shutdown();
	}
	public static void proxyTest() throws Exception //测试OK,注意看日志
	{
		String password="xxx";
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpHost targetHost = new HttpHost("cn.bing.com");
		//UsernamePasswordCredentials 	creds= new UsernamePasswordCredentials("APAC\476425", "Zhao1@@Jin4");//这个会报NTLM authentication error
		NTCredentials creds = new NTCredentials("476425",password , "workstation", "APAC");
		httpclient.getCredentialsProvider().setCredentials(new AuthScope("172.52.17.184", 8080), creds);   
		HttpHost proxy = new HttpHost("172.52.17.184", 8080);//要设置两次IP
		httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy); 
		HttpGet httpget = new HttpGet("/");
		System.out.println("请求: " + httpget.getRequestLine());
		HttpResponse response = httpclient.execute(targetHost, httpget);
		HttpEntity entity = response.getEntity();
		System.out.println("响应状态:"+response.getStatusLine());
		// 显示结果
		BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
		String line = null;
		while ((line = reader.readLine()) != null) 
		{
		  System.out.println(line);
		}
		reader.close();
		
		httpclient.getConnectionManager().shutdown();
	}
	public static void download() throws Exception
	{
		DefaultHttpClient httpclient = new DefaultHttpClient();
		//HttpHost targetHost = new HttpHost("127.0.0.1",8080);
		HttpGet httpget = new HttpGet("http://127.0.0.1:8080/J_JavaEE/download");
		HttpResponse response =httpclient.execute(httpget);
		if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode())
		{
			
			String disposition= response.getHeaders("Content-Disposition")[0].getValue();//attachment; filename=xx
			String fileName=disposition.substring(disposition.indexOf('=')+1);
			String cnFileName=new String(fileName.getBytes("iso8859-1"),"GBK");
			
			HttpEntity entity = response.getEntity(); 
			System.out.println(entity.getContentType());
			InputStream input=entity.getContent();
			FileOutputStream output=new FileOutputStream(new File("c:/temp/"+cnFileName));
			byte[] buffer=new byte[1024];
			int len=0;
			while( (len=input.read(buffer)) != -1  )
			{
				output.write(buffer,0,len);
			}
			input.close();
			output.close();
		}
	}
	public static void simpleRequest() throws Exception
	{

	}
	public static void main(String[] args) throws Exception
	{
		//proxyTest();//OK
		//download();//OK
		//postFormRequest(true);//上传 ,文件名上传带中文不行???????????
		//postFormRequest(false);//无上传 ,OK
		 async();//OK
	}
		
		
}
