package statemachin_persistence;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;

public class MainPersister {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(PersistenceConfig.class);
		StateMachine<States, Events> stateMachine = context.getBean(StateMachine.class);
		
		stateMachine.start();
		
		StateMachinePersister persister=null;
//    	persister.persist(stateMachine, t);
//    	persister.restore(stateMachine, t);
		
    	
//		stateMachine.getExtendedState().getVariables().put("myChangeTime", new Date());//�����¼�ǰ�Ĵ��ݵı���
//		stateMachine.sendEvent(Events.PAY);
//		Object myChangeTime= stateMachine.getExtendedState().getVariables().get("myChangeTime");//�����¼���ı���������
//		System.out.println("�����¼���ı���ֵ:"+myChangeTime);
//		stateMachine.sendEvent(Events.RECEIVE);
	}

}
