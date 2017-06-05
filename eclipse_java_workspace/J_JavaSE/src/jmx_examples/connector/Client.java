package jmx_examples.connector;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import javax.management.Attribute;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.log4j.Logger;

public class Client {

    public static void main(String[] args) {
        try {
            // Create an RMI connector client and
            // connect it to the RMI connector server
            //
            echo("\nCreate an RMI connector client and " +
		 "connect it to the RMI connector server");
            
            //JMXServiceURL url = new JMXServiceURL( "service:jmx:rmi:///jndi/rmi://localhost:9999/server");//RMI OK
            JMXServiceURL url = new JMXServiceURL( "service:jmx:iiop:///jndi/iiop://localhost:7777/server");//CORBA OK
            //CORBA时client端提示要配置log4j为(org.jboss.logging)
            //org.apache.log4j.PropertyConfigurator.configure("D:/Program/eclipse_java_workspace/J_JavaSE/src/jmx_examples/connector/log4j.properties");//OK
            org.apache.log4j.xml.DOMConfigurator.configure("D:/Program/eclipse_java_workspace/J_JavaSE/src/jmx_examples/connector/log4j.xml");//OK
            
            JMXConnector jmxc = JMXConnectorFactory.connect(url, null);

	    // Create listener
	    //
            ClientListener listener = new ClientListener();

            // Get an MBeanServerConnection
            //
            echo("\nGet an MBeanServerConnection");
            MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
	    waitForEnterPressed();

            // Get domains from MBeanServer
            //
            echo("\nDomains:");
            String domains[] = mbsc.getDomains();//有 JMImplementation 和 efaultDomain
            for (int i = 0; i < domains.length; i++) {
                echo("\tDomain[" + i + "] = " + domains[i]);
            }
	    waitForEnterPressed();

	    // Get MBeanServer's default domain
	    //
	    String domain = mbsc.getDefaultDomain();

            // Create SimpleStandard MBean
            //
            ObjectName stdMBeanName =
		new ObjectName(domain +":type=SimpleStandard,name=2");//name变为2了,name提供一个相同type的另一个标识,为createMBean()
            echo("\nCreate SimpleStandard MBean...");
            mbsc.createMBean("jmx_examples.connector.SimpleStandard", stdMBeanName, null, null);//修改为全类名,后两个参数 Object[] params, String[] signature
	    waitForEnterPressed();

            // Create SimpleDynamic MBean
            //
            ObjectName dynMBeanName =
		new ObjectName(domain +":type=SimpleDynamic,name=2");
            echo("\nCreate SimpleDynamic MBean...");
            mbsc.createMBean("jmx_examples.connector.SimpleDynamic", dynMBeanName, null, null);//修改为全类名
	    waitForEnterPressed();

            // Get MBean count
            //
            echo("\nMBean count = " + mbsc.getMBeanCount());//如是5,除了createMBean两个,其它的是jconsole中可以看到已有的

	    // Query MBean names
	    //
            echo("\nQuery MBeanServer MBeans:");
	    Set names = mbsc.queryNames(null, null);
	    for (Iterator i = names.iterator(); i.hasNext(); ) {
		echo("\tObjectName = " + (ObjectName) i.next());//多了一个JMImplementation:type=MBeanServerDelegate
	    }
	    waitForEnterPressed();

	    // -------------------------------
	    // Manage the SimpleStandard MBean
	    // -------------------------------
            echo("\n>>> Perform operations on SimpleStandard MBean <<<");

            // Get State attribute in SimpleStandard MBean
            //
            echo("\nState = " + mbsc.getAttribute(stdMBeanName, "State"));//和JMX.newMBeanProxy效果一样

            // Set State attribute in SimpleStandard MBean
            //
            mbsc.setAttribute(stdMBeanName,
                              new Attribute("State", "changed state"));

            // Get State attribute in SimpleStandard MBean
	    //
	    // Another way of interacting with a given MBean is through a
	    // dedicated proxy instead of going directly through the MBean
	    // server connection
	    //
	    SimpleStandardMBean proxy = JMX.newMBeanProxy(mbsc, stdMBeanName, SimpleStandardMBean.class, true);//和mbsc.getAttribute效果一样
            echo("\nState = " + proxy.getState());

            // Add notification listener on SimpleStandard MBean
            //
            echo("\nAdd notification listener...");
            mbsc.addNotificationListener(stdMBeanName, listener, null, null);

            // Invoke "reset" in SimpleStandard MBean
            //
            // Calling "reset" makes the SimpleStandard MBean emit a
            // notification that will be received by the registered
            // ClientListener.
            //
            echo("\nInvoke reset() in SimpleStandard MBean...");
            mbsc.invoke(stdMBeanName, "reset", null, null);

            // Sleep for 2 seconds in order to have time to receive the
            // notification before removing the notification listener.
            //
            echo("\nWaiting for notification...");
            sleep(2000);

            // Remove notification listener on SimpleStandard MBean
            //
            echo("\nRemove notification listener...");
            mbsc.removeNotificationListener(stdMBeanName, listener);

            // Unregister SimpleStandard MBean
            //
            echo("\nUnregister SimpleStandard MBean...");
            mbsc.unregisterMBean(stdMBeanName);
	    waitForEnterPressed();

	    // ------------------------------
	    // Manage the SimpleDynamic MBean
	    // ------------------------------
            echo("\n>>> Perform operations on SimpleDynamic MBean <<<");

            // Get State attribute in SimpleDynamic MBean
            //
            echo("\nState = " + mbsc.getAttribute(dynMBeanName, "State"));

            // Set State attribute in SimpleDynamic MBean
            //
            mbsc.setAttribute(dynMBeanName,
                              new Attribute("State", "changed state"));

            // Get State attribute in SimpleDynamic MBean
            //
            echo("\nState = " +
                               mbsc.getAttribute(dynMBeanName, "State"));

            // Add notification listener on SimpleDynamic MBean
            //
            echo("\nAdd notification listener...");
            mbsc.addNotificationListener(dynMBeanName, listener, null, null);

            // Invoke "reset" in SimpleDynamic MBean
            //
            // Calling "reset" makes the SimpleDynamic MBean emit a
            // notification that will be received by the registered
            // ClientListener.
            //
            echo("\nInvoke reset() in SimpleDynamic MBean...");
            mbsc.invoke(dynMBeanName, "reset", null, null);

            // Sleep for 2 seconds in order to have time to receive the
            // notification before removing the notification listener.
            //
            echo("\nWaiting for notification...");
            sleep(2000);

            // Remove notification listener on SimpleDynamic MBean
            //
            echo("\nRemove notification listener...");
            mbsc.removeNotificationListener(dynMBeanName, listener);

            // Unregister SimpleDynamic MBean
            //
            echo("\nUnregister SimpleDynamic MBean...");
            mbsc.unregisterMBean(dynMBeanName);
	    waitForEnterPressed();

            // Close MBeanServer connection
            //
            echo("\nClose the connection to the server");
            jmxc.close();
            echo("\nBye! Bye!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void echo(String msg) {
	System.out.println(msg);
    }

    private static void sleep(int millis) {
	try {
	    Thread.sleep(millis);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }

    private static void waitForEnterPressed() {
	try {
	    echo("\nPress <Enter> to continue...");
	    System.in.read();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
