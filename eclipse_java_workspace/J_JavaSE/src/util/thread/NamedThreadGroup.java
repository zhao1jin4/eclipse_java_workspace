package util.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * ���̹߳�����
 */
public class NamedThreadGroup extends ThreadGroup
{
	private String logContext="--�̹߳���--";
	private static final Logger logger= LoggerFactory.getLogger("service");
	
	private String groupName;
	public NamedThreadGroup(String name) {
		super(name);
		this.groupName=name;
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		//super.uncaughtException(t, e);
		logger.error("���߳���:{} �е��߳� {}  ִ��ʧ�� " , groupName, t.getName(),e );
		//System.err.println("���߳���:"+groupName+" �е��߳� "+t.getName()+" ִ��ʧ��,ԭ��Ϊ"+e.getMessage()  );
	}
	
}