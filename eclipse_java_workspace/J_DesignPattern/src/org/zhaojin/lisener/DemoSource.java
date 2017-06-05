package org.zhaojin.lisener;

import java.util.Enumeration;
import java.util.Vector;

public class DemoSource {   
    private Vector repository = new Vector();//监听自己的监听器队列   
    public DemoSource(){}   
    public void addDemoListener(DemoListener dl) {   
           repository.addElement(dl);   
    }   
    public void notifyDemoEvent() {//通知所有的监听器   
    	 Enumeration  e = repository.elements();   
           while(e.hasMoreElements()) 
           {   
                   DemoListener dl = (DemoListener)e.nextElement();   
                 dl.handleEvent(new DemoEvent(this));   
           }   
    }   
}  
