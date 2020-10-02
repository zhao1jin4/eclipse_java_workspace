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
// Persister 未实现
@Configuration
@EnableStateMachine
public class PersistenceConfig {
    
	@Bean // Persister 未实现
    public StateMachinePersister<States,Events,StateEntity> persistence()
    {
    	StateMachinePersist<States,Events,StateEntity> p=new StateMachinePersist<States,Events,StateEntity>() {
			@Override
			public StateMachineContext<States, Events> read(StateEntity entity) throws Exception {
				//从某个位置（数据库）读状态机上下文数据做返回
				return entity.getContext();
			}
			@Override
			public void write(StateMachineContext<States, Events> context, StateEntity entity) throws Exception {
				// 状态机上下文数据  写到某个位置（数据库） 
				entity.setContext(context);
			}
    	};
    	return new DefaultStateMachinePersister<States,Events,StateEntity>(p);
    }
    
     
}
