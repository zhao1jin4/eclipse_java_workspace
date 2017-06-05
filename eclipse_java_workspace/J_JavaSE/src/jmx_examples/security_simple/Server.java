package jmx_examples.security_simple;

import java.io.File;
import java.util.HashMap;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.remote.JMXAuthenticator;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.rmi.RMIConnectorServer;
import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.rmi.ssl.SslRMIServerSocketFactory;
import javax.security.auth.Subject;

public class Server {

    public static void main(String[] args) {
        try {
            // Instantiate the MBean server
            //
            System.out.println("\nCreate the MBean server");
            MBeanServer mbs = MBeanServerFactory.createMBeanServer();

            // Environment map
            //
            System.out.println("\nInitialize the environment map");
            HashMap env = new HashMap();

            // Provide SSL-based RMI socket factories.
            //
            SslRMIClientSocketFactory csf = new SslRMIClientSocketFactory();
            SslRMIServerSocketFactory ssf = new SslRMIServerSocketFactory();
            env.put(RMIConnectorServer.RMI_CLIENT_SOCKET_FACTORY_ATTRIBUTE,csf);
            env.put(RMIConnectorServer.RMI_SERVER_SOCKET_FACTORY_ATTRIBUTE,ssf);

            // Provide the password file used by the connector server to
            // perform user authentication. The password file is a properties
            // based text file specifying username/password pairs. This
            // properties based password authenticator has been implemented
            // using the JMXAuthenticator interface and is passed to the
            // connector through the "jmx.remote.authenticator" property
            // in the map.
            //
            // This property is implementation-dependent and might not be
            // supported by all implementations of the JMX Remote API.
            //
            
            String root="D:/program/eclipse_java_workspace/J_JavaSE/src/jmx_examples/security_simple/self_command_file";
            env.put("jmx.remote.x.password.file",root+"/password.properties");
            
            //对于jmx.remote.x.password.file的另一种方法是,,未测试????
            //env.put( JMXConnectorServer.AUTHENTICATOR, JMXAuthenticator的实现);//AUTHENTICATOR="jmx.remote.authenticator" 
//            env.put( JMXConnectorServer.AUTHENTICATOR, new JMXAuthenticator(){
//				public Subject authenticate(Object credentials) {
//					System.out.println(credentials);
//					return null;
//				}
//            });
            
            
            
        	System.setProperty ("javax.net.ssl.keyStore", root+"/keystore");//使用keytool -genkey生成的文件,相当于 java -Dx=y
        	System.setProperty ("javax.net.ssl.keyStorePassword", "password");//仿问keystore文件的密码

            
            
            
            // Provide the access level file used by the connector server to
            // perform user authorization. The access level file is a properties
            // based text file specifying username/access level pairs where
            // access level is either "readonly" or "readwrite" access to the
            // MBeanServer operations. This properties based access control
            // checker has been implemented using the MBeanServerForwarder
            // interface which wraps the real MBean server inside an access
            // controller MBean server which performs the access control checks
            // before forwarding the requests to the real MBean server.
            //
            // This property is implementation-dependent and might not be
            // supported by all implementations of the JMX Remote API.
            //
            env.put("jmx.remote.x.access.file",root+"/access.properties");

            // Create an RMI connector server
            //
            System.out.println("\nCreate an RMI connector server");
            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/server");
            JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(url, env, mbs);

            // Start the RMI connector server
            //
            System.out.println("\nStart the RMI connector server");
            //之前要rmiregistry 9999,最好在正确的classpath下
            cs.start();
            System.out.println("\nRMI connector server successfully started");
            System.out.println("\nWaiting for incoming connections...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
