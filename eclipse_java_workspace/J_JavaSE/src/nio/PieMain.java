package nio;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.nio.channels.WritableByteChannel;

public class PieMain {
 
	public static void pipedStream() throws Exception 
	{
		PipedOutputStream output=new PipedOutputStream();
		PipedInputStream input=new PipedInputStream();
		output.connect(input);
		int len = -1;  
        byte[] buffer = new byte[1024];  
         //若没有数据可读,则让读进程等待(见read()函数)  
         while ((len = input.read(buffer)) != -1) 
         {  
             System.out.println(new String(buffer, 0, len));  
         }
         
	}
	public static void nioPiped() throws Exception 
	{
		Pipe pipe=Pipe.open( );
		Pipe.SourceChannel source=pipe.source();//ReadableByteChannel
		Pipe.SinkChannel sink=pipe.sink();//WritableByteChannel
		//Pipe.SourceChannel（管道负责读的一端）和Pipe.SinkChannel（管道负责写的一端）
		
		//是AbstractSelectableChannel可以与selector一起使用
		
		WritableByteChannel out = Channels.newChannel (System.out);
		
		
	}
	public static void main(String[] args) throws Exception 
	{
		//pipedStream();
		nioPiped();
	}
}
