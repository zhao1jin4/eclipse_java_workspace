package net;
 import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

  public class TestReadTimeOutClient {

    public static void main(String args[]) throws Exception {


       // Socket socket=new Socket("127.0.0.1",4700);
    	 Socket socket=new Socket();
    	 socket.connect(new InetSocketAddress("127.0.0.1",4700), 2000);
    	 
    	 
    	 socket.setSoTimeout(1000);//客户端   设置读  服务端  的 read() timeout 
    	 socket.setSoLinger(true, 1000);//close timeout
    	 
        OutputStream output=socket.getOutputStream();
        InputStream input= socket.getInputStream();
        byte[] buffer=new byte[1024];
        int i=0;
        while(true)
        {
        	 i++;
	    	 if(i>1000)
	    		 break;
	        output.write((i+"---------from from from from from from from from from from from from from from from from from from from from from from from from from from from from from from from from from from from from fro  ===").getBytes());
	        output.write((i+"---------to  to to to to to to to to to to to to to to to to to to to to to to to to to to to to to to to to to to to to to to to to to to to to to to to to to to to vvv	to to to to to to to to ===").getBytes());
		       
	        
	        int read=input.read(buffer);
	        System.out.println("client received:"+new String(buffer,0,read));
        }
        //socket.close();
  }

}