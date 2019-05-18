package nio.asynchronized;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

public class AIOLockFile {

	static boolean isDone=false;
	public static void main(String[] args) {

		ByteBuffer buffer = ByteBuffer.allocate(100);
		String encoding = System.getProperty("file.encoding");
		Path path = Paths.get("/Users/zh/", "test.txt");
		 
 
		// 异步文件写示例
		ByteBuffer buffer1 = ByteBuffer.wrap("The win keeps Nadal at the top of the heap in men's".getBytes());
 
		Path path1 = Paths.get("/Users/zh/", "test.txt");
		try (AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(path1,
				StandardOpenOption.WRITE)) 
		{
			Future<Integer> result = asynchronousFileChannel.write(buffer1, 100);//开始位置
			while (!result.isDone()) {
				System.out.println("Do something else while writing ...");
			}
			System.out.println("Written done: " + result.isDone());

//--------------------
//			// file lock
			Future<FileLock> featureLock = asynchronousFileChannel.lock();
			while (!featureLock.isDone()) {
				System.out.println("waiting lock ...");
			}
			FileLock lock = featureLock.get();
			if (lock.isValid()) {
				Future<Integer> featureWrite = asynchronousFileChannel.write(buffer, 0);
				while (!featureWrite.isDone()) {  
						System.out.println("Waiting for the bytes to be written ..."); 
			            Thread.sleep(1);  
			     }  
				int written = featureWrite.get();
				// or, use shortcut
				// int written = asynchronousFileChannel.write(buffer,0).get();
				System.out.println("I’ve written " + written + " bytes into " + path.getFileName() + " locked file!");
				lock.release();
			}
//--------------------
			
//			 asynchronousFileChannel.lock("Lock operation status:", new CompletionHandler<FileLock, Object>(){
//			
//						@Override
//						public void completed(FileLock result, Object attachment) {
//							isDone=true;
//						}
//		
//						@Override
//						public void failed(Throwable exc, Object attachment) {
//			 
//							
//						}
//				}) ;
//			 while(!isDone)
//			 {
//				 System.out.println("waiting");
//			 }
//			 
//			 System.out.println("done");
//--------------------
		} catch (Exception ex) {
			System.err.println(ex);
		}
	 
	}
 
}
