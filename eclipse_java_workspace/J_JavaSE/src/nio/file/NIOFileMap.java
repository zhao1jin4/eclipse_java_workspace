package nio.file;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class NIOFileMap {
	 public static void map()throws Exception 
	 {
		 String file = "C:\\temp\\file.txt";  
		 RandomAccessFile stream = new RandomAccessFile(file,"rw");  
        FileChannel fileChannel = stream.getChannel();  //FileChannel 线程安全的
        MappedByteBuffer mappedBuffer= fileChannel.map(MapMode.READ_WRITE, 1, 3);
        
//        fileChannel.position(0);
//        fileChannel.write(ByteBuffer.wrap("ABC".getBytes()));
//        fileChannel.force(true);
      
        
        System.out.println("==position="+mappedBuffer.position()+"===limit="+  mappedBuffer.limit());
        for(int i=0;i< mappedBuffer.limit();i++)
        {
        	 System.out.println((char)mappedBuffer.get(i));//OK
//        	 System.out.println( mappedBuffer.getChar(i));//getChar是两个字节 做为一个char
        	 char x='中';
        }
	 }
	
	 public static void main(String[] args) throws Exception 
	 {  
		 map();
	 }
}
