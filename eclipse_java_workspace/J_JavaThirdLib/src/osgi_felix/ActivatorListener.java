package osgi_felix;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceEvent;
//Bundle-Name: ActivatorListener example
//Bundle-Description: A bundle that displays messages at startup and when service events occur
//Bundle-Vendor: Apache Felix
//Bundle-Version: 1.0.0
//Bundle-Activator: osgi_felix.ActivatorListener
//Import-Package: org.osgi.framework
/**
 * This class implements a simple bundle that utilizes the OSGi
 * framework's event mechanism to listen for service events. Upon
 * receiving a service event, it prints out the event's details.
**/

//manifest.mf 文件最后一定要有一个空行(最后一行被忽略)
public class ActivatorListener implements BundleActivator, ServiceListener//实现ServiceListener接口也会监听其它bundler的registerService
{
    /**
     * Implements BundleActivator.start(). Prints
     * a message and adds itself to the bundle context as a service
     * listener.
     * @param context the framework context for the bundle.
    **/
    public void start(BundleContext context)
    {
        System.out.println("Starting to listen for service events.");
        context.addServiceListener(this);
    }

    /**
     * Implements BundleActivator.stop(). Prints
     * a message and removes itself from the bundle context as a
     * service listener.
     * @param context the framework context for the bundle.
    **/
    public void stop(BundleContext context) 
    {
        context.removeServiceListener(this);//可以不做,框架会自动做
        System.out.println("Stopped listening for service events.");

        // Note: It is not required that we remove the listener here,
        // since the framework will do it automatically anyway.
    }

    /**
     * Implements ServiceListener.serviceChanged().
     * Prints the details of any service event from the framework.
     * @param event the fired service event.
    **/
    public void serviceChanged(ServiceEvent event)
    {
        String[] objectClass = (String[])
            event.getServiceReference().getProperty("objectClass");//固定

        if (event.getType() == ServiceEvent.REGISTERED)//对应context.registerService(
        {
            System.out.println(
                "Ex1: Service of type " + objectClass[0] + " registered.");
        }
        else if (event.getType() == ServiceEvent.UNREGISTERING)
        {
            System.out.println(
                "Ex1: Service of type " + objectClass[0] + " unregistered.");
        }
        else if (event.getType() == ServiceEvent.MODIFIED)
        {
            System.out.println(
                "Ex1: Service of type " + objectClass[0] + " modified.");
        }
    }
}