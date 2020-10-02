package statemachin_persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

import statemachin_proj2.Events;
import statemachin_proj2.States;
// Persister δʵ��
@Configuration
@EnableStateMachine
public class PersistenceConfig {
    
	@Bean // Persister δʵ��
    public StateMachinePersister<States,Events,StateEntity> persistence()
    {
    	StateMachinePersist<States,Events,StateEntity> p=new StateMachinePersist<States,Events,StateEntity>() {
			@Override
			public StateMachineContext<States, Events> read(StateEntity entity) throws Exception {
				//��ĳ��λ�ã����ݿ⣩��״̬������������������
				return entity.getContext();
			}
			@Override
			public void write(StateMachineContext<States, Events> context, StateEntity entity) throws Exception {
				// ״̬������������  д��ĳ��λ�ã����ݿ⣩ 
				entity.setContext(context);
			}
    	};
    	return new DefaultStateMachinePersister<States,Events,StateEntity>(p);
    }
    
     
}
