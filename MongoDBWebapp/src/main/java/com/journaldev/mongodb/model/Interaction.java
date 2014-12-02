package com.journaldev.mongodb.model;

public class Interaction {
	
	private String id;
	private String event_id;
	private String user1;
	private String user2;
	private String start_time;
	private String end_time;
	
	public Interaction(String event_id, String user1, String user2,
			String start_time, String end_time) {
		this.event_id = event_id;
		this.user1 = user1;
		this.user2 = user2;
		this.start_time = start_time;
		this.end_time = end_time;
	}
	

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}
	
	public String getEvent_id() {
		return event_id;
	}



	public String getUser1() {
		return user1;
	}



	public String getUser2() {
		return user2;
	}



	public String getStart_time() {
		return start_time;
	}



	public String getEnd_time() {
		return end_time;
	}



	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}



	public void setUser1(String user1) {
		this.user1 = user1;
	}



	public void setUser2(String user2) {
		this.user2 = user2;
	}



	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}



	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}



	@Override
	public String toString() {
		return "[event_id= " + event_id +
				", user1= " + user1 +
				", user2= " + user2 +
				", start_time= " + start_time +
				", end_time= " + end_time + "]";
	}
	
}
