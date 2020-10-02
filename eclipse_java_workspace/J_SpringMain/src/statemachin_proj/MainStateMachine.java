package statemachin_proj;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.statemachine.StateMachine;

public class MainStateMachine {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(StateMachineConfig.class);
		StateMachine<States, Events> stateMachine=context.getBean(StateMachine.class);
		stateMachine.start();//��ʼdraft״̬
	    boolean isOK= stateMachine.sendEvent(Events.ONLINE);//�����¼�,�����״̬�仯configure������,�ᴥ��@OnTransition(target =XX) ���ں���
	    stateMachine.sendEvent(Events.PUBLISH);//��ע�����У�״̬�л�Υ��,����false
	    isOK=stateMachine.sendEvent(Events.ROLLBACK);
	    System.out.println(isOK);
	}

}

