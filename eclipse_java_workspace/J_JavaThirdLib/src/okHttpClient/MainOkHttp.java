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
		//selenium-java ����okhttp 3
		
		//getRequest(); //ok
//		postRequest();//ok
		uploadFile();// 
//		downloadFile(); //�����õ������ ���� �ļ�������
	}

	public static void getRequest( )  
	{
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
                .get()
                .url("https:www.baidu.com")
                .build();
		Call call = client.newCall(request);
		//ͬ������
		try
		{
			Response response = call.execute();
			System.out.println("ͬ�����÷���"+response.body().string());
		}catch(Exception e )
		{
			e.printStackTrace();
		}
		/*
		//�첽����,�����ûص�����,ִ���겻���˳�
		call.enqueue(new Callback() {
		    @Override
		    public void onFailure(Call call, IOException e) {
		        System.out.println("�첽����ʧ��"+e);
		    }
		    @Override
		    public void onResponse(Call call, final Response response) throws IOException {
		        final String res = response.body().string();
		        System.out.println("�첽���÷���"+res);
		    }
		});
		*/
	}
	public static void postRequest() 
	{
		FormBody formBody = new FormBody.Builder()
                .add("username", "�û���user")
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
			System.out.println("ͬ�����÷���"+response.body().string());
		}catch(Exception e )
		{
			e.printStackTrace();
		}
	}
	public static void uploadFile( )   //�ļ��� Ϊ�������룿��
	{
		File file = new File("d:/tmp/my.jpg");
		File fileCn = new File("d:/tmp/����.jpg"); 
		
		MediaType cnMedia=MediaType.parse("application/octet-stream");
		cnMedia.charset(Charset.forName("UTF-8"));
		 
		RequestBody muiltipartBody = new MultipartBody.Builder()
		        //һ��Ҫ�������
		        .setType(MultipartBody.FORM)
		        .addFormDataPart("username", "�û���1")//���Ŀ���
		        .addFormDataPart("departName", "������2")
		        .addFormDataPart("password", "pass")
		        .addFormDataPart("attache1", "my.jpg", RequestBody.create(MediaType.parse("application/octet-stream"), file))
		        .addFormDataPart("attache2", "����.jpg", RequestBody.create(cnMedia, fileCn))//�����Ҫת��ΪUTF-8����
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
			System.out.println("ͬ�����÷���"+response.body().string());
		}catch(Exception e )
		{
			e.printStackTrace();
		}
	}
	public static void downloadFile() throws Exception //�����õ������ ���� �ļ�������
	{
	    OkHttpClient client = new OkHttpClient();
	    final Request request = new Request.Builder()
	            .get()
	            .url("http://127.0.0.1:8080/J_JavaEE/download?filename="+URLEncoder.encode("�ļ�1.txt","UTF-8"))
	            .build();
	    Call call = client.newCall(request);
	    call.enqueue(new Callback() 
	    {
	        @Override
	        public void onFailure(Call call, IOException e) {
	        	 System.out.println("�첽����ʧ��"+e);
	        }
	        @Override
	        public void onResponse(Call call, Response response) throws IOException 
	        {
	        	String disposition= response.header("Content-Disposition") ;//attachment; filename=xx
				String fileName=disposition.substring(disposition.indexOf('=')+1);
				String cnFileName=new String(fileName.getBytes("iso8859-1"),"UTF-8");//����ת��Ϊ���������� UTF-8,GBK�����У�����
				
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
