package com.deloitte.flowable.service;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class ProfileWorkflowService implements JavaDelegate{
	public void execute(DelegateExecution execution) {
        System.out.println("Save Profile Data....");
    }
}
