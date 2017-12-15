package com.sample;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.event.process.ProcessCompletedEvent;
import org.kie.api.event.process.ProcessEventListener;
import org.kie.api.event.process.ProcessNodeLeftEvent;
import org.kie.api.event.process.ProcessNodeTriggeredEvent;
import org.kie.api.event.process.ProcessStartedEvent;
import org.kie.api.event.process.ProcessVariableChangedEvent;
import org.kie.api.logger.KieRuntimeLogger;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;
 

public class Jbpm61Dooc {

	public static void main(String[] args) {
		  KieHelper kieHelper = new KieHelper();
		  KieBase kieBase = kieHelper.addResource(ResourceFactory.newClassPathResource("com/sample/hiring.bpmn2"))
		                    .build();
		  
			KieSession ksession = kieBase.newKieSession();
			
			 KieRuntimeLogger logger = KieServices.Factory.get().getLoggers().newFileLogger(ksession, "test");
			 ProcessInstance processInstance = ksession.startProcess("hiring");
		 
			 

			 logger.close();
			 
			new ProcessEventListener ()
			{
				@Override
				public void afterNodeLeft(ProcessNodeLeftEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void afterNodeTriggered(ProcessNodeTriggeredEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void afterProcessCompleted(ProcessCompletedEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void afterProcessStarted(ProcessStartedEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void afterVariableChanged(
						ProcessVariableChangedEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void beforeNodeLeft(ProcessNodeLeftEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void beforeNodeTriggered(ProcessNodeTriggeredEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void beforeProcessCompleted(ProcessCompletedEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void beforeProcessStarted(ProcessStartedEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void beforeVariableChanged(
						ProcessVariableChangedEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
			};
	}

}
