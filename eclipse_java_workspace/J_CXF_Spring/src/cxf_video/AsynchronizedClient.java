package cxf_video;

import java.io.StringReader;
import java.net.URL;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Response;
import javax.xml.ws.handler.MessageContext;

import org.xml.sax.InputSource;

public class AsynchronizedClient 
{
	
	public AsynchronizedClient(boolean isAsync)
	{
		try {
			
			String payload=
					"<ns1:sayHi xmlns:ns1='http://cxf_video/'>" +
						"<speak>张三</speak>"+
					"</ns1:sayHi>";
			//名称空间看WSDL
			QName serviceName=new QName("http://cxf_video/","HelloWorldImplService");//默认definitions名，看 ?wsdl
			javax.xml.ws.Service service=javax.xml.ws.Service.create(new URL("http://127.0.0.1:8080/HelloWorld?wsdl"),serviceName);
			QName portName =new QName("http://cxf_video/","HelloWorldImplPort");//默认Port名，看 ?wsdl
			Dispatch<Source> dispatch=service.createDispatch(portName,Source.class,javax.xml.ws.Service.Mode.PAYLOAD);//PAYLOAD只发SOAP中的body,MESSAGE发整个SOAP
			
			Map<String, Object> requestContext = dispatch.getRequestContext();
		    requestContext.put(MessageContext.HTTP_REQUEST_METHOD, "POST");
		        
			Source msg=new SAXSource(new InputSource(new StringReader(payload)));
			//方法一   将来(Futrue)  异步
			if(! isAsync)
			{
				Response<Source> responseSource= dispatch.invokeAsync(msg);
				//Source source=dispatch.invoke(msg);//可用同步 invoke(msg);
				
				System.out.println("开始同步等待Server 返回");		
				Source response=responseSource.get();//会等侍
				System.out.println("结束同步等待Server 返回 值：");	
				Transformer transformer=TransformerFactory.newInstance().newTransformer();
				transformer.transform(response, new StreamResult(System.out));
			}
			//方法二  回调异步
			else
			{
				dispatch.invokeAsync(msg, new AsyncHandler<Source>()//返回Furture
						{
							public void handleResponse(Response<Source> res)
							{
								try {
									Source response= res.get();
									Transformer transformer=TransformerFactory.newInstance().newTransformer();
									transformer.transform(response, new StreamResult(System.out));
								} 
								 catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
				System.out.println("异步等待Server 返回");			
				Thread.sleep(1000*10);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception  
	{
		boolean isAsync=false;
		new AsynchronizedClient(isAsync);
	}
}