package jcraft_jsch_sftp;

import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;

public class TestURL {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try
		{
			//URLConnection conn=new URL("ftps://192.168.146.128").openConnection();
			
			InetAddress ip=InetAddress.getByName("192.168.146.128");//VMware can not connect
			//InetAddress ip=InetAddress.getByName("10.39.101.238");
			
			
			Socket s=new Socket(ip,22);
			s.getOutputStream();
			s.close();
			
			System.out.println("OK");
		}catch(Exception e)
		{	
			e.printStackTrace();
			
		}
		
		
		
		
		
	}

}
