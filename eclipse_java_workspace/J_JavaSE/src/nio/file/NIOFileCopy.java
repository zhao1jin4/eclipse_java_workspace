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
        // ��ȡԴ�ļ���Ŀ���ļ������������  
        FileInputStream fin = new FileInputStream(infile);  
        FileOutputStream fout = new FileOutputStream(outfile);  
        // ��ȡ�������ͨ��  
        FileChannel fcin = fin.getChannel();  //FileChannel �̰߳�ȫ��
        FileChannel fcout = fout.getChannel();  
        // ����������  
        ByteBuffer buffer = ByteBuffer.allocate(1024);  
        while (true) {  
            // clear�������軺������ʹ�����Խ��ܶ��������  
            buffer.clear();  
            // ������ͨ���н����ݶ���������  
           int start= buffer.position();//
            int r = fcin.read(buffer);  
            // read�������ض�ȡ���ֽ���������Ϊ�㣬�����ͨ���ѵ�������ĩβ���򷵻�-1  
            if (r == -1) {  
                break;  
            }  
            // flip�����û��������Խ��¶��������д����һ��ͨ��  
            int end=buffer.position();//
            buffer.flip(); //      ͬһ��Buffer�����β�������д����Ҫ����ָ��position
            //buffer.rewind();//Ҳ��
            int end2=buffer.position();//
            // �����ͨ���н�����д�뻺����  
            fcout.write(buffer);  
        }  
	 }
	 public static void copy1()throws Exception 
	 {
		 String infile = "C:\\temp\\file.txt";  
        String outfile = "C:\\temp\\file_1.txt";  
        FileInputStream fin = new FileInputStream(infile);  
        FileOutputStream fout = new FileOutputStream(outfile);  
        FileChannel fileChannelIn = fin.getChannel();  //FileChannel �̰߳�ȫ��
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
