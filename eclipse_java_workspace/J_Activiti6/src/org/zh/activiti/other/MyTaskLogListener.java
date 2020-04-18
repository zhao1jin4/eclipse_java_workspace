package org.zh.activiti.other;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class MyTaskLogListener implements TaskListener {
	@Override
	public void notify(DelegateTask delegateTask) {
//		delegateTask.setAssignee("");
		System.out.println("===MyTaskListener==eventName"+delegateTask.getEventName()+",Assignee="+delegateTask.getAssignee());
	}
}
