package com.journaldev.mongodb.model;

import java.util.List;

import com.journaldev.mongodb.utils.Pair;

public class Data {

	private Integer time_interval;
	private List<List<Pair<String, Integer>>> bluetooth_strengths;
	private String user_name;
	private String event_id;

	public Data(String user_name, String event_id, Integer time_interval,
			List<List<Pair<String, Integer>>> strengths) {
		this.time_interval = time_interval;
		this.bluetooth_strengths = strengths;
		this.user_name = user_name;
		this.event_id = event_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getEvent_id() {
		return event_id;
	}

	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}

	public Integer getTime_interval() {
		return time_interval;
	}

	public void setTime_interval(Integer time_interval) {
		this.time_interval = time_interval;
	}

	public List<List<Pair<String, Integer>>> getBluetooth_strengths() {
		return bluetooth_strengths;
	}

	public void setBluetooth_strengths(
			List<List<Pair<String, Integer>>> bluetooth_strengths) {
		this.bluetooth_strengths = bluetooth_strengths;
	}

}
