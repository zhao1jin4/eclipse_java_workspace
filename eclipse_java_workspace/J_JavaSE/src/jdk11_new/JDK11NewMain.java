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
		//javax.jws.WebService web;//JDK 11û������� 
		//ɾjava.xml.ws , java.xml.bind  ,java.xml.ws.annotation 
		//ɾ���� wsimport,wsgen
		//ɾJava Mission Control (JMC) ��JavaFx
		//���Ƽ���  Nashorn JavaScript Engine 
		
		
		// -XX:+UseDynamicNumberOfCompilerThreads
		//-XX:UseDynamicNumberOfGC Ĭ������
		//-XX:ParallelRefProcEnabled  Ĭ������
	
		//Epsilon GC ( No-Op) ʵ��׶� ,������������     �����������ܲ��ԡ��ڴ�ѹ�����Եȣ�Epsilon GC������Ϊ���������������������ܵĶ�����
		//ZGC  ʵ��׶� ֻ��linux X64 ��Ч��, ÿ��GC��ͣ��ʱ�䲻����10MS  -XX:+UseZGC

		// Transport Layer Security (TLS) 1.3
		Cipher clipher= Cipher.getInstance("ChaCha20");
		httpClient();
		java.lang.invoke.MethodHandles.lookup ();
		JDK11NewMain.class.getNestHost();
		JDK11NewMain.class.getNestMembers();
		
		//var����������ΪLambda���ʽ�ľֲ�����������
	
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
