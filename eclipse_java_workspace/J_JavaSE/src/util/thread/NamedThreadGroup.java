package util.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 简单线程工具类
 */
public class NamedThreadGroup extends ThreadGroup
{
	private String logContext="--线程工具--";
	private static final Logger logger= LoggerFactory.getLogger("service");
	
	private String groupName;
	public NamedThreadGroup(String name) {
		super(name);
		this.groupName=name;
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		//super.uncaughtException(t, e);
		logger.error("在线程组:{} 中的线程 {}  执行失败 " , groupName, t.getName(),e );
		//System.err.println("在线程组:"+groupName+" 中的线程 "+t.getName()+" 执行失败,原因为"+e.getMessage()  );
	}
	
}