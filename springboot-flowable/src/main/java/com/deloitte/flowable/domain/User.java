package com.deloitte.flowable.domain;

public class User {
	private String id;
	private String userName;
    private String role;
    
    public User(String id, String userName, String role) {
		super();
		this.id = id;
		this.userName = userName;
		this.role = role;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}


}
