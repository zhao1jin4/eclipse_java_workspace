package net;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client 
{
	InputStream input;
	OutputStream output;
	Socket socket;
	
	public Client()
	{
		try
		{
			System.out.println("cleint connect...");
			socket=new Socket(InetAddress.getByName("localhost"),5000);
			while(true)
			{
				BufferedReader bufreader=new BufferedReader(new InputStreamReader(System.in));
				String str=bufreader.readLine();
				
				OutputStream output=socket.getOutputStream();
				//--字节
				output.write(str.getBytes());//不必调flush
				//----字符
				BufferedOutputStream bufferOut=new BufferedOutputStream(output);
				bufferOut.write(str.getBytes());
				bufferOut.write("\n".getBytes());//另一端是 BufferedReader以换行符为结束
				bufferOut.flush();//BufferedOutputStream 不调flush不会立即写
				
				String[] s=str.split(" ");
				if(s[0].equals("put"))
				{
					upLoad(s[1]);
				}else if(s[0].equals("get"))
				{
					downLoad(s[1]);
				}else if(s[0].equals("list"))
				{
					listFile();
				}
			}
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void upLoad(String file)
	{
		try
		{
			FileInputStream input=new FileInputStream (file);  //指定的文件名
			output=socket.getOutputStream();
			int len;
			byte[] buffer= new byte[10];
			while((len=input.read(buffer))!=-1)
			{
				output.write(buffer,0,len);
			}
			output.close();
			input.close();
			
		}catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("文件不存在，请检查你的文件是否正确");
		}		
	}
	public void downLoad(String file)
	{
		try
		{
			String str;
			input=socket.getInputStream();
			BufferedReader reader=new BufferedReader(new InputStreamReader(input));
			str=reader.readLine();
			FileOutputStream fileout=new FileOutputStream(file);
			
			while(str!=null)
			{
				fileout.write(str.getBytes());
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			
		}
	}
	
	public void listFile()
	{
		try
		{
			input=socket.getInputStream();
			PrintStream print=new PrintStream(System.out);
			int data;
			while((data=input.read())!=-1)
			{
				print.write(data);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String args[])
	{
		Client client=new Client();
	}
}
