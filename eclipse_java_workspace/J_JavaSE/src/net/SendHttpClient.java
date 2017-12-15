package net;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SendHttpClient {
	
	public static void main(String[] args) throws Exception
	{
		//soapUI��POST����ֻ�ܴ�ӡhttp ͷ
		//JMeter��GET����ֻ�ܴ�ӡhttp ͷ
		
		 Socket client=new Socket ("127.0.0.1",9000);
		System.out.println("client ���� 9000");
	 	
			byte[] readBuffer=new byte[1024*10];
			OutputStream output=client.getOutputStream();
			output.write("POST / HTTP/1.1\r\n".getBytes()); //���������write ,����һ��û�м�ʱ��read �����ݾ��п��ܶ�ʧ(���һ��write)
			output.write("User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0\r\n".getBytes());//Fiefox��ͷ
			output.write("User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko\r\n".getBytes());//IE-11 ��ͷ
			
			output.write("Accept: text/html,application/xhtml+xml,application/xml\r\n".getBytes());
			
			//POST
			boolean multipart=true;
			
			if(multipart)
			{
				/*
				 * Content-type: multipart/form-data, boundary=AaB03x

        --AaB03x
        content-disposition: form-data; name="field1"

        Joe Blow
        --AaB03x
        content-disposition: form-data; name="pics"; filename="file1.txt"
        Content-Type: text/plain

         ... contents of file1.txt ...
        --AaB03x--
        
				 */
				String boundary="AaB03x";// ��Ϣ�� �ָ�Ϊ     --AaB03x
				 
				output.write( ( "Content-type: multipart/form-data, boundary="+boundary+"\r\n"  ) .getBytes());
			}
			
			output.write("Content-Type: application/x-www-form-urlencoded\r\n".getBytes());
			output.write("Content-Length: 12\r\n".getBytes());
			
			output.write("\n".getBytes());
			
			output.write("username=123".getBytes());

			//output.flush();
			//client.close();
			
			
			
			
			
			InputStream input=client.getInputStream();
			int len=0;
			while( (len=input.read(readBuffer)) !=-1) //�ͻ��˲�close,��������һ��Ҳ�ڶ�,����,ֻcloseʱ����-1
			{
				String str=new String(readBuffer,0,len);
				System.out.println(str);
			}
			 
			
			input.close();
			output.flush();
			output.close();
			client.close();
		  
	}
}
