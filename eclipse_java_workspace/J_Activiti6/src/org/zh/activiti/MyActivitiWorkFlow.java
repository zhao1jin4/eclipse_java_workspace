package org.zh.activiti;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test; //Junit 5 
public class MyActivitiWorkFlow {
 
	@Test
	public void deploy()
	{
		org.activiti.bpmn.constants.BpmnXMLConstants x;
		
		
		
		//1.部署流程定义
		//(看源码)找classpath:activiti.cfg.xml,找id为processEngineConfiguration
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		
		
//		RepositoryService repositoryService=processEngine.getRepositoryService();
//		DeploymentBuilder deploymentBuilder=repositoryService.createDeployment();
//		deploymentBuilder.addClasspathResource("hello.bpmn");
//		deploymentBuilder.addClasspathResource("hello.png");
//		Deployment deployment=deploymentBuilder.deploy();
//		
		//简写版
		Deployment deployment=processEngine.getRepositoryService() 
				.createDeployment() 
				.name("helloword")
				
			   //.addClasspathResource("org/zh/activiti/hello.bpmn") 
		       //.addClasspathResource("org/zh/activiti/hello.png") 
		       .addZipInputStream(new ZipInputStream(this.getClass().getResourceAsStream("/org/zh/activiti/hello.zip")))
		       //从zip 是一样的，会自动解压
		       //如不用zip 在act_ge_bytearray表中显示为org/zh/activiti/hello.bpmn,用zip就没包路径
		       
		       .deploy();
		
		 
		System.out.println(deployment.getId());//是数据库中act_re_deployment的id值 
		System.out.println(deployment.getName()); //就是前面的name参数值
		//有数据表,act_re_procdef 表,act_ge_bytearray表(存bpmn和png文件)
	}
	@Test
	public void startInstance()
	{
		//一个  流程定义  可以有多个 流程实例  
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		//2.启动流程实例
		String processDefinitionKey = "myProcess";//key对应 bpmn 文件中id的属性值
		
		//使用key值启动，默认是按照最新版本的流程定义启动
		ProcessInstance instance = processEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey,"leave10009");
		//act_ru_execution(ACT_ID_是任务的ID),act_hi_procinst 表中有 BUSINESS_KEY_ 可用来存自己的业务表的ID,可选第二个参数传BUSINESS_KEY_的值
		//第三个参数为流程变量的Map,act_ru_variable表存变量,act_hi_identitylink表是替换变量后的值,act_ge_bytearray中的bpmn文件还是变量
		//如bpmn图中的Assignee的值为${user0},使用的是JavaEE的UEL表达式，可以${user.birthday}或${user.getAge()}或选线${user.getAge()>10},类要实现Serializable接口
		//这的变量范围是全流程实例的，如task中的可以覆盖这里的同名变量
		
		System.out.println("流程实例ID:"+instance.getId());//流程实例ID,是act_hi_procinst表的ID,看END_TIME_列为空
		System.out.println("流程定义ID:"+instance.getProcessDefinitionId());//流程定义ID,是act_re_procdef表的ID
		System.out.println("BUSINESS_KEY_:"+instance.getBusinessKey());

