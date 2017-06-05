package timer_task;

import java.util.Timer;
import java.util.TimerTask;

public class TimerSchedule 
{
	public static void main(String[] args) 
	{
		Timer t=new Timer();
		
		TimerSchedule x=new TimerSchedule();
		MyTimerTask m =x.new MyTimerTask();//内部类实例化
		
		t.schedule(m ,1000*2);
		//t.scheduleAtFixedRate(task, firstTime, period)
		//最后会一直运行着
		
	}
	
	class MyTimerTask extends TimerTask
	{
		public void run()
		{
			System.out.println("任务运行了");
		}
	}

}
