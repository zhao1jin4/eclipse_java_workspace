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
		buf.get(dataDest);//会把目标数组加满,如加不满报错,从当前position开始加
		
		byte[]   w=buf.array();//返回的数组和data一样的
		byte[] res=Arrays.copyOfRange(data,2,3);//from,to
		
		
		ByteBuffer to=ByteBuffer.wrap(new byte[]{48,56});
		int equ=ByteBuffer.wrap(data,2,2).compareTo(to);//返回0 相等
		
		
		
		System.out.println("end");
		
		
	}
}
