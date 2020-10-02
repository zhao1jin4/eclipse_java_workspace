package statemachin_proj;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.statemachine.StateMachine;

public class MainStateMachine {
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(StateMachineConfig.class);
		StateMachine<States, Events> stateMachine=context.getBean(StateMachine.class);
		stateMachine.start();//初始draft状态
	    boolean isOK= stateMachine.sendEvent(Events.ONLINE);//发送事件,会产生状态变化configure方法中,会触发@OnTransition(target =XX) 所在函数
	    stateMachine.sendEvent(Events.PUBLISH);//如注释这行，状态切换违法,返回false
	    isOK=stateMachine.sendEvent(Events.ROLLBACK);
	    System.out.println(isOK);
	}

}

