package timer_task;

import java.util.Timer;
import java.util.TimerTask;

public class TimerSchedule 
{
	public static void main(String[] args) 
	{
		Timer t=new Timer();
		
		TimerSchedule x=new TimerSchedule();
		MyTimerTask m =x.new MyTimerTask();//�ڲ���ʵ����
		
		t.schedule(m ,1000*2);
		//t.scheduleAtFixedRate(task, firstTime, period)
		//����һֱ������
		
	}
	
	class MyTimerTask extends TimerTask
	{
		public void run()
		{
			System.out.println("����������");
		}
	}

}
