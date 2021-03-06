package net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;


public class TCPMain 
{
	public static void main(String[] args) throws Exception 
	{
		
		System.out.println(InetAddress.getLocalHost().getHostAddress());//windows OK ,linux 127.0.0.1
		
		Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		while (allNetInterfaces.hasMoreElements())
		{
			NetworkInterface netInterface =  allNetInterfaces.nextElement();
			if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                continue;
            }
			//System.out.println(netInterface.getName());
			Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
			while (addresses.hasMoreElements())
			{
				ip =  addresses.nextElement();
				if (ip != null && ip instanceof Inet4Address)
				{
					System.out.printf("���� ����%s �� IP�� %s %n" ,netInterface.getName(), ip.getHostAddress());
				}
			}
		}
		
		if(args.length>0)
			server();
		else 
			client();
	}

	static public void server ()
	{
		try
		{
		
			ServerSocket ser=new ServerSocket (6000);
			Socket cli=ser.accept();
	
			InputStream input=cli.getInputStream();
			OutputStream output=cli.getOutputStream();
			output.write("hello welcom you".getBytes());
			byte[] byt=new byte[1000];
	
			int len=input.read(byt);
			String str=new String(byt,0,len);
			System.out.println(str);
			
			
			input.close();
			output.close();
			cli.close();
			ser.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	static public void client ()
	{
		try
		{
			Socket soc=new Socket(InetAddress.getByName("localhost"),6000);   //null localhost 127.0.0.1
			InputStream input=soc.getInputStream();
			OutputStream output=soc.getOutputStream();
			byte[] byt=new byte[1000];
			
			int len=input.read(byt);
			System.out.println(new String(byt,0,len));
			output.write("hello I am is lisi".getBytes());
			input.close();
			output.close();
			soc.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}

