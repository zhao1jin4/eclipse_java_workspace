package nio.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class NIOFileLock {
	 public static void changeFile()throws Exception 
	 {
        String outfile = "C:\\temp\\file.txt";  
        FileOutputStream fout = new FileOutputStream(outfile);  
        FileChannel fileChannel = fout.getChannel();  //FileChannel 线程安全的
        
        FileLock lock= fileChannel.lock(1,2,false);//pos,size,isShared
        try {
        	fileChannel.position(0);
        	fileChannel.write(ByteBuffer.wrap("123".getBytes()));
        	fileChannel.position(2);
        	fileChannel.write(ByteBuffer.wrap("AB".getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			 lock.release();//finally中
		}
	 }
	
	 public static void main(String[] args) throws Exception 
	 {  
		 changeFile();
	 }
}
