package com.deloitte.flowable.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.flowable.domain.Approval;
import com.deloitte.flowable.domain.User;
import com.deloitte.flowable.service.NisWorkflowService;

@RestController
@RequestMapping("nis")
public class NisWorkflowController {

	@Autowired
	private NisWorkflowService nisService;

	@PostMapping(value = "/start")
	public void submit(@RequestBody User user) {
		nisService.startProcess(user);
	}

	@GetMapping("/tasks")
	public List<User> getTasks(@RequestParam String assignee) {
		return nisService.getTasks(assignee);
	}

	@PostMapping("/assigned")
	public void review(@RequestBody Approval approval) {
		nisService.submitSupport(approval);
	}

}
