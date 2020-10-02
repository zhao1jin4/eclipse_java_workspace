package okHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainOkHttp {
	/*
	  implementation("com.squareup.okhttp3:okhttp:4.6.0")
		<dependency>
		    <groupId>com.squareup.okhttp3</groupId>
		    <artifactId>okhttp</artifactId>
		    <version>4.6.0</version>
		</dependency> 
	 */
	public static void main(String[] args) throws Exception   {
		//selenium-java 依赖okhttp 3
		
		//getRequest(); //ok
//		postRequest();//ok
		uploadFile();// 
//		downloadFile(); //不能拿到服务端 中文 文件名？？
	}

	public static void getRequest( )  
	{
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
                .get()
                .url("https:www.baidu.com")
                .build();
		Call call = client.newCall(request);
		//同步调用
		try
		{
			Response response = call.execute();
			System.out.println("同步调用返回"+response.body().string());
		}catch(Exception e )
		{
			e.printStackTrace();
		}
		/*
		//异步调用,并设置回调函数,执行完不会退出
		call.enqueue(new Callback() {
		    @Override
		    public void onFailure(Call call, IOException e) {
		        System.out.println("异步调用失败"+e);
		    }
		    @Override
		    public void onResponse(Call call, final Response response) throws IOException {
		        final String res = response.body().string();
		        System.out.println("异步调用返回"+res);
		    }
		});
		*/
	}
	public static void postRequest() 
	{
		FormBody formBody = new FormBody.Builder()
                .add("username", "用户名user")
                .add("password", "pass")
                .build(); 
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.post(formBody)
                .url("http://127.0.0.1:8080/J_JavaEE/receiveForm")
                .build();
		Call call = client.newCall(request);
		try
		{
			Response response = call.execute();
			System.out.println("同步调用返回"+response.body().string());
		}catch(Exception e )
		{
			e.printStackTrace();
		}
	}
	public static void uploadFile( )   //文件名 为中文乱码？？
	{
		File file = new File("d:/tmp/my.jpg");
		File fileCn = new File("d:/tmp/中文.jpg"); 
		
		MediaType cnMedia=MediaType.parse("application/octet-stream");
		cnMedia.charset(Charset.forName("UTF-8"));
		 
		RequestBody muiltipartBody = new MultipartBody.Builder()
		        //一定要设置这句
		        .setType(MultipartBody.FORM)
		        .addFormDataPart("username", "用户名1")//中文可以
		        .addFormDataPart("departName", "部门名2")
		        .addFormDataPart("password", "pass")
		        .addFormDataPart("attache1", "my.jpg", RequestBody.create(MediaType.parse("application/octet-stream"), file))
		        .addFormDataPart("attache2", "中文.jpg", RequestBody.create(cnMedia, fileCn))//服务端要转换为UTF-8才行
		        .build(); 
		//RequestBody requestBodyImg = RequestBody.create(MediaType.parse("application/octet-stream"), file);
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.post(muiltipartBody)
                .url("http://127.0.0.1:8080/J_JavaEE/uploadServlet3")
                .build();
		Call call = client.newCall(request);
		try
		{
			Response response = call.execute();
			System.out.println("同步调用返回"+response.body().string());
		}catch(Exception e )
		{
			e.printStackTrace();
		}
	}
	public static void downloadFile() throws Exception //不能拿到服务端 中文 文件名？？
	{
	    OkHttpClient client = new OkHttpClient();
	    final Request request = new Request.Builder()
	            .get()
	            .url("http://127.0.0.1:8080/J_JavaEE/download?filename="+URLEncoder.encode("文件1.txt","UTF-8"))
	            .build();
	    Call call = client.newCall(request);
	    call.enqueue(new Callback() 
	    {
	        @Override
	        public void onFailure(Call call, IOException e) {
	        	 System.out.println("异步调用失败"+e);
	        }
	        @Override
	        public void onResponse(Call call, Response response) throws IOException 
	        {
	        	String disposition= response.header("Content-Disposition") ;//attachment; filename=xx
				String fileName=disposition.substring(disposition.indexOf('=')+1);
				String cnFileName=new String(fileName.getBytes("iso8859-1"),"UTF-8");//不能转换为正常的中文 UTF-8,GBK都不行？？？
				
	            InputStream is = response.body().byteStream();
	            int len = 0;
	            File file  = new File("e:/tmp/result.txt");
	            FileOutputStream fos = new FileOutputStream(file);
	            byte[] buf = new byte[128];
	            while ((len = is.read(buf)) != -1){
	                fos.write(buf, 0, len);
	            }
	            fos.flush();
	            fos.close();
	            is.close();
	        }
	    });
	}
 
}
