package com.journaldev.mongodb.model;

public class User {

	// id will be used for primary key in MongoDB
	// We could use ObjectId, but I am keeping it
	// independent of MongoDB API classes
	private String id;
	private String user_name;
	private String password;
	private String mac_address;
	private String gcm_id;
	
	public String getPassword(){
		return password;
	}
	
	public String getGCM(){
		return gcm_id;
	}
	
	public void setGCM(String gcm_id){
		this.gcm_id = gcm_id;
	}

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
	
	public void setPassword(String password){
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString(){
		return "(" + id + ", " + user_name + ", " + password + ", " + mac_address + ")";
	}
}
