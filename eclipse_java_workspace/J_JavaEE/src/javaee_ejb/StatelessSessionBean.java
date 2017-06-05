
package javaee_ejb;

import java.util.Date;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;

@Stateless
public class StatelessSessionBean implements StatelessSession
{
	static int count=0;
    public String hello() {
        return "hello, world!\n";
    }
    @Schedule(second="*/3", minute="*", hour="*", info="Automatic Timer Test")
    public void test_automatic_timer(Timer t)
    {
    	System.out.println("Canceling timer " + t.getInfo() + " at " + new Date());
    	if(count++>10)
    	{
    		 t.cancel();
             System.out.println("Done");
    	}
    }
}
