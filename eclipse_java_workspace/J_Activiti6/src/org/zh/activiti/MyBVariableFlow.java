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

public class MyBVariableFlow {
	
	@Test
	public void deploy()
	{
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine(); 
		Deployment deployment=processEngine.getRepositoryService() 
				.createDeployment() 
				.name("varFlow")
			   .addClasspathResource("org/zh/activiti/variableFlow.bpmn") 
		       .addClasspathResource("org/zh/activiti/variableFlow.png") 
		       .deploy();
	}
	//---上和前面一样的
	@Test
	public void deleteProcessDefinition(){
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		//按名查询Deployment
		Deployment deploy=processEngine.getRepositoryService().createDeploymentQuery()
				.deploymentName("varFlow").singleResult();
		processEngine.getRepositoryService()
						.deleteDeployment(deploy.getId(),true);
	}

	@Test
	public void startInstance()
	{
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		String processDefinitionKey = "myVarProcess"; 
		
		Map<String,Object> var=new HashMap<>();
		var.put("user0", "小王");
		var.put("user1", "王队");
		var.put("user2", "陈总");
		var.put("user3", "孙财务"); 
		
		
		//使用key值启动，默认是按照最新版本的流程定义启动
		ProcessInstance instance = processEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey,"leave10009",var);
		//act_ru_execution,act_hi_procinst 表中有 BUSINESS_KEY_ 可用来存自己的业务表的ID,可选第二个参数传BUSINESS_KEY_的值
		//第三个参数为流程变量的Map,act_ru_variable表存变量,act_hi_identitylink表是替换变量后的值,act_ge_bytearray中的bpmn文件还是变量
		//如bpmn图中的Assignee的值为${user0},使用的是JavaEE的UEL表达式，可以${user.birthday}或${user.getAge()}或选线${user.getAge()>10},类要实现Serializable接口
		//这的变量范围是全流程实例的，如task中的可以覆盖这里的同名变量
		
	}
	
	@Test
	public void completeMyTask(){
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		String processDefinitionKey = "myVarProcess"; 
		//选线，Main Config->Condition中写条如${leave.days<=3}，在任务完成时传递,如流程分支的线的条件可能同时满足，那么就一起进入两个任务
		 Task  task = processEngine.getTaskService() .createTaskQuery() 
				//where 
				.taskAssignee("小王")
				.processDefinitionKey(processDefinitionKey).singleResult();
		Map<String,Object> vars=new HashMap<>();
		vars.put("leave",new Leave(3.5f,"回家"));
		processEngine.getTaskService().complete(task.getId(),vars);//可选第二个参数传递Map变量,可以覆盖流程实例级的变量
	}
	
	/**查询流程变量的历史表*/
	@Test
	public void findHistoryProcessVariables(){
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		List<HistoricVariableInstance> list = processEngine.getHistoryService()
						.createHistoricVariableInstanceQuery()//就是act_hi_varinst表
						//.variableName("leave")
						.list();
		for(HistoricVariableInstance hvi:list){
			System.out.println(hvi.getId()+"   "+hvi.getProcessInstanceId()+"   "+hvi.getVariableName()+"   "+hvi.getVariableTypeName()+"    "+hvi.getValue());
		}
	}
	//act_ru_variable表的数据
	public void setAndGetVariables(){
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		/**流程变量 流程实例级 （正在执行）*/
		RuntimeService runtimeService = processEngine.getRuntimeService();
		/**流程变量 任务级（正在执行）*/
		TaskService taskService = processEngine.getTaskService();
		
		/**设置流程变量*/
//		runtimeService.setVariable(executionId, variableName, value)//表示使用执行对象ID， 一次只能设置一个值 
//		runtimeService.setVariables(executionId, variables)//Map集合设置 （一次设置多个值）
		
//		taskService.setVariable(taskId, variableName, value)//表示使用任务ID（一次只能设置一个值）
//		taskService.setVariables(taskId, variables)// Map集合设置流程变量（一次设置多个值）
		
//		runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);//启动流程实例的同时，用Map集合
//		taskService.complete(taskId, variables)//完成任务的同时，用Map集合
		
		/**获取流程变量*/
//		runtimeService.getVariable(executionId, variableName);//使用执行对象ID和流程变量的名称
//		runtimeService.getVariables(executionId);//使用执行对象ID，获取所有的流程变量,返回Map
//		runtimeService.getVariables(executionId, variableNames);//使用执行对象ID，变量的名称存放到集合中
		
//		taskService.getVariable(taskId, variableName);//使用任务ID和流程变量的名称 
//		taskService.getVariables(taskId);//使用任务ID， 将流程变量放置到Map集合中 
//		taskService.getVariables(taskId, variableNames);//使用任务ID， 名称存放到集合中 
	}
	
	//eclipse properties标签中有listener标签，new一个 event可选create(task创建),assigment(task分配),all,complete(idea工具没有),(idea工具还有delete)
	//如type为class 要求实现 TaskListener接口
	
	
	//	Condiate Users,Condiate Groups 由逗号分隔多个,表示是由多个人完成任务
	//在act_hi_actinst表中的ASSIGEE_开始为空的，做完才有值,act_ru_task中ASSIGEE_，act_hi_identitylink表TYPE_有participant(单人),condiate(多人)
	//processEngine.getTaskService().claim(taskId, userId);//组中或多候选人(也可以是非组任务的成员) 来做任务
	//processEngine.getTaskService().setAssignee(taskId, null);//回退到组任务，前提，之前一定是个组任务
	//processEngine.getTaskService().setAssignee(taskId, userId);//把任务指派给其它人办理,业务上保证当前登录人是目前经办人,也可按角色认领任务

//	processEngine.getTaskService().addCandidateUser(taskId, userId);//task中增加候选人
//	processEngine.getTaskService().deleteCandidateUser(taskId, userId);//task中删除候选人
//	processEngine.getTaskService().getIdentityLinksForTask(taskId);
//	processEngine.getHistoryService().getHistoricIdentityLinksForProcessInstance(processInstanceId)
 
}
