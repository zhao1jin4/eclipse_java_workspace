package jms2.async;

import java.util.concurrent.CountDownLatch;

import javax.jms.CompletionListener;
import javax.jms.Message;

class SampleCompletionListener implements CompletionListener {  
 
   CountDownLatch latch;  
 
   Exception exception;  
     
   public SampleCompletionListener(CountDownLatch latch) {  
      this.latch=latch;  
   }  
  
   @Override  
   public void onCompletion(Message message) {  
      latch.countDown();  
   }  
  
   @Override  
   public void onException(Message message, Exception exception) {  
      latch.countDown();  
      this.exception = exception;  
   }  
  
   public Exception getException(){  
      return exception;  
   }  
}  