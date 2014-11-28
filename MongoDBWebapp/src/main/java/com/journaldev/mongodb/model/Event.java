package com.journaldev.mongodb.model;

import java.util.Set;

public class Event {

	private String id;
	private String start_time;
	private String end_time;
	private String address;
	private String name;
	private String description;
	private String speaker_id;
	private String organiser_id;
	private Set<String> attendees;

	public Event(String start_time, String end_time, String address,
			String name,String description, String speaker_id, String organiser_id,
			Set<String> attendees) {
		
		this.start_time = start_time;
		this.end_time = end_time;
		this.address = address;
		this.name = name;
		this.speaker_id = speaker_id;
		this.attendees = attendees;		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSpeaker_id() {
		return speaker_id;
	}

	public void setSpeaker_id(String speaker_id) {
		this.speaker_id = speaker_id;
	}

	public String getOrganiser_id() {
		return organiser_id;
	}

	public void setOrganiser_id(String organiser_id) {
		this.organiser_id = organiser_id;
	}

	public Set<String> getAttendees() {
		return attendees;
	}

	public void setAttendees(Set<String> attendees) {
		this.attendees = attendees;
	}
}
