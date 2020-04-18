package org.zh.activiti;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.zh.activiti.other.Leave;
import org.zh.activiti.other.ReImbursement;

public class MyGatewayFlow {
	
	@Test
	public void deploy()
	{
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine(); 
		Deployment deployment=processEngine.getRepositoryService() 
				.createDeployment() 
				.name("gatewayFlow")
			   .addClasspathResource("org/zh/activiti/gatewayFlow.bpmn") 
		       .addClasspathResource("org/zh/activiti/gatewayFlow.png") 
		       .deploy();
	}
	
	/* 
	@Test
	public void deleteProcessDefinition(){
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		//按名查询Deployment
		Deployment deploy=processEngine.getRepositoryService().createDeploymentQuery()
				.deploymentName("gatewayFlow").singleResult();
		processEngine.getRepositoryService()
						.deleteDeployment(deploy.getId(),true);
	}
*/
	//---上和前面一样的
	
	@Test
	public void startInstance()
	{
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		String processDefinitionKey = "myGatewayProcess"; 
		
		Map<String,Object> var=new HashMap<>();
		var.put("user0", "小王");
		var.put("groupA", "孙队");
		var.put("managerA", "陈总");
		var.put("managerB", "李总");
		var.put("accountA", "孙财务"); 
		var.put("accountB", "礼出纳"); 
		
		ProcessInstance instance = processEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey,"reimbursement007",var);
		
	}
	
	@Test
	public void completeMyTask(){
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		String processDefinitionKey = "myGatewayProcess"; 
		
		 Task  task = processEngine.getTaskService() .createTaskQuery() 
				//where 
				.taskAssignee("小王")
				.processDefinitionKey(processDefinitionKey).singleResult();
		 processEngine.getTaskService().complete(task.getId());
		 
		 task = processEngine.getTaskService() .createTaskQuery() 
					//where 
					.taskAssignee("孙队")
					.processDefinitionKey(processDefinitionKey).singleResult();
		Map<String,Object> vars=new HashMap<>();
		vars.put("money",new ReImbursement(400,"报销出差车费"));
		processEngine.getTaskService().complete(task.getId(),vars);
		//exclusiveGateway 排它网关后如有两个分支， 同时满足两个条件，那么就会进id值小的那一个任务,如所有条件不满足就报错 ，控制出，不控制入 
	    
		
	}
	
	@Test
	public void completeInParallelTask(){
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		String processDefinitionKey = "myGatewayProcess"; 
		
		//如流程分支的线的条件可能同时满足(或都没有设置条件)，那么就一起进入两个任务
		 Task  task = processEngine.getTaskService() .createTaskQuery() 
				//where 
				.taskAssignee("陈总")
				.processDefinitionKey(processDefinitionKey).singleResult();
		 processEngine.getTaskService().complete(task.getId());
		 //看 act_ru_task 有两个任务一起
	}
	@Test
	public void completeOutOneParallelTask(){
		
		//parallelGateway 并行网关 多个同时满足才能向下走 ，控制出(忽略后面分支线上的条件)，不控制入 
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		String processDefinitionKey = "myGatewayProcess"; 
		
		 Task  task = processEngine.getTaskService() .createTaskQuery() 
				//where 
				.taskAssignee("孙财务")
				.processDefinitionKey(processDefinitionKey).singleResult();
		 processEngine.getTaskService().complete(task.getId());
	}
	@Test
	public void completeOutTwoParallelTask(){
		
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		String processDefinitionKey = "myGatewayProcess"; 
		
		 Task  task = processEngine.getTaskService() .createTaskQuery() 
				//where 
				.taskAssignee("礼出纳")
				.processDefinitionKey(processDefinitionKey).singleResult();
		 processEngine.getTaskService().complete(task.getId());
	}
	// includeGateway   包含网关 多个同时满足才能向下走 ，    出(使用后面分支线上的条件)  ，
	
	
	 
	//如何在指定Task时，在图的边框高亮选中  org.activiti.image
}
