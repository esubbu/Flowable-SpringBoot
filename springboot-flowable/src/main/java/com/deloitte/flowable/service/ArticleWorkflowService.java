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

@Service
public class ArticleWorkflowService {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @Transactional
    public void startProcess(Article article) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("author", article.getAuthor());
        variables.put("url", article.getUrl());
        runtimeService.startProcessInstanceByKey("articleReview1", variables);
    }

    @Transactional
    public List<Article> getTasks(String assignee) {
        List<Task> tasks = taskService.createTaskQuery()
          .taskCandidateGroup(assignee)
          .list();
        
        
        List<Article> articles = tasks.stream()
          .map(task -> {
              Map<String, Object> variables = taskService.getVariables(task.getId());
              return new Article(
                task.getId(), (String) variables.get("author"), (String) variables.get("url"));
          })
          .collect(Collectors.toList());
        return articles;
    }

    @Transactional
    public void submitReview(Approval approval) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("approved", approval.isStatus());
		
		  List<Task> tasks = taskService.createTaskQuery()
				  .processDefinitionKey("articleReview1")
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