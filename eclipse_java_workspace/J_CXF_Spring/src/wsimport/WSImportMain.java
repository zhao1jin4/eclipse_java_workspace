package wsimport;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
 

import wsimport.gen.HelloWorldImplService;
import wsimport.gen.User;

public class WSImportMain {
//	demo.hw.server.Server 
// wsimport -s c:/tmp/wsCode -p wsimport.gen -keep http://localhost:9000/helloWorld?wsdl -encoding utf8
 
	public static void main(String[] args) throws Exception {
		org.codehaus.stax2.ri.Stax2EventFactoryImpl x;//  Cannot create a secure XMLInputFactory 加  stax2-api-3.1.4.jar 和  woodstox-core-5.0.3.jar
		
		URL url=new URL("http://localhost:9000/helloWorld?wsdl");
		QName HELLOWORLDIMPLSERVICE_QNAME = new QName("http://server.hw.demo/", "HelloWorldImplService");
		HelloWorldImplService service=new  HelloWorldImplService(url,HELLOWORLDIMPLSERVICE_QNAME);
		User u=new User();
		u.setName("test生成代码");
		String res=service.getHelloWorldImplPort().sayHiToUser(u);
		System.out.println(res);
	}

}
