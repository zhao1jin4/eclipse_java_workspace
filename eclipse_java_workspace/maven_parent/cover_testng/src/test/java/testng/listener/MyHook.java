package testng.listener;

import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;

public class MyHook implements IHookable {
  public void run(final IHookCallBack icb, ITestResult testResult) {
    // Preferably initialized in a @Configuration method
	  /*
    mySubject = authenticateWithJAAs();
    
    Subject.doAs(mySubject, new PrivilegedExceptionAction() {
      public Object run() {
        icb.callback(testResult);
      }
    };
    */
  }
}