package statemachin_persistence;

import org.springframework.statemachine.StateMachineContext;

import statemachin_proj2.Events;
import statemachin_proj2.States;

//@Entity
//@Table(name="STATE_MACHINE")
public class StateEntity {	
//	@Id
//	private Long id;
//	
//	@Column(name="CONTEXT")
	private StateMachineContext<States,Events> context;

	public StateMachineContext<States, Events> getContext() {
		return context;
	}
	public void setContext(StateMachineContext<States, Events> context) {
		this.context = context;
	}
}
