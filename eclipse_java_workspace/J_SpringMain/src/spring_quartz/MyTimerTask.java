package spring_quartz;

import java.util.TimerTask;

public class MyTimerTask extends TimerTask{

	@Override
	public void run() {
		System.out.println("in MyTimerTask ");
	}
}
