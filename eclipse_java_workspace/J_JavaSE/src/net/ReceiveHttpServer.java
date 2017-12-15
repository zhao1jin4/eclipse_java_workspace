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
		while( (len=input.read(readBuffer)) !=-1) //另一端 close,才返回-1
		{
			//如果只另一端写多于1次，这里只一次全读出
			String str=new String(readBuffer,0,len);  //必须要知道何时结束，也可根据长度
			System.out.println(str);
		}
	}
	public static void   writeResponse(OutputStream output) throws Exception
	{

		//当请求jquery.js服务端 304 Not Modified表示用本地缓存
		
		StringBuffer respHeader =new StringBuffer();
		respHeader.append("HTTP/1.1 200 OK\r\n")
			 .append("Content-Type:text/html\r\n") 
			  .append("Content-Length: 12\r\n")
//			 .append("Connection:Keep-Alive")
//			 .append("Keep-Alive:timeout=20")
//			 .append("Cache-Control:private, max-age=0, must-revalidate")
			 ;
		output.write(respHeader.toString().getBytes());
		
		output.write("\n".getBytes()); //空行
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
				int index=line.indexOf(":");//防止http头的值中有:
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
		int totalLen=0;//在解析到http头结束时已经读了多少
		boolean hasReadFirstLine=false;
		int bufferPos=0;//存缓冲区上一个换行的位置
		
		//读http头
	HEAD:while((len=input.read(readBuffer))!=-1)//读头可能只读了一部分,不一定全的，头的大小不确定 ，URL长度，头的数量和值不确定
		{
			totalLen+=len;
			for(int i=1;i<len;i++)//处理buffer,从第2开始读，下标1
			{
				if(readBuffer[i-1]=='\r' && readBuffer[i]=='\n')
				{
					String line=new String(readBuffer,bufferPos,i-1-bufferPos);
					bufferPos=i-1+2;
					if(preLen>0)
					{
						byte[] mergeBuffer=new byte[preLen+i-1];//把上一下缓冲区和现在缓冲区合在一起拼成字串
						System.arraycopy(preBuffer, 0, mergeBuffer, 0, preLen);//上一下缓冲区
						System.arraycopy(readBuffer, 0, mergeBuffer, preLen, i-1);//现在缓冲区
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
							break HEAD;//http头结束
						}
						int index=line.indexOf(":");//防止http头的值中有:
						header.put( line.substring(0,index).trim() , line.substring(index+1).trim());
					}
				}
				
			}//for
			if(bufferPos < len)//表示缓冲区中最后一行还没有读完，要保存下来
			{
				preLen=len-bufferPos;
				System.arraycopy(readBuffer, bufferPos, preBuffer, 0, preLen);
			} 
		}
		
		System.out.println("请求方式："+res.getMethod());
		System.out.println("请求路径："+res.getReqPath());
		System.out.println("协议/版本："+res.getHttpVer());
		System.out.println("请求头："+res.getHeader());
		 
		if("POST".equals(res.getMethod()))//只处理 POST读体
		{
			int bodyLen=Integer.parseInt(header.get("Content-Length").toString().trim());
			if("application/x-www-form-urlencoded".equals(header.get("Content-Type").trim()))
			{
				byte[] bodyBuffer=new byte[bodyLen];
				int remainLen=totalLen - bufferPos;//上一次buffer中还有多少个
				if(bodyLen > remainLen) //表示上个buffer 中只有一半   的 http body
				{ 
					if(remainLen > 0)
						System.arraycopy(readBuffer, bufferPos, bodyBuffer, 0,remainLen );
					if((len=IOUtils.readFully(input, bodyBuffer, remainLen, bodyLen - remainLen)) > 0 )
					{
						System.out.println("---POST 请求体：再一次读到了"+len+"个字节");
					}
				} else //remainLen == bodyLen   全部
					System.arraycopy(readBuffer, bufferPos, bodyBuffer, 0, bodyLen);
				
				String[] params=new String(bodyBuffer).split("&");
				for(String param:params)
				{
					int index=param.indexOf("=");
					if(index==0) //防止  ?a=1&b=&c=2
						postParam.put(param,null);
					 else
						postParam.put(param.substring(0,index),param.substring(index+1));
				}
				System.out.println("---POST 请求体："+postParam);
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
				
				
				String boundary=header.get("Content-Type").trim().split(";")[1].split("=")[1];  //再加\r\h
				String endBoundary=boundary+"--"; //再加\r\h
				
				res.setPostBoundary(boundary);
				
			} 
		}
		return res;
	}
	public static void main(String[] args) throws Exception
	{
		//soapUI对POST请求只能打印http 头
		//JMeter对GET请求只能打印http 头
		String newLine="\r\n";
		
		
		ServerSocket server=new ServerSocket (9000);
		System.out.println("ServerSocket 监听端口 9000");
		while(true)
		{
			Socket client=server.accept();
			System.out.println("client连接上来");
			
			InputStream input=client.getInputStream();
			
			printAllMsg(input);
			 //---二选一
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
	private String method; //GET 或 POST
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