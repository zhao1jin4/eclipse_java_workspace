
package activiti7.core;
import java.util.List;

import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.GetProcessInstancesPayloadBuilder;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.model.payloads.GetProcessInstancesPayload;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.CandidateGroupsPayloadBuilder;
import org.activiti.api.task.model.builders.CandidateUsersPayloadBuilder;
import org.activiti.api.task.model.builders.DeleteTaskPayloadBuilder;
import org.activiti.api.task.model.builders.GetTasksPayloadBuilder;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.model.payloads.GetTasksPayload;
import org.activiti.api.task.runtime.TaskRuntime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import activiti7.core.SecurityUtil;


@RunWith(SpringRunner.class)
@SpringBootTest//如不指定class=,包名要和src/main/java里的包名一样，为了找到 @SpringBootApplication
public class ActivitiCore7Tests {
    @Autowired
    private ProcessRuntime processRuntime;
    @Autowired
    private TaskRuntime taskRuntime;

    @Autowired
    private SecurityUtil securityUtil;
    
    
    //在执行任何代码前 会自动建立表,但没有act_hi_*表？？ 
    //自动部署src/main/resources/目录下的所有流程(如目录有变化，会再次部署,deployment表名为SpringAutoDeployment产生新版本，相同的流程定义以前就没用了)
   
    //7.1.0.M6再次部署有BUG!!!，查最大版本的Deployment时表中PROJECT_RELEASE_VERSION_为null,导致查出两条记录报错，也导致不能启动流程实例
    
    @Test
    public void testQueryDeployByPage()
    {
    	//要求先登录系统,即使用SpringSecurity的SecurityContextHolder.setContext(xx),要求必须是ACTIVITI_USER角色
    	securityUtil.logInAs("system");
      Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10)); 
//      ProcessDefinition pd= processRuntime.processDefinition(processDefinitionId);
      for (ProcessDefinition pd : processDefinitionPage.getContent()) {
          System.out.println("查询到已经部署的流程定义 : " + pd);
      }
    }
    @Test
    public void startInstance() 
    { 
    	 securityUtil.logInAs("system");
    	  //启动流程实例 
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("myProcess")
                .withName("helloword Name")
//                .withBusinessKey(businessKey)
                .withVariable("account", "erdemedeiros")
                .build());
        System.out.println("启动了流程实例"+processInstance);
    }
    @Test
    public void queryInstance()
    {
    	securityUtil.logInAs("system");
    	GetProcessInstancesPayload payload=new GetProcessInstancesPayloadBuilder().withProcessDefinitionKey("myProcess").build();
    	Page<ProcessInstance>list=processRuntime.processInstances(Pageable.of(0, 10),payload);
    	for(ProcessInstance inst:list.getContent())
    	{
    		System.out.println("查询到流程实例"+inst);
    	}
    }
    @Test
    public void queryTask()
    {
    	 securityUtil.logInAs("salaboy");
//    	 GetTasksPayload payload=new GetTasksPayloadBuilder().withProcessInstanceId("myProcess:3:9c8a2f3a-815b-11ea-9b24-bc77376878aa").build();
    	 //没有历史 ,如没有指定人，组，候选人，查不到
    	 Page<Task>  tasks = taskRuntime.tasks(Pageable.of(0, 10));//可选第二个参数
    	 //只查当前登录人的(同组的) ，SpringSeurity组名要加前缀GROUP_如GROUP_activitiTeam，在activiti中就要用activitiTeam
    	 for(Task task:tasks.getContent())
    	 {
    		  System.out.println("找到任务"+task);
    	 }
    }
    @Test
    public void completeTask()
    {
        securityUtil.logInAs("salaboy");
        Page<Task>  tasks = taskRuntime.tasks(Pageable.of(0, 10)); 
	  	 for(Task task:tasks.getContent())
	  	 {
	  		 Task t=taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).
//	    			 .withVariable("x", "y")
	    			 build());
	  	    System.out.println("完成任务"+t);
	  	 }
	  
    }
    @Test
    public void claimTask() 
    {
    	 securityUtil.logInAs("ryandawsonuk");//同组里的人 
    	 Page<Task>  tasks = taskRuntime.tasks(Pageable.of(0, 10)); 
	  	 for(Task task:tasks.getContent())
	  	 {
	  		 Task t=taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
	         System.out.println("claim任务 : " + t);
	    	 t=taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build());
	  	    System.out.println("完成任务"+t); 
	  	     //驳回到上一节点，通过排它网关指向前一节点
	  	 }
    }
   
    @Test
    public void completeLastTask()
    {
       securityUtil.logInAs("erdemedeiros");
        Page<Task>  tasks = taskRuntime.tasks(Pageable.of(0, 10)); 
	  	 for(Task task:tasks.getContent())
	  	 {
	  		  Task t=taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build());
	  		   System.out.println("完成Last任务"+t);
	  	 }
    }
    @Test
    public void deleteInstance() {
        securityUtil.logInAs("system");
        GetProcessInstancesPayload payload=new GetProcessInstancesPayloadBuilder().withProcessDefinitionKey("myProcess").build();
    	Page<ProcessInstance>list=processRuntime.processInstances(Pageable.of(0, 10),payload);
    	for(ProcessInstance inst:list.getContent())
    	{
    		ProcessInstance del = processRuntime.delete(ProcessPayloadBuilder.delete().withProcessInstanceId(inst.getId()).build());
    		 System.out.println("删除了流程实例 : " + del);
    	}
    }
    @Test
    public void  queryAllVar()
    {
    	 securityUtil.logInAs("erdemedeiros");
    	 Page<Task>  tasks = taskRuntime.tasks(Pageable.of(0, 10)); 
    	 String taskId=tasks.getContent().get(0).getId();
    	 
    	 List<VariableInstance> vars=taskRuntime.variables(TaskPayloadBuilder
                 .variables()
                 .withTaskId(taskId)
                 .build());
    	 for(VariableInstance var :vars)
    	 {
    		 System.out.println("任务中的流程变量: " + var.getName()+"="+var.getValue());
    	 }
    }
    
    @Test
    public void createTask()  //动态创建使用？？ 只有ParentTaskId，后面节点会自动接上？
    {
    	  taskRuntime.create(TaskPayloadBuilder.create()
                  .withName("First Team Task")
                  .withDescription("This is something really important")
                  .withCandidateGroup("activitiTeam")
                  .withPriority(10) 
//                  .withParentTaskId(parentTaskId)
                  .build());
    }
     
    @Test
    public void deleteTask()  //后面节点会自动接上？
    {
    	 securityUtil.logInAs("erdemedeiros");
    	 Page<Task>  tasks = taskRuntime.tasks(Pageable.of(0, 10)); 
    	 String taskId=tasks.getContent().get(0).getId();
    	 

//       taskRuntime.delete(new DeleteTaskPayloadBuilder().withTaskId(taskId).build())
    	 //taskRuntime.delete(TaskPayloadBuilder.delete().withTaskId(taskId).build());//正在运行任务不能删除 
    	 
    	 //删除数据
//       taskRuntime.deleteCandidateUsers(TaskPayloadBuilder.addCandidateUsers().withTaskId(taskId).build());
//       taskRuntime.deleteCandidateUsers(new CandidateUsersPayloadBuilder().withTaskId(taskId).build());
//       taskRuntime.deleteCandidateGroups(new CandidateGroupsPayloadBuilder().withTaskId(taskId).build());
    }

}
