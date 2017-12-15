package util.thread;


import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseCallable<T> implements Callable<T>{
	private String logContext="--�̹߳���--";
	private static final Logger logger= LoggerFactory.getLogger("service");
	
	public abstract T doCall();
	public T call()  {
		T res =null;
		try{
			logger.info(logContext+"{}��ʼ����",Thread.currentThread().getName());
			res = doCall () ;//���ܵ���super.call();//��Ϊû�з���ʵ��
			logger.info(logContext+"{}�������",Thread.currentThread().getName());
		}catch(Exception ex)
		{
			logger.error(logContext+"����!!!",ex);
		}
		return res;
	}
}

