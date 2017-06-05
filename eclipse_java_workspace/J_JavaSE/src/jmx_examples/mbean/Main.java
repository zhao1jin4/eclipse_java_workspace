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
	//如果没有会自动 调用  MBeanServerFactory.createMBeanServer()来创建
	// Construct the ObjectName for the MBean we will register
	
	ObjectName name = new ObjectName("com.example.mbeans12312:type=ABC");
	//ObjectName name = new ObjectName("com.example.mbeans:type=Hello");//:前面的部分是jconsole中显示的文件夹名(叫domain),type叫key-properties,Hello是显示在文件夹下的名字
	//ObjectName的API有key 的说明,是immutable的,domain中不能有"//", 

	//所有的public属性(getter)和方法会在jconsole中看到,如果有public属性(setter)和方法,jconsole中可以修改属性值和调用方法

	
	// Create the Hello World MBean
	Hello mbean = new Hello();

	// Register the Hello World MBean
	mbs.registerMBean(mbean, name);

	// Wait forever
	System.out.println("Waiting forever...");
	Thread.sleep(Long.MAX_VALUE);
    }
}
