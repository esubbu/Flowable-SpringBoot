package com.deloitte.flowable.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deloitte.flowable.domain.Approval;
import com.deloitte.flowable.domain.Article;
import com.deloitte.flowable.domain.User;

@Service
public class NisWorkflowService {
	
	@Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @Transactional
    public void startProcess(User user) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("userName", user.getUserName());
		/* variables.put("support", true); */
        runtimeService.startProcessInstanceByKey("nisworkflow", variables);
    }

    @Transactional
    public List<User> getTasks(String assignee) {
        List<Task> tasks = taskService.createTaskQuery()
				/* .taskCandidateGroup(assignee) */
          .active()
          .list();
        
        
        List<User> users = tasks.stream()
          .map(task -> {
              Map<String, Object> variables = taskService.getVariables(task.getId());
              return new User(
                task.getId(), (String) variables.get("userName"),(String) variables.get("role"));
          })
          .collect(Collectors.toList());
        return users;
    }

    @Transactional
    public void submitSupport(Approval approval) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("assigned", approval.isStatus());
		
		  List<Task> tasks = taskService.createTaskQuery()
				  .processDefinitionKey("nisworkflow")
				/*
				 * .or().taskCandidateGroup(assignee) .or().taskAssignee("userName")
				 */
				  .orderByTaskCreateTime().desc().list();
		  
		  System.out.println("tasks-----******subbu---"+tasks.size());
		  for(Task t:tasks) {
			  System.out.println(t.getId());
		  }
        
        taskService.complete(approval.getId(), variables);
    }

}
