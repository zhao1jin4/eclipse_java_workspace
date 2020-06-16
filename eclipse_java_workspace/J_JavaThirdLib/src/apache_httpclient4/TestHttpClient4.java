package apache_httpclient4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
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
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class TestHttpClient4 
{
	public static void main(String[] args) throws Exception
	{
		//download();//OK
		//async();//OK
		
		postFormRequest(true);//上传 ,文件名  带中文不行???????????
		//postFormRequest(false);//无上传 ,OK
		//proxyTest();
	}
	
	public static void sessionTest() throws Exception
	{
		//Cookie
	}
	public static void httpsTest() throws Exception
	{
		
	}
	public static void async() throws Exception
	{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		//HttpGet httpMehtod = new HttpGet("http://localhost:8080/J_JavaEE/");
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
		
	}
	public static void postFormRequest(boolean isUpload) throws Exception  
	{
		CloseableHttpClient httpClient = HttpClients.createDefault();
	    HttpPost httppost = new HttpPost(); //POST
	   
	    httppost.setHeader("user-agent","NX_Browser");//header中不能有中文
	    if(isUpload)//要加 httpmime-4.2.1.jar
	    {
	    	httppost.setURI(URI.create("http://127.0.0.1:8080/J_JavaEE/uploadServlet3"));
	    	ContentType textContentType= ContentType.create("text/plain", "UTF-8"); 
	    	
	    	FileBody file1 = new FileBody(new File("d:/tmp/my.jpg"),ContentType.IMAGE_JPEG,"my.jpg");
	    	
			FileBody file2 = new FileBody(new File("d:/tmp/中文.jpg"),textContentType ,"中文.jpg"); //文件名  中文不行 ??????????? 
			//String charset=file2.getCharset();
			
//			FileBody file3 = new FileBody(new File("d:/tmp/my.txt"),ContentType.TEXT_PLAIN ,"my.txt"); 
			 HttpEntity reqEntity = MultipartEntityBuilder.create()
					 .setCharset(Charset.forName("UTF-8"))
					 .addPart("attache1", file1)
					 .addPart("attache2", file2)
					 .addTextBody("departName", "部门名1",textContentType)//中文正常
					 .addPart("username", new StringBody("用户名1",textContentType))//中文正常
//					 .addPart("username", new StringBody("user01",ContentType.MULTIPART_FORM_DATA))
					.build();
			httppost.setEntity(reqEntity);
			
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
	   
	}
	public static void download() throws Exception
	{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://127.0.0.1:8080/J_JavaEE/download?filename="+URLEncoder.encode("文件1.txt","UTF-8"));
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

	public static void proxyTest() throws Exception  
	{
		String domainUserId="domain/userid";
		String domain ="domain";
		String userId="userid";
		String password="xxx";
		String proxyIp="172.52.17.184";
		int port=8080;
	        
        
        // 设置代理HttpHost
        HttpHost proxy = new HttpHost(proxyIp, port, "http");
        // 设置认证
        CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(new AuthScope(proxy), new UsernamePasswordCredentials(userId, password));

        CloseableHttpClient httpClient = HttpClients.custom().setDefaultCredentialsProvider(provider).build();

        RequestConfig config = RequestConfig.custom().setProxy(proxy)
        		 .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .setConnectionRequestTimeout(3000)
                .build();

        HttpGet httpGet = new HttpGet("/");
        httpGet.setConfig(config);

        HttpHost target = new HttpHost("baidu.com", 80);
        CloseableHttpResponse response = httpClient.execute(target, httpGet);

        if (response.getStatusLine().getStatusCode() == 200)
        {
            System.out.println("OK");
        }
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
	}
		
}
