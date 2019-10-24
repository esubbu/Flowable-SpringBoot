package com.deloitte.flowable;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.test.Deployment;
import org.flowable.spring.impl.test.FlowableSpringExtension;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(FlowableSpringExtension.class)
@SpringBootTest
class SpringbootFlowableApplicationTests {

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;

	@Test
	@Deployment(resources = { "processes/article-workflow1.bpmn20.xml" })
	void articleApprovalTest() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("author", "test@gmail.com");
		variables.put("url", "http://google.com");
		runtimeService.startProcessInstanceByKey("articleReview1", variables);
		Task task = taskService.createTaskQuery().singleResult();

		assertEquals("Review the submitted tutorial", task.getName());
		variables.put("approved", true);
		taskService.complete(task.getId(), variables);
		assertEquals(0, runtimeService.createProcessInstanceQuery().count());

	}

}
