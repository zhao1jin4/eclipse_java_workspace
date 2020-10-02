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
		
    	
//		stateMachine.getExtendedState().getVariables().put("myChangeTime", new Date());//发送事件前的传递的变量
//		stateMachine.sendEvent(Events.PAY);
//		Object myChangeTime= stateMachine.getExtendedState().getVariables().get("myChangeTime");//发送事件后的变量还存在
//		System.out.println("发送事件后的变量值:"+myChangeTime);
//		stateMachine.sendEvent(Events.RECEIVE);
	}

}
