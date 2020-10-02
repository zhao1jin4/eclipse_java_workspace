package statemachin_proj2;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

public class MainStateMachine {
	public static void main(String[] args) {
		//ApplicationContext context = new AnnotationConfigApplicationContext(StateMachineConfig.class);
		//StateMachine<States, Events> stateMachine = context.getBean(StateMachine.class);
		
		ApplicationContext context = new AnnotationConfigApplicationContext(StateMachineFactoryConfig.class);
		StateMachineFactory<States, Events> stateMachineFactory = context.getBean(StateMachineFactory.class);
		StateMachine<States, Events> stateMachine = stateMachineFactory.getStateMachine();
		
		
		stateMachine.start();
		
		stateMachine.getExtendedState().getVariables().put("myChangeTime", new Date());//�����¼�ǰ�Ĵ��ݵı���
		stateMachine.sendEvent(Events.PAY);
		Object myChangeTime= stateMachine.getExtendedState().getVariables().get("myChangeTime");//�����¼���ı���������
		System.out.println("�����¼���ı���ֵ:"+myChangeTime);
		
		
		stateMachine.sendEvent(Events.RECEIVE);
	}

}
