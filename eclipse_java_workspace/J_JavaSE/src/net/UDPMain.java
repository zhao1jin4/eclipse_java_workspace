package net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPMain 
{
	public void  server ()
	{
		try{
				byte[] buff=new byte[1000];
				
				DatagramPacket pack=new DatagramPacket(buff,buff.length);
				
				DatagramSocket rev=new DatagramSocket(6000);
				rev.receive(pack);//����
				System.out.println(new String(buff,0,buff.length));//pack.getLength()�ĳ���С��buff.length
					
				String str=new String ("hello welcom you");
				DatagramPacket sendpack=new DatagramPacket(str.getBytes(),str.getBytes().length,
							pack.getAddress(),pack.getPort());//�����ĵ�ַ
												//str.length()
				rev.send(sendpack);
				rev.close();
			}catch(Exception e )	
			{
				e.printStackTrace();
				
			}
	}



	public void client()
	{
		try{
			//byte[] buff=new byte[1000];
			String str=new String("hello world i am is send");
			
			//client����ַ
			DatagramPacket packet=new DatagramPacket(str.getBytes(),str.length(),InetAddress.getByName("localhost"),6000);
			
			DatagramSocket socket=new DatagramSocket();
			socket.send(packet);

			byte[] buff=new byte[1000];

			DatagramPacket receive=new DatagramPacket(buff,1000);
			socket.receive(receive);//��������
			
			System.out.println(new String (buff,0,receive.getLength()));


			
			socket.close();
		}catch(Exception e )
		{
			e.printStackTrace();
		}
	}


	public static void main(String[] args) 
	{
		UDPMain net=new UDPMain();
		if(args.length>0)
		{
			net.server(); 
		}else{
			net.client();
		}
	}
}
