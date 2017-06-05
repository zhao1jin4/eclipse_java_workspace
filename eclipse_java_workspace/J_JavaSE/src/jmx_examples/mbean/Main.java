/* Main.java - main class for Hello World example.  Create the
   HelloWorld MBean, register it, then wait forever (or until the
   program is interrupted).  */

package jmx_examples.mbean;

import java.lang.management.*;
import javax.management.*;

public class Main {
    /* For simplicity, we declare "throws Exception".  Real programs
       will usually want finer-grained exception handling.  */
    public static void main(String[] args) throws Exception {
	// Get the Platform MBean Server
	MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
	//���û�л��Զ� ����  MBeanServerFactory.createMBeanServer()������
	// Construct the ObjectName for the MBean we will register
	
	ObjectName name = new ObjectName("com.example.mbeans12312:type=ABC");
	//ObjectName name = new ObjectName("com.example.mbeans:type=Hello");//:ǰ��Ĳ�����jconsole����ʾ���ļ�����(��domain),type��key-properties,Hello����ʾ���ļ����µ�����
	//ObjectName��API��key ��˵��,��immutable��,domain�в�����"//", 

	//���е�public����(getter)�ͷ�������jconsole�п���,�����public����(setter)�ͷ���,jconsole�п����޸�����ֵ�͵��÷���

	
	// Create the Hello World MBean
	Hello mbean = new Hello();

	// Register the Hello World MBean
	mbs.registerMBean(mbean, name);

	// Wait forever
	System.out.println("Waiting forever...");
	Thread.sleep(Long.MAX_VALUE);
    }
}
