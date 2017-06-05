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
		//�Ҳ���???,������MBeanServer �õ��ķ�ʽ������,����Ҫ���Ӳ���,jconsole��������
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
		//����????
		int size = queueSample.getSize(); 
		System.out.println("size");
	}
	public static void main(String[] args) throws Exception 
	{
		//directly() ;
		proxy() ;
	}
}
