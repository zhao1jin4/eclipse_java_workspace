package timer_task;

import java.util.Timer;
import java.util.TimerTask;

public class MyTimer {

	public static void main(String[] args)
	{
		
		Timer timer=new Timer();
		timer.schedule(new TimerTask(){
			public void run() {
				System.out.println("��ʼ ��ʱ�������");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("���� ��ʱ�������");
			}
		}, 3000,2000);//delay��һ�ο�ʼ��ʱ��,period�Ժ�ÿ�������ִ��ʱ��
		//���̲߳��������˳�,����һֱ������,���ӵ��� Qutarz
		
		System.out.println("���߳����һ�д���");
	}

}
