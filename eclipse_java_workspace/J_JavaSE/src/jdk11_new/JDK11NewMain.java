package jdk11_new;

import java.io.FileNotFoundException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Paths;
import java.time.Duration;

import javax.crypto.Cipher;

public class JDK11NewMain {

	public static void main(String[] args) throws Exception {
		//javax.jws.WebService web;//JDK 11没有这个类 
		//删java.xml.ws , java.xml.bind  ,java.xml.ws.annotation 
		//删命令 wsimport,wsgen
		//删Java Mission Control (JMC) ，JavaFx
		//不推荐用  Nashorn JavaScript Engine 
		
		
		// -XX:+UseDynamicNumberOfCompilerThreads
		//-XX:UseDynamicNumberOfGC 默认启用
		//-XX:ParallelRefProcEnabled  默认启用
	
		//Epsilon GC ( No-Op) 实验阶段 ,不做垃圾回收     用来进行性能测试、内存压力测试等，Epsilon GC可以作为度量其他垃圾回收器性能的对照组
		//ZGC  实验阶段 只在linux X64 有效，, 每次GC的停顿时间不超过10MS  -XX:+UseZGC

		// Transport Layer Security (TLS) 1.3
		Cipher clipher= Cipher.getInstance("ChaCha20");
		httpClient();
		java.lang.invoke.MethodHandles.lookup ();
		JDK11NewMain.class.getNestHost();
		JDK11NewMain.class.getNestMembers();
		
		//var可以用来作为Lambda表达式的局部变量声明。
	
	}
	public static void httpClient() throws Exception
	{
		java.net.http.HttpClient httpClient;
		
		 HttpRequest request = HttpRequest.newBuilder()
		         .uri(URI.create("https://foo.com/"))
		         .timeout(Duration.ofMinutes(2))
		         .header("Content-Type", "application/json")
		         .POST(BodyPublishers.ofFile(Paths.get("file.json")))
		         .build();
		    
		HttpClient client = HttpClient.newBuilder()
			       .version(Version.HTTP_1_1)
			       .followRedirects(Redirect.NORMAL)
			       .connectTimeout(Duration.ofSeconds(20))
			       .proxy(ProxySelector.of(new InetSocketAddress("proxyIP", 80)))
			       .authenticator(Authenticator.getDefault())
			       .build();
		
	    client.sendAsync(request, BodyHandlers.ofString())
        .thenApply(HttpResponse::body)
        .thenAccept(System.out::println);  
	    
	    
	    HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
	    System.out.println(response.statusCode());
	    System.out.println(response.body()); 
	}
}
