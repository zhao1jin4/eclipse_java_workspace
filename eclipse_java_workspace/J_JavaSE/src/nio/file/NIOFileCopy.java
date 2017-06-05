package nio.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileCopy {
	 public static void copy()throws Exception 
	 {
		 String infile = "C:\\temp\\file.txt";  
        String outfile = "C:\\temp\\file_1.txt";  
        // 获取源文件和目标文件的输入输出流  
        FileInputStream fin = new FileInputStream(infile);  
        FileOutputStream fout = new FileOutputStream(outfile);  
        // 获取输入输出通道  
        FileChannel fcin = fin.getChannel();  //FileChannel 线程安全的
        FileChannel fcout = fout.getChannel();  
        // 创建缓冲区  
        ByteBuffer buffer = ByteBuffer.allocate(1024);  
        while (true) {  
            // clear方法重设缓冲区，使它可以接受读入的数据  
            buffer.clear();  
            // 从输入通道中将数据读到缓冲区  
           int start= buffer.position();//
            int r = fcin.read(buffer);  
            // read方法返回读取的字节数，可能为零，如果该通道已到达流的末尾，则返回-1  
            if (r == -1) {  
                break;  
            }  
            // flip方法让缓冲区可以将新读入的数据写入另一个通道  
            int end=buffer.position();//
            buffer.flip(); //      同一个Buffer的两次操作（读写），要重置指针position
            //buffer.rewind();//也可
            int end2=buffer.position();//
            // 从输出通道中将数据写入缓冲区  
            fcout.write(buffer);  
        }  
	 }
	 public static void copy1()throws Exception 
	 {
		 String infile = "C:\\temp\\file.txt";  
        String outfile = "C:\\temp\\file_1.txt";  
        FileInputStream fin = new FileInputStream(infile);  
        FileOutputStream fout = new FileOutputStream(outfile);  
        FileChannel fileChannelIn = fin.getChannel();  //FileChannel 线程安全的
        FileChannel fileChannelOut = fout.getChannel();  
        fileChannelIn.transferTo(0, fileChannelIn.size(), fileChannelOut);
	        
        fileChannelIn.close();
        fileChannelOut.close();
	 }
	 public static void main(String[] args) throws Exception 
	 {  
		 //copy();
		 copy1();
	 }
}
