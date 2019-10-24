package com.deloitte.flowable.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.flowable.domain.Approval;
import com.deloitte.flowable.domain.Article;
import com.deloitte.flowable.service.ArticleWorkflowService;

@RestController
public class ArticleWorkflowController {
    @Autowired
    private ArticleWorkflowService service;
    @PostMapping(value="/submit")
    public void submit(@RequestBody Article article) {
        service.startProcess(article);
    }
    @GetMapping("/tasks")
    public List<Article> getTasks(@RequestParam String assignee) {
        return service.getTasks(assignee);
    }
    @PostMapping("/review")
    public void review(@RequestBody Approval approval) {
        service.submitReview(approval);
    }
}