package jmx_examples.security_simple;

import java.util.HashMap;
import javax.management.Attribute;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class Client {

    public static void main(String[] args) {
        try {
            // Environment map
            //
            System.out.println("\nInitialize the environment map");
            HashMap env = new HashMap();

            // Provide the credentials required by the server to successfully
            // perform user authentication
            //
            String[] credentials = new String[] { "username" , "password" };//是已经在服务端有的用户密码
            env.put("jmx.remote.credentials", credentials);//JMXConnector.CREDENTIALS=jmx.remote.credentials

            
            String root="D:/program/eclipse_java_workspace/J_JavaSE/src/jmx_examples/security_simple/self_command_file";
            System.setProperty ("javax.net.ssl.trustStore", root+"/truststore");//使用keytool -import 生成的文件,相当于 java -Dx=y
            System.setProperty ("javax.net.ssl.trustStorePassword", "trustword");//仿问truststore文件的密码
            		  
            		  
            // Create an RMI connector client and
            // connect it to the RMI connector server
            //
            System.out.println("\nCreate an RMI connector client and " +   "connect it to the RMI connector server");
            JMXServiceURL url = new JMXServiceURL( "service:jmx:rmi:///jndi/rmi://localhost:9999/server");
          
            //connect时提示要配置log4j为(org.jboss.logging)
          //org.apache.log4j.PropertyConfigurator.configure("D:/Program/eclipse_java_workspace/J_JavaSE/src/jmx_examples/connector/log4j.properties");//OK
          org.apache.log4j.xml.DOMConfigurator.configure("D:/Program/eclipse_java_workspace/J_JavaSE/src/jmx_examples/connector/log4j.xml");//OK
             
            JMXConnector jmxc = JMXConnectorFactory.connect(url, env);//这里总是过不去????

            // Get an MBeanServerConnection
            //
            System.out.println("\nGet an MBeanServerConnection");
            MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

            // Get domains from MBeanServer
            //
            System.out.println("\nDomains:");
            String domains[] = mbsc.getDomains();
            for (int i = 0; i < domains.length; i++) {
                System.out.println("\tDomain[" + i + "] = " + domains[i]);
            }

            // Create SimpleStandard MBean
            //
            ObjectName mbeanName = new ObjectName("MBeans:type=SimpleStandard");
            System.out.println("\nCreate SimpleStandard MBean...");
            mbsc.createMBean("SimpleStandard", mbeanName, null, null);

            // Get MBean count
            //
            System.out.println("\nMBean count = " + mbsc.getMBeanCount());

            // Get State attribute
            //
            System.out.println("\nState = " +
                               mbsc.getAttribute(mbeanName, "State"));

            // Set State attribute
            //
            mbsc.setAttribute(mbeanName,
                              new Attribute("State", "changed state"));

            // Get State attribute
            //
            // Another way of interacting with a given MBean is through a
            // dedicated proxy instead of going directly through the MBean
            // server connection
            //
            SimpleStandardMBean proxy = JMX.newMBeanProxy(
                    mbsc, mbeanName, SimpleStandardMBean.class);
            System.out.println("\nState = " + proxy.getState());

            // Add notification listener on SimpleStandard MBean
            //
            ClientListener listener = new ClientListener();
            System.out.println("\nAdd notification listener...");
            mbsc.addNotificationListener(mbeanName, listener, null, null);

            // Invoke "reset" in SimpleStandard MBean
            //
            // Calling "reset" makes the SimpleStandard MBean emit a
            // notification that will be received by the registered
            // ClientListener.
            //
            System.out.println("\nInvoke reset() in SimpleStandard MBean...");
            mbsc.invoke(mbeanName, "reset", null, null);

            // Sleep for 2 seconds in order to have time to receive the
            // notification before removing the notification listener.
            //
            System.out.println("\nWaiting for notification...");
            Thread.sleep(2000);

            // Remove notification listener on SimpleStandard MBean
            //
            System.out.println("\nRemove notification listener...");
            mbsc.removeNotificationListener(mbeanName, listener);

            // Unregister SimpleStandard MBean
            //
            System.out.println("\nUnregister SimpleStandard MBean...");
            mbsc.unregisterMBean(mbeanName);

            // Close MBeanServer connection
            //
            System.out.println("\nClose the connection to the server");
            jmxc.close();
            System.out.println("\nBye! Bye!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
