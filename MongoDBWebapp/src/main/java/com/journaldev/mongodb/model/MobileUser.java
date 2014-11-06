package com.journaldev.mongodb.model;

public class MobileUser {

	// id will be used for primary key in MongoDB
	// We could use ObjectId, but I am keeping it
	// independent of MongoDB API classes
	private String id;

	private String user_name;

	private String mac_address;

	public String getUserName() {
		return user_name;
	}

	public void setUserName(String name) {
		this.user_name = name;
	}

	public String getMacAddress() {
		return mac_address;
	}

	public void setMacAddress(String mac_addr) {
		this.mac_address = mac_addr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
