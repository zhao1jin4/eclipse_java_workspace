package net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.compress.utils.IOUtils;

public class ReceiveHttpServer {
	public static void printAllMsg(InputStream input) throws Exception
	{

		byte[] readBuffer=new byte[1024*10];
		int len=0;
		while( (len=input.read(readBuffer)) !=-1) //��һ�� close,�ŷ���-1
		{
			//���ֻ��һ��д����1�Σ�����ֻһ��ȫ����
			String str=new String(readBuffer,0,len);  //����Ҫ֪����ʱ������Ҳ�ɸ��ݳ���
			System.out.println(str);
		}
	}
	public static void   writeResponse(OutputStream output) throws Exception
	{

		//������jquery.js����� 304 Not Modified��ʾ�ñ��ػ���
		
		StringBuffer respHeader =new StringBuffer();
		respHeader.append("HTTP/1.1 200 OK\r\n")
			 .append("Content-Type:text/html\r\n") 
			  .append("Content-Length: 12\r\n")
//			 .append("Connection:Keep-Alive")
//			 .append("Keep-Alive:timeout=20")
//			 .append("Cache-Control:private, max-age=0, must-revalidate")
			 ;
		output.write(respHeader.toString().getBytes());
		
		output.write("\n".getBytes()); //����
		String body="<p>12345</p>\n";
		//String body="<result><code>200</code><description>OK</description></result>";	
		output.write(body.getBytes());
		output.write("\n".getBytes());
		
		output.flush();
		output.close();
	}
	public static HttpObject   parseRequstUseReader(InputStream input) throws Exception
	{
		HttpObject res=new HttpObject();
		 Map<String,String> header=new HashMap<String,String>();
		 Map<String,String> postParam=new HashMap<String,String>();
		 res.setHeader(header);
		 res.setPostParam(postParam);
		 
		 BufferedReader reader=new BufferedReader(new InputStreamReader(input));
		 String line=null;
		 boolean hasReadFirstLine=false;
		 while((line=reader.readLine())!=null)
		 {	
			if(! hasReadFirstLine) 
			{
				hasReadFirstLine=true;
				String[] http=line.split(" ");
				res.setMethod(http[0]);
				res.setReqPath(http[1]);
				res.setHttpVer(http[2]);
			}else
			{
				int index=line.indexOf(":");//��ֹhttpͷ��ֵ����:
				header.put( line.substring(0,index).trim() , line.substring(index+1).trim());
			}
			
		 }
		 return res;
	}
	public static HttpObject   parseRequst(InputStream input) throws Exception
	{
		HttpObject res=new HttpObject();
		 Map<String,String> header=new HashMap<String,String>();
		 Map<String,String> postParam=new HashMap<String,String>();
		 res.setHeader(header);
		 res.setPostParam(postParam);
		 
		byte[] preBuffer=new byte[1024];
		int preLen=0;
		
		byte[] readBuffer=new byte[1024];
		int len=0;
		int totalLen=0;//�ڽ�����httpͷ����ʱ�Ѿ����˶���
		boolean hasReadFirstLine=false;
		int bufferPos=0;//�滺������һ�����е�λ��
		
		//��httpͷ
	HEAD:while((len=input.read(readBuffer))!=-1)//��ͷ����ֻ����һ����,��һ��ȫ�ģ�ͷ�Ĵ�С��ȷ�� ��URL���ȣ�ͷ��������ֵ��ȷ��
		{
			totalLen+=len;
			for(int i=1;i<len;i++)//����buffer,�ӵ�2��ʼ�����±�1
			{
				if(readBuffer[i-1]=='\r' && readBuffer[i]=='\n')
				{
					String line=new String(readBuffer,bufferPos,i-1-bufferPos);
					bufferPos=i-1+2;
					if(preLen>0)
					{
						byte[] mergeBuffer=new byte[preLen+i-1];//����һ�»����������ڻ���������һ��ƴ���ִ�
						System.arraycopy(preBuffer, 0, mergeBuffer, 0, preLen);//��һ�»�����
						System.arraycopy(readBuffer, 0, mergeBuffer, preLen, i-1);//���ڻ�����
						line=new String(mergeBuffer);
						
						preLen=0;
						Arrays.fill(preBuffer,(byte)0);
					}
					if(!hasReadFirstLine) // GET /index.html HTTP/1.1
					{
						hasReadFirstLine=true;
						String[] http=line.split(" ");
						res.setMethod(http[0]);
						res.setReqPath(http[1]);
						res.setHttpVer(http[2]);
					}else
					{
						if("".equals(line))
						{
							bufferPos=i-1+2;
							break HEAD;//httpͷ����
						}
						int index=line.indexOf(":");//��ֹhttpͷ��ֵ����:
						header.put( line.substring(0,index).trim() , line.substring(index+1).trim());
					}
				}
				
			}//for
			if(bufferPos < len)//��ʾ�����������һ�л�û�ж��꣬Ҫ��������
			{
				preLen=len-bufferPos;
				System.arraycopy(readBuffer, bufferPos, preBuffer, 0, preLen);
			} 
		}
		
		System.out.println("����ʽ��"+res.getMethod());
		System.out.println("����·����"+res.getReqPath());
		System.out.println("Э��/�汾��"+res.getHttpVer());
		System.out.println("����ͷ��"+res.getHeader());
		 
		if("POST".equals(res.getMethod()))//ֻ���� POST����
		{
			int bodyLen=Integer.parseInt(header.get("Content-Length").toString().trim());
			if("application/x-www-form-urlencoded".equals(header.get("Content-Type").trim()))
			{
				byte[] bodyBuffer=new byte[bodyLen];
				int remainLen=totalLen - bufferPos;//��һ��buffer�л��ж��ٸ�
				if(bodyLen > remainLen) //��ʾ�ϸ�buffer ��ֻ��һ��   �� http body
				{ 
					if(remainLen > 0)
						System.arraycopy(readBuffer, bufferPos, bodyBuffer, 0,remainLen );
					if((len=IOUtils.readFully(input, bodyBuffer, remainLen, bodyLen - remainLen)) > 0 )
					{
						System.out.println("---POST �����壺��һ�ζ�����"+len+"���ֽ�");
					}
				} else //remainLen == bodyLen   ȫ��
					System.arraycopy(readBuffer, bufferPos, bodyBuffer, 0, bodyLen);
				
				String[] params=new String(bodyBuffer).split("&");
				for(String param:params)
				{
					int index=param.indexOf("=");
					if(index==0) //��ֹ  ?a=1&b=&c=2
						postParam.put(param,null);
					 else
						postParam.put(param.substring(0,index),param.substring(index+1));
				}
				System.out.println("---POST �����壺"+postParam);
			}
			else if(header.get("Content-Type").trim().startsWith("multipart/form-data"))
			{
				/*
				
				Content-Type: multipart/form-data; boundary=---------------------------12268631225002
				 -----------------------------12268631225002
				Content-Disposition: form-data; name="username"
				
				123
				-----------------------------12268631225002
				Content-Disposition: form-data; name="pic"; filename="file.txt"
				Content-Type: text/plain
				
				file content
				-----------------------------12268631225002--
				*/
				
				
				String boundary=header.get("Content-Type").trim().split(";")[1].split("=")[1];  //�ټ�\r\h
				String endBoundary=boundary+"--"; //�ټ�\r\h
				
				res.setPostBoundary(boundary);
				
			} 
		}
		return res;
	}
	public static void main(String[] args) throws Exception
	{
		//soapUI��POST����ֻ�ܴ�ӡhttp ͷ
		//JMeter��GET����ֻ�ܴ�ӡhttp ͷ
		String newLine="\r\n";
		
		
		ServerSocket server=new ServerSocket (9000);
		System.out.println("ServerSocket �����˿� 9000");
		while(true)
		{
			Socket client=server.accept();
			System.out.println("client��������");
			
			InputStream input=client.getInputStream();
			
			printAllMsg(input);
			 //---��ѡһ
			//HttpObject res=parseRequst(input);
			HttpObject res=parseRequstUseReader(input);
			OutputStream output=client.getOutputStream();
			writeResponse(output);
			client.close();
		}
		//server.close();
	}
}
class HttpObject
{
	private String method; //GET �� POST
	private String postBoundary; //--ABCD
	private Map<String,String> postParam ;
	
	private String reqPath; //index.html
	private String httpVer; //HTTP 1.1
	private Map<String,String> header ;

	
	public String getPostBoundary() {
		return postBoundary;
	}
	public void setPostBoundary(String postBoundary) {
		this.postBoundary = postBoundary;
	}
	public Map<String, String> getPostParam() {
		return postParam;
	}
	public void setPostParam(Map<String, String> postParam) {
		this.postParam = postParam;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	 
	public String getReqPath() {
		return reqPath;
	}
	public void setReqPath(String reqPath) {
		this.reqPath = reqPath;
	}
	 
	public String getHttpVer() {
		return httpVer;
	}
	public void setHttpVer(String httpVer) {
		this.httpVer = httpVer;
	}
	public Map<String, String> getHeader() {
		return header;
	}
	public void setHeader(Map<String, String> header) {
		this.header = header;
	}
	 
}