		//有数据表,act_hi_identitylink表存截止当前任务参与的人,act_ru_task ,act_hi_taskinst,act_hi_actinst,act_ru_execution
	}
	
	@Test
	public void testQueryMyTask(){
		queryMyTask( "小李");
	}
	public String queryMyTask(String assignee){
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		
		//3.查询当前人的个人任务(我的待办)
		//一个 流程实例  下 有多个任务
		String processDefinitionKey = "myProcess";
		
		List<Task> list = processEngine.getTaskService() //就是act_ru_task(act_hi_taskinst)表的数据
						.createTaskQuery() 
						//where
						.taskAssignee(assignee)
						.processDefinitionKey(processDefinitionKey)//可指定哪个流程 
						//.taskCandidateUser(candidateUser) 
						//.processDefinitionId(processDefinitionId) 
						//.processInstanceId(processInstanceId) 
						//.executionId(executionId)
						//order by
						.orderByTaskCreateTime().desc()
						//返回
						.list();
					//.singleResult();
					//.count() 
					//.listPage(firstResult, maxResults);
		
		 for(Task task:list){
				System.out.println("任务ID:"+task.getId());
				System.out.println("任务名称:"+task.getName());
				System.out.println("任务的创建时间:"+task.getCreateTime());
				System.out.println("任务的办理人:"+task.getAssignee());
				System.out.println("流程实例ID："+task.getProcessInstanceId());
				System.out.println("执行对象ID:"+task.getExecutionId());
				System.out.println("流程定义ID:"+task.getProcessDefinitionId());
				System.out.println("########################################################");
		}
		return list.get(0).getId();
	}
	@Test
	public void completeMyTask(){
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		//完成我的任务
		String taskId = queryMyTask("小李");
		processEngine.getTaskService().complete(taskId);//可选第二个参数传递Map变量
		//act_hi_taskinst表指定ID的记录END_TIME_有值 ,并增加一行为下一个任务
		//act_hi_actinst表指定TASK_ID_的记录END_TIME_有值 ,并增加一行为下一个任务
		//act_hi_identitylink表增加新的记录，下一任务的人
		//act_ru_task表把原来的做删除(ID_不能更新)，增加新的记录，记录当前任务的信息，如人
		
		
		System.out.println("完成任务：任务ID："+taskId);
	}
	/**查询流程定义*/
	@Test
	public void testQueryProcessDefinition(){
		queryProcessDefinition();
	}
	
	/**查询流程定义*/
	public String queryProcessDefinition(){
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		
		List<ProcessDefinition> list = processEngine.getRepositoryService()
				.createProcessDefinitionQuery()//就是act_re_procdef表记录
				//where条件
//				.deploymentId(deploymentId) 
//				.processDefinitionId(processDefinitionId) 
//				.processDefinitionKey(processDefinitionKey) 
//				.processDefinitionNameLike(processDefinitionNameLike)
				//排序
				.orderByProcessDefinitionVersion().desc() 
//				.orderByProcessDefinitionName().asc()
				//返回
				.list(); 
//				.singleResult(); 
//				.count();
//				.listPage(firstResult, maxResults);
 
		for(ProcessDefinition pd:list){
			System.out.println("流程定义ID:"+pd.getId());//流程定义的key+版本+随机生成数
			System.out.println("流程定义的名称:"+pd.getName());//对应.bpmn文件中的name属性值
			System.out.println("流程定义的key:"+pd.getKey());//对应.bpmn文件中的id属性值
			System.out.println("流程定义的版本:"+pd.getVersion());//当流程定义的key值相同的相同下，版本升级，默认1
			System.out.println("资源名称bpmn文件:"+pd.getResourceName());
			System.out.println("资源名称png文件:"+pd.getDiagramResourceName());
			System.out.println("部署对象ID："+pd.getDeploymentId());//可用作删除
			System.out.println("#########################################################");
		}
		
		return list.get(0).getDeploymentId();
	}
	
	//下载bpmn和图片文件
	@Test
	public void downloadFile() throws IOException
	{
		String deploymentId = queryProcessDefinition();
		
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		
		List<String> reslist = processEngine.getRepositoryService()
				.getDeploymentResourceNames(deploymentId);//是人act_ge_bytearray表中下载
		for(String resourceName:reslist)
		{
			InputStream in = processEngine.getRepositoryService().getResourceAsStream(deploymentId, resourceName);
			FileUtils.copyInputStreamToFile(in, new File("D:/"+resourceName));
		}
		//方式二
		List<ProcessDefinition> list = processEngine.getRepositoryService() .createProcessDefinitionQuery().list(); 
		for(ProcessDefinition pd:list){
			InputStream in = processEngine.getRepositoryService().getResourceAsStream(deploymentId, pd.getResourceName());//图片
			FileUtils.copyInputStreamToFile(in, new File("E:/"+pd.getResourceName()));
			
			InputStream in2 = processEngine.getRepositoryService().getResourceAsStream(deploymentId, pd.getDiagramResourceName());//bpmn
			FileUtils.copyInputStreamToFile(in2, new File("E:/"+pd.getDiagramResourceName()));
		}
	}
	
	/**删除流程定义(部署)*/
	@Test
	public void deleteProcessDefinition(){
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		
		String deploymentId = queryProcessDefinition();
		processEngine.getRepositoryService()
						.deleteDeployment(deploymentId,true);//true 级联删除,即中途做废流程
						//如不传第二个参数只能流程中止才可删除,否则外键报错
		//act_re_deployment,act_re_procdef,act_ge_bytearray 表删除记录
		//当第二个参数为true 在act_hi_actinst,act_hi_taskinst中也没有记录了
		
		System.out.println("删除成功！");
	}
	
	/* 挂起/激活 流程定义(全部流程实例)*/
	@Test
	public void suspendActivateAllProcessDef()
	{
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	
		RepositoryService repositoryService=processEngine.getRepositoryService();
		ProcessDefinition pd= repositoryService.createProcessDefinitionQuery().singleResult();
		if(pd.isSuspended())
		{
			//第三个参数为时间，如为null则立即执行
			repositoryService.activateProcessDefinitionById(pd.getId(),true,null);//激活流程定义
			//act_re_procdef表的SUSPENSION_STATE_为1表示激活
		}else
		{
			repositoryService.suspendProcessDefinitionById(pd.getId(),true,null);//挂起流程定义
			//act_re_procdef表的SUSPENSION_STATE_为2表示挂起
		}
	}
	/*   挂起/激活 某个流程实例 */
	@Test
	public void suspendActivateProcessInstance()
	{
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		String processDefinitionKey = "myProcess";
		
		RuntimeService runtimeService=processEngine.getRuntimeService();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processDefinitionKey(processDefinitionKey).singleResult();
		//挂起的流程实例不能再进行完成Task的,会报异常
		if(pi.isSuspended())
		{
			//act_ru_execution表的SUSPENSION_STATE_为1表示激活
			runtimeService.activateProcessInstanceById(pi.getId());
		}else
		{
			//act_ru_execution表的SUSPENSION_STATE_为2表示挂起
			runtimeService.suspendProcessInstanceById(pi.getId());
		}
	}
	
	
	
	
}
