package com.journaldev.mongodb.listener;

import java.util.Date;
import java.util.List;

import com.journaldev.mongodb.dao.MongoDBEventDAO;
import com.journaldev.mongodb.model.Event;
import com.mongodb.MongoClient;

public class StartAndStopEvents implements Runnable {

	MongoClient mongo;
	
	@Override
	public void run() {
		MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
		List<Event> events = eventDAO.readAllEvent();
		Date d = new Date();
		for (Event event : events) {
			long millsec = d.getTime() - new Date(event.getStart_time()).getTime();
			if (millsec > 0 && millsec < 5000) {
				// TODO somethign with gcm_id;
			}
			millsec = d.getTime() - new Date(event.getEnd_time()).getTime();
			if (millsec > 0 && millsec < 5000) {
				// TODO somethign with gcm_id;
			}
		}
	}
	
	public void setClient(MongoClient client) {
		mongo = client;
	}

}
