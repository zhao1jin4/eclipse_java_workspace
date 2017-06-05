package cxf_video;


//@WebService  //这里可以没有
public class HelloWorldImpl implements HelloWorld {
  

    public String sayHi(String text) {

        try {
			Thread.sleep(1000*5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        System.out.println("sayHi called");
        return "Hello " + text;
    }
}
