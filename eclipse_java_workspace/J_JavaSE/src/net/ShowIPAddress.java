package net;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class ShowIPAddress {

	public static void main(String[] args) throws SocketException {
		//得到本机网卡 
		Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
		while (networkInterfaces.hasMoreElements()) {
			NetworkInterface networkInterface = networkInterfaces.nextElement();
			System.out.println("name=" + networkInterface.getName() + ",DisplayName=" + networkInterface.getDisplayName());
			Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
			while (inetAddresses.hasMoreElements()) {
				InetAddress inetAddress = inetAddresses.nextElement();
				if (inetAddress != null && inetAddress instanceof Inet4Address) { // IPV4
					String ip = inetAddress.getHostAddress();
					System.out.println(ip);
				}
			}
		}
		
		
		
		
	}

}
