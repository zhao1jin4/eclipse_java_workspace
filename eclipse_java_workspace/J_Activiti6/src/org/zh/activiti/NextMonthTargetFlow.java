package org.zh.activiti;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

public class NextMonthTargetFlow 
{
	ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine(); 

	@Test
	public void deployAndStart()
	{
		Deployment deployment=processEngine.getRepositoryService() 
				.createDeployment() 
				.name("nextMonthTarget")
				//另一种方式增加资源
				.addInputStream("nextMonthTarget.bpmn", this.getClass().getResourceAsStream("nextMonthTarget.bpmn"))
				.addInputStream("nextMonthTarget.png",  this.getClass().getResourceAsStream("nextMonthTarget.png"))
		       .deploy();
		String processDefinitionKey = "nextMonthTargetProcess"; 
		ProcessInstance instance = processEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey);
	}
	@Test
	public void deleteProcessDefinition(){
		String processDefinitionKey = "nextMonthTargetProcess"; 
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	 ProcessDefinition  def = processEngine.getRepositoryService()
				.createProcessDefinitionQuery() 
				.processDefinitionKey(processDefinitionKey).singleResult();
		processEngine.getRepositoryService()
						.deleteDeployment(def.getDeploymentId(),true);//true 级联删除 
		System.out.println("删除成功！");
	}
	@Test
	public void haveResult()
	{
		String processDefinitionKey = "nextMonthTargetProcess"; 
		RuntimeService runtimeService=processEngine.getRuntimeService();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processDefinitionKey(processDefinitionKey).singleResult();

		Execution execution1 = processEngine.getRuntimeService() 
				.createExecutionQuery() 
				.processInstanceId(pi.getId()) 
				.activityId("receivetask1")//对应.bpmn文件中的ReceiveTask节点id的属性值,存在ACT_ID_字段中
				.singleResult();
		
		processEngine.getRuntimeService() //RuntimeService传变量
			.setVariable(execution1.getId(), "salesByLastMonth", 21000);
		
		System.out.println("汇总上月业绩完成，发邮件通知总经理制定当月计划");
		//流程继续执行(5版本的signal(executionId)没有了，6版本如何做？？用signalEventReceived,messageEventReceived不行)
		processEngine.getRuntimeService().signalEventReceived("计算上月销量额");
	}
	@Test
	public void confirmAmountForThisMonth()
	{	
		String processDefinitionKey = "nextMonthTargetProcess"; 
		RuntimeService runtimeService=processEngine.getRuntimeService();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processDefinitionKey(processDefinitionKey).singleResult();

		//能取到变量值 ？？？
		Execution execution2 = processEngine.getRuntimeService()
						.createExecutionQuery() 
						.processInstanceId(pi.getId()) 
						.activityId("usertask2") 
						.singleResult();
		
		Integer value = (Integer)processEngine.getRuntimeService()//RuntimeService取变量
						.getVariable(execution2.getId(), "salesByLastMonth");
		  
	 
		 Task task  = processEngine.getTaskService()  
						.createTaskQuery() 
						//where
						.taskAssignee("张总经理")
						.processDefinitionKey(processDefinitionKey).singleResult();
		 processEngine.getTaskService().complete(task.getId());
		 System.out.println("总经理制定了本月目标为："+value+1000);
	     System.out.println("流程结束");
		 
	}
}
