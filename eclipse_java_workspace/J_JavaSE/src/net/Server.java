package net;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
	ServerSocket server=null;
	Socket socket=null;
	
	public Server()
	{
		try
		{
			server=new ServerSocket(5000);
			
			while(true)
			{
				System.out.println("serer listen...");
				socket=	server.accept();           
												
				InputStream input=socket.getInputStream();
				//--字节
				byte[] buffer=new byte[1024] ;
				int len=input.read(buffer);//另一端close 才返回-1 , 和文件的读不一样的是这里每一次读时不一定读满才返回
				String line=new String(buffer,0,len);
				//----字符
//				BufferedReader bufreader=new BufferedReader(new InputStreamReader(input));
//				String line=bufreader.readLine();//如果没有读到'\n'会一直卡住
				
				String[] s=line.split(" ");

				
				
				if(s[0].equals("put"))
				{
					new inputThread(s[1]).start();   
				
				}
				else if(s[0].equals("get"))
				{
					new outputThread(s[1]).start();
				}else if(s[0].equals("list"))
				{
					new listThread().start();
				}
			}
		}catch (Exception e)
		{
			e.printStackTrace();
			
		}
	}
	
	public static void main(String args[])
	{
		Server s=new Server();
		
	}

	class inputThread extends Thread   //put 上传
	{
		File file;
		public inputThread(String file)
		{
			this.file=new File(file);
			
		}
		public void run()
		{
			try
			{	
				InputStream input=socket.getInputStream();
				byte[] buff=new byte[1024];
				BufferedOutputStream buf=new BufferedOutputStream(new FileOutputStream("d:/server_dir/"+file.getName()));
				
				while(input.read(buff)!=-1)
				{
					buf.write(buff);
				}
				//OutputStream ar=socket.getOutputStream();
				//ar.write("你的上传完成".getBytes());
			
				socket.close();
		
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		
		}
		
		
	}
		
	class outputThread extends Thread  //相对客户下载
	{
		String file;
		public outputThread(String file)
		{
			this.file=file;
		}
		public void run()
		{
			try
			{
				OutputStream output=socket.getOutputStream();
				FileInputStream filein=new FileInputStream(file);
				int da;
			 //bufferdOuptputStream如何实现输出，即有什么来做条件
				while((da=filein.read())!=-1)
				{	
					
					output.write(da);	
				}
			
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
		}
		
		
	} 
	
	class listThread extends Thread
	{
		public void run()
		{
			File ff=new File(".");
			String[] filedir=ff.list();
			try
			{
				BufferedOutputStream buffout =new BufferedOutputStream(socket.getOutputStream());
				for(int i=0;i<filedir.length;i++)
				{
					buffout.write((filedir[i]+"\n").getBytes());
					
				}
		
			}catch(Exception e)
			{
				e.printStackTrace();
			}
	
		}
	
	} 

}
	

