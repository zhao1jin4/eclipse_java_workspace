package statemachin_proj2;

import java.util.EnumSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Actions;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.transition.Transition;

import jdk.nashorn.internal.runtime.Context;

@Configuration
@EnableStateMachineFactory(name="stateMachineFactory")//������Ϳ��Բ���@EnableStateMachine
public class StateMachineFactoryConfig extends EnumStateMachineConfigurerAdapter<States, Events> {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
            .withStates()
                .initial(States.UNPAID)   
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
            .withExternal()
                .source(States.UNPAID).target(States.WAITING_FOR_RECEIVE)
                .event(Events.PAY)
                .action(Actions.errorCallingAction(context->{
                	Object param=context.getExtendedState().getVariables().get("myChangeTime");
                	System.out.println("״̬���յ��Ĳ���:"+param);
                }, context->{
                	System.err.println(Context.getCurrentErr());
                }))
                .and()
            .withExternal()
                .source(States.WAITING_FOR_RECEIVE).target(States.DONE)
                .event(Events.RECEIVE);
    }

    @Override //������Ͳ���@WithStateMachine ��������
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config
            .withConfiguration()
            .autoStartup(true).machineId("myStateMachine01") //�ɶ�ӵĲ���
                .listener(listener());
    }
    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {

            @Override
            public void transition(Transition<States, Events> transition) {
                if(transition.getTarget().getId() == States.UNPAID) {
                    logger.info("������������֧��");
                    return;
                }
                if(transition.getSource().getId() == States.UNPAID
                        && transition.getTarget().getId() == States.WAITING_FOR_RECEIVE) {
                    logger.info("�û����֧�������ջ�");
                    return;
                }
                if(transition.getSource().getId() == States.WAITING_FOR_RECEIVE
                        && transition.getTarget().getId() == States.DONE) {
                    logger.info("�û����ջ����������");
                    return;
                }
            }
        };
    }
  
}