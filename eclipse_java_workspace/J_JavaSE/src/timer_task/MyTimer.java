package timer_task;

import java.util.Timer;
import java.util.TimerTask;

public class MyTimer {

	public static void main(String[] args)
	{
		
		Timer timer=new Timer();
		timer.schedule(new TimerTask(){
			public void run() {
				System.out.println("开始 定时里的任务");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("结束 定时里的任务");
			}
		}, 3000,2000);//delay第一次开始延时间,period以后每次任务的执行时间
		//主线程不会立即退出,而是一直运行着,复杂的用 Qutarz
		
		System.out.println("主线程最后一行代码");
	}

}
