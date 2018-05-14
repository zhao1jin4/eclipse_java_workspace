package apache_jmeter_rocketmq;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerClient;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;  

public class JMeterRocketMQProducter  implements JavaSamplerClient
{  
	//apache-jmeter-4.0\lib\ext\ApacheJMeter_java.jar  ApacheJMeter_core.jar
	//rocketmq-client-4.2.0.jar   rocketmq-common-4.2.0.jar   rocketmq-remoting-4.2.0.jar
	//netty-all-4.1.8.Final.jar
	String mqGroup = "myGroup";
	String mqIP = "127.0.0.1:9876";
	String mqTopic = "myTopic";
	String mqTag = "myTag";
	String keyPrefix="mykey";
	String msgPrefix="hello –°¿Ó";
	int times=15;
	String appName="app-1";
	SampleResult sr = new SampleResult();  
	DefaultMQProducer producer=null;
	
	boolean isJMeter=false;
	
//	@Override
	public Arguments getDefaultParameters()
	{
		 //io.netty.util.concurrent.GenericFutureListener x;
		System.out.println("getDefaultParameters");
		Arguments args=new Arguments();
		args.addArgument("group", mqGroup);
		args.addArgument("ipPort", mqIP);
		args.addArgument("topic", mqTopic);
		args.addArgument("tag", mqTag);
		args.addArgument("keyPrefix",keyPrefix);
		args.addArgument("msgPrefix", msgPrefix);
		args.addArgument("times",Integer.toString(times));
		return args;
	}


//	@Override
	public void setupTest(JavaSamplerContext context) 
	{
		System.out.println("setupTest isJMeter="+isJMeter);
		if(isJMeter)
		{
			this.mqGroup=context.getParameter("group");
			this.mqIP=context.getParameter("ipPort");
			this.mqTopic=context.getParameter("topic");
			this.mqTag=context.getParameter("tag");
			this.keyPrefix=context.getParameter("keyPrefix");
			this.msgPrefix=context.getParameter("msgPrefix");
			System.out.println("setupTest parameter group="+ context.getParameter("group"));
			
			sr.setSampleLabel("RocketMQ≤‚ ‘");  
		    sr.sampleStart();  
		}
	    producer = new DefaultMQProducer(mqGroup);  
        producer.setNamesrvAddr(mqIP);  
        producer.setInstanceName(appName);  
        producer.setVipChannelEnabled(false);  
        try {
			producer.start();
		} catch (MQClientException e) {
			e.printStackTrace();
		} 
	}

//	@Override
	public void teardownTest(JavaSamplerContext context)
	{
		System.out.println("teardownTest");
		producer.shutdown();
		if(isJMeter)
		{
		   sr.sampleEnd();
		}
	}
	
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) 
    {  
        try 
        {  
            for(int i=0;i<times;++i)
            {  
                Message msg = new Message(mqTopic, mqTag, keyPrefix+i, (msgPrefix+i).getBytes(Charset.forName("UTF-8")));  
                SendResult sendResult = producer.send(msg);  
                if(sendResult ==null || sendResult.getSendStatus() != SendStatus.SEND_OK){  
                    System.err.println(sendResult);  
                }
                System.out.println("send msg times="+i);
            }  
            if(isJMeter)
			{
	            sr.setResponseData("success","utf-8");  
	            sr.setDataType(SampleResult.TEXT);  
	            sr.setSuccessful(true);
			}
        }catch(Exception e)
        {  
            e.printStackTrace(); 
            StringWriter strWriter=  new StringWriter();
            e.printStackTrace(new PrintWriter(strWriter)); 
            System.err.println(strWriter.toString());
            if(isJMeter)
			{
	            sr.setSuccessful(false);
	            sr.setResponseData(strWriter.toString(),"utf-8");  
			}
        }  
        return sr;  
    }
    public static void main(String[] args) 
    {
    	JMeterRocketMQProducter p=new JMeterRocketMQProducter();
    	p.setupTest(null);
    	p.runTest(null);
    	p.teardownTest(null);
	}
  }