
package javaee_event;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
//import javax.enterprise.event.Event;
import javax.inject.Inject;

@Stateless
@LocalBean
public class PrintProducer
{
//    @Inject Event<PrintEvent> printEvent;
//    @Inject @BindIt Event<PrintEvent> printAndBindEvent;    
    public void print(int pages) {
//        PrintEvent event = new PrintEvent(pages);
//        if (pages < 10)
//            printEvent.fire(event);
//        else
//            printAndBindEvent.fire(event);
    }
   
}

