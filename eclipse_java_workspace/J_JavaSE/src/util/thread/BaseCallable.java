package util.thread;


import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseCallable<T> implements Callable<T>{
	private String logContext="--线程工具--";
	private static final Logger logger= LoggerFactory.getLogger("service");
	
	public abstract T doCall();
	public T call()  {
		T res =null;
		try{
			logger.info(logContext+"{}开始处理",Thread.currentThread().getName());
			res = doCall () ;//不能调用super.call();//因为没有方法实现
			logger.info(logContext+"{}处理完成",Thread.currentThread().getName());
		}catch(Exception ex)
		{
			logger.error(logContext+"错误!!!",ex);
		}
		return res;
	}
}

