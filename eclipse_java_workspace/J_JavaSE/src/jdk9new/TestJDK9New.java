package jdk9new;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class TestJDK9New {

	public static void main(String[] args) throws Exception { 
 
		System.out.println(10/3);
		System.out.println(10.0/3);
		
 
//		JDK9 中  
//		包javax.jws 		          在 java.xml.ws 模块 下
//		包javax.annotation 		          在java.xml.ws.annotation 模块 下 JDK 10 中 
//		module J_JavaSE
//		 {
//			 requires java.xml.ws; 
//			 requires java.xml.ws.annotation; //JDK 10 中 
//		 }
		javax.jws.WebService web;
		javax.annotation.PostConstruct x; 
		javax.annotation.Resource y;
		
		FileInputStream resource1 = new FileInputStream("c:/tmp/input.txt"); 
		FileInputStream resource2 = new FileInputStream("c:/tmp/input2.txt"); 
 
		try (resource1;resource2){
			
		} catch (FileNotFoundException e) {
			 
			e.printStackTrace();
		}
		resource1.read();//流在这已经关闭
		  
		//HTTP 2.0 
		//G1 default
 
 
		Process process =Runtime.getRuntime().exec("cmd /c dir c:/ ");
		java.lang.ProcessHandle handle=process.toHandle(); //ProcessHandle JDK9 new 
		CompletableFuture<ProcessHandle> future=handle.onExit();
		future.get();
		
		Set<String> alphabet = Set.of("a", "b", "c");
		
		//Nashorn  ECMAScript
 
		
	}
	//jdeprscan 命令
//	@Deprecated(since="9", forRemoval=true)
	public static void oldMethod()
	{
		
	}
	class EventHandler {
        volatile boolean eventNotificationNotReceived;
        void waitForEventAndHandleIt() {
            while ( eventNotificationNotReceived ) {
//                java.lang.Thread.onSpinWait();  //JDK9 New
            }
            readAndProcessEvent();
        }

        void readAndProcessEvent() {
            // Read event from some source and process it
        }
    }
}
