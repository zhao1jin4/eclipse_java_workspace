package nio.asynchronized;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
 
public class Server { 
    public static void main(String args[]) throws Exception{ 


		final int DEFAULT_PORT = 8888;
		final String IP = "127.0.0.1";
		ExecutorService taskExecutor = Executors.newCachedThreadPool(Executors.defaultThreadFactory());
		
		
		// 使用threadGroup
		 ExecutorService executorService = Executors .newCachedThreadPool(Executors.defaultThreadFactory());
		 AsynchronousChannelGroup  threadGroup =  AsynchronousChannelGroup.withCachedThreadPool(executorService, 3);
		
		// create asynchronous server socket channel bound to the default group
		//try (AsynchronousServerSocketChannel asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open())
		try ( AsynchronousServerSocketChannel asynchronousServerSocketChannel =  AsynchronousServerSocketChannel.open(threadGroup))
		{
			
			if (asynchronousServerSocketChannel.isOpen()) {
				// set some options
				asynchronousServerSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024);
				asynchronousServerSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
				// bind the server socket channel to local address
				asynchronousServerSocketChannel.bind(new InetSocketAddress(IP, DEFAULT_PORT));
				// display a waiting message while ... waiting clients
				System.out.println("Waiting for connections ...");
				while (true) {
					//-------------//方式1 OK
				 Future<AsynchronousSocketChannel> asynchronousSocketChannelFuture = asynchronousServerSocketChannel .accept();
					try {
						final AsynchronousSocketChannel asynchronousSocketChannel = asynchronousSocketChannelFuture.get();//阻塞直到连接上来
						Callable<String> worker = new Callable<String>() {
							@Override
							public String call() throws Exception {
								String host = asynchronousSocketChannel.getRemoteAddress().toString();
								System.out.println("Incoming connection from: " + host);
								final ByteBuffer buffer = ByteBuffer.allocate(1024);
								// transmitting data
								int readed=0;
								while ((readed=asynchronousSocketChannel.read(buffer).get()) != -1) {
									//客户不退出，一直读到0个，没有阻塞？？？？
									if(readed==0)
										System.out.println(".");
									byte[] array=buffer.array();
									System.out.println("server receive data:"+new String(array));
									buffer.flip();
									asynchronousSocketChannel.write(buffer).get();
								}
								
								if (buffer.hasRemaining()) {
									buffer.compact();
								} else {
									buffer.clear();
								}
								asynchronousSocketChannel.close();
								System.out.println(host + " was successfully served!");
								return host;
							}
						};
						taskExecutor.submit(worker);
					} catch (InterruptedException | ExecutionException ex) {
						System.err.println(ex);
						System.err.println("\n Server is shutting down ...");
						// this will make the executor accept no new threads
						// and finish all existing threads in the queue
						taskExecutor.shutdown();
						// wait until all threads are finished
						while (!taskExecutor.isTerminated()) {
						}
						break;
					}
					//-------------//方式2 Fail
					 //使用CompletionHandler来处理IO事件
//					 asynchronousServerSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>(){
//
//						public void completed(AsynchronousSocketChannel result, Void attachment)
//						{ 
//							try {
//								System.out.println("Incoming connection from: "+result.getRemoteAddress() );
//								final ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
//								 while(result.read(buffer).get()!= -1)
//								 {
//										buffer.flip();
//										System.out.println("data: "+buffer.toString());
//								 }
//							}catch(Exception e){
//								e.printStackTrace();
//							} 
//						}
//						public void failed(Throwable exc, Void attachment) {
//							 
//						}
//						 
//					 });
					 
					//----- 
					 
					 
				}
			} else {
				System.out.println("The asynchronous server-socket channel cannot be opened!");
			}
		} catch (Exception ex) {
			System.err.println(ex);
		}

    } 
}  