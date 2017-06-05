package jmx_examples.mxbean;

import java.lang.management.ManagementFactory;

import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;

public class MainClient_Fail {
	public static void directly() throws Exception 
	{
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = new ObjectName( "com.example.mxbeans:type=QueueSampler");
		CompositeData queueSample = (CompositeData) mbs.getAttribute(name, "QueueSample");
		//找不到???,可能是MBeanServer 得到的方式有问题,可能要连接才行,jconsole是这样的
		int size = (Integer) queueSample.get("size");
		System.out.println("size");
	}
	public static void proxy() throws Exception 
	{
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = new ObjectName("com.example.mxbeans:type=QueueSample"); 
		QueueSamplerMXBean123 proxy = JMX.newMXBeanProxy(mbs, name,  
				QueueSamplerMXBean123.class); 
		QueueSample queueSample = proxy.getQueueSample(); 
		//报错????
		int size = queueSample.getSize(); 
		System.out.println("size");
	}
	public static void main(String[] args) throws Exception 
	{
		//directly() ;
		proxy() ;
	}
}
