
 package javaee_event;

import javax.ejb.Singleton;
import javax.ejb.Startup;
/*
//import javax.enterprise.event.Observes;
//import javax.enterprise.inject.Default;
//glassfish中的weld-osgi-bundle.jar
@Singleton
@Startup
public class PrintObserver {
    
    public void onPrintAndBind(@Observes @BindIt PrintEvent event) {
        System.out.println("Printing and binding " + event.getPages() + " pages");
    }
    
    public void onPrint(@Observes @Default PrintEvent event) {
        System.out.println("Printing " + event.getPages() + " pages");
    }
}
 */