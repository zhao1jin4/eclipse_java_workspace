
package demo.hw.client;

import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import demo.hw.server.HelloWorld;
import demo.hw.server.User;
import demo.hw.server.UserImpl;

public final class Client
{

    private static final QName SERVICE_NAME 
        = new QName("http://server.hw.demo_1/", "HelloWorld_xx");//无所谓是什么,
    private static final QName PORT_NAME 
        = new QName("http://server.hw.demo/", "HelloWorldPort");  //PORT 不可以变,包名返写


    private Client() {
    } 

    public static void main(String args[]) throws Exception 
    {
        Service service = Service.create(SERVICE_NAME);
        // Endpoint Address
        String endpointAddress = "http://localhost:9000/helloWorld";

        // Add a port to the Service
        service.addPort(PORT_NAME, SOAPBinding.SOAP11HTTP_BINDING, endpointAddress);
        
        HelloWorld hw = service.getPort(HelloWorld.class);
        System.out.println(hw.sayHi("World"));

        User user = new UserImpl("World");
        System.out.println(hw.sayHiToUser(user));

        //say hi to some more users to fill up the map a bit
        user = new UserImpl("Galaxy");
        System.out.println(hw.sayHiToUser(user));

        user = new UserImpl("Universe");
        System.out.println(hw.sayHiToUser(user));

        System.out.println();
        System.out.println("Users: ");
        Map<Integer, User> users = hw.getUsers();
        for (Map.Entry<Integer, User> e : users.entrySet()) {
            System.out.println("  " + e.getKey() + ": " + e.getValue().getName());
        }

    }

}
