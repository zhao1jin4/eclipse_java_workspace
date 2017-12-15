package net;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

  public class TestReadTimeOutServer{

    public static void main(String args[]) throws Exception {
	 ServerSocket   server=new ServerSocket(4700);
	 Socket client=server.accept();
	 
//	 client.setSoTimeout(1000);//服务端设置  读 客户端的 read() timeout 
//	 client.setSoLinger(true, 1000);//close timeout
	 
	   InputStream input=client.getInputStream();
	   OutputStream output=client.getOutputStream();
	   byte[] buffer=new byte[1024];
	   int i=0;
	     while(true)
	     {
	    	   int read=input.read(buffer);
			      System.out.println("server received:"+new String(buffer,0,read));
			      
	    	 i++;
	    	 if(i>1000)
	    		 break;
			  output.write((i+"---------hello hellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohellohello  ==").getBytes());
		     
		   
		      
	     }
	      
    }   
  }