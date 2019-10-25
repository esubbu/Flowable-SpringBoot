package com.deloitte.flowable.service;

import org.springframework.stereotype.Service;

@Service
public class CreateRepositoryService {
	
	
	public boolean createBitBucketRepo(String userName) {
		System.out.println("Bit Bucket Repository Created...");
		return true;
		
	}

}
