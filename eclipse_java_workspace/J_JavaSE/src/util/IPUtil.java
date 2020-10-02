package util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class IPUtil {
	
	public static List<String> getLocalIP()  
	{
		 List<String> res=new  ArrayList<String>();
		 try
		 {
			String osName = System.getProperty("os.name" );
			if(osName.startsWith("Windows"))
			{
				res.add(InetAddress.getLocalHost().getHostAddress());
				return res;
			}
	
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
						res.add(ip.getHostAddress());
						//System.out.printf("本机 网卡%s 的 IP是 %s %n" ,netInterface.getName(), ip.getHostAddress());
					}
				}
			}
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return res;
	}
	
	
	public static void main(String[] args) {
		System.out.println(getLocalIP());
	}

}
