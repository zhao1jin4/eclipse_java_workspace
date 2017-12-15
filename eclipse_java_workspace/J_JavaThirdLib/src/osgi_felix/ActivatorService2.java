package osgi_felix;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceEvent;

import osgi_felix.service.DictionaryService;

//Bundle-Name: ActivatorService2 example
//Bundle-Description: A bundle that displays messages at startup and when service events occur
//Bundle-Vendor: Apache Felix
//Bundle-Version: 1.0.0
//Bundle-Activator: osgi_felix.ActivatorService2
//Import-Package: org.osgi.framework,osgi_felix.service

/**
 * This class implements a simple bundle that uses the bundle
 * context to register an French language dictionary service
 * with the OSGi framework. The dictionary service interface is
 * defined in a separate class file and is implemented by an
 * inner class. This class is identical to the class in
 * Example 2, except that the dictionary contains French words.
**/
public class ActivatorService2 implements BundleActivator
{
    /**
     * Implements BundleActivator.start(). Registers an
     * instance of a dictionary service using the bundle context;
     * attaches properties to the service that can be queried
     * when performing a service look-up.
     * @param context the framework context for the bundle.
    **/
    public void start(BundleContext context)
    {
        Hashtable<String, String> props = new Hashtable<String, String>();
        props.put("Language", "French");
        context.registerService(
            DictionaryService.class.getName(), new DictionaryImpl(), props);
    }

    /**
     * Implements BundleActivator.stop(). Does nothing since
     * the framework will automatically unregister any registered services.
     * @param context the framework context for the bundle.
    **/
    public void stop(BundleContext context)
    {
        // NOTE: The service is automatically unregistered.
    }

    /**
     * A private inner class that implements a dictionary service;
     * see DictionaryService for details of the service.
    **/
    private static class DictionaryImpl implements DictionaryService
    {
        // The set of words contained in the dictionary.
        String[] m_dictionary =
            { "bienvenue", "au", "tutoriel", "osgi" };

        /**
         * Implements DictionaryService.checkWord(). Determines
         * if the passed in word is contained in the dictionary.
         * @param word the word to be checked.
         * @return true if the word is in the dictionary,
         *         false otherwise.
        **/
        public boolean checkWord(String word)
        {
            word = word.toLowerCase();

            // This is very inefficient
            for (int i = 0; i < m_dictionary.length; i++)
            {
                if (m_dictionary[i].equals(word))
                {
                    return true;
                }
            }
            return false;
        }
    }
}