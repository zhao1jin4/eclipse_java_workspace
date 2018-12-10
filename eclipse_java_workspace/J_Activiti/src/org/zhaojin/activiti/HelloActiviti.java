package org.zhaojin.activiti;
import org.junit.jupiter.api.Test; //Junit 5 

 

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
public class HelloActiviti {
 
	@Test
	public void deploy()
	{
		org.activiti.bpmn.constants.BpmnXMLConstants x;
		
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();//源码中找classpaht:activiti.cfg.xml
		
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
				.addClasspathResource("org/zhaojin/activiti/hello.bpmn") 
		       .addClasspathResource("org/zhaojin/activiti/hello.png") 
		       .deploy();
		
		 
		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
		
	}
	 
}
