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
		
 
//		JDK9 ��  
//		��javax.jws 		          �� java.xml.ws ģ�� ��
//		��javax.annotation 		          ��java.xml.ws.annotation ģ�� �� JDK 10 �� 
//		module J_JavaSE
//		 {
//			 requires java.xml.ws; 
//			 requires java.xml.ws.annotation; //JDK 10 �� 
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
		resource1.read();//�������Ѿ��ر�
		  
		//HTTP 2.0 
		//G1 default
 
 
		Process process =Runtime.getRuntime().exec("cmd /c dir c:/ ");
		java.lang.ProcessHandle handle=process.toHandle(); //ProcessHandle JDK9 new 
		CompletableFuture<ProcessHandle> future=handle.onExit();
		future.get();
		
		Set<String> alphabet = Set.of("a", "b", "c");
		
		//Nashorn  ECMAScript
 
		
	}
	//jdeprscan ����
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
