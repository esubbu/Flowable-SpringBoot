package com.deloitte.flowable;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.flowable.engine.ProcessEngines;
@SpringBootApplication
public class SpringbootFlowableApplication {
//	@Autowired
//	static ProcessEngine processEngine;
	public static void main(String[] args) {
		SpringApplication.run(SpringbootFlowableApplication.class, args);
		
		ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
		  RepositoryService repositoryService = processEngine.getRepositoryService();
		  Deployment deployment = repositoryService.createDeployment()
				/* .addClasspathResource("processes/article-workflow1.bpmn20.xml") */
		  .addClasspathResource("processes/NISworkflow.bpmn20.xml")
		  .deploy();
		  
		  ProcessDefinition processDefinition =
		  repositoryService.createProcessDefinitionQuery()
		  .deploymentId(deployment.getId()) .singleResult();
		  System.out.println("Found process definition : " +
		  processDefinition.getName());
		 
	}

}
