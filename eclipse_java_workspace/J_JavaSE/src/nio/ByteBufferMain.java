package nio;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;


public class ByteBufferMain {
 
	 
	public static void main(String[] args) throws Exception 
	{
		AtomicInteger x;
		byte[] data=new byte[]{48,48,48,56,77};
		ByteBuffer buf=ByteBuffer.wrap(data,2,3);
		int p=buf.position();//2
		int l=buf.limit();//5
		int c=buf.capacity();//5
		
		byte[] dataDest=new byte[buf.limit()-buf.position()];
		//buf.rewind();
		//buf.flip();
		buf.get(dataDest);//���Ŀ���������,��Ӳ�������,�ӵ�ǰposition��ʼ��
		
		byte[]   w=buf.array();//���ص������dataһ����
		byte[] res=Arrays.copyOfRange(data,2,3);//from,to
		
		
		ByteBuffer to=ByteBuffer.wrap(new byte[]{48,56});
		int equ=ByteBuffer.wrap(data,2,2).compareTo(to);//����0 ���
		
		
		
		System.out.println("end");
		
		
	}
}
