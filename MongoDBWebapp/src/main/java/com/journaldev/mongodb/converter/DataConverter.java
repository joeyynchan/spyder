package com.journaldev.mongodb.converter;

import java.util.ArrayList;
import java.util.List;

import com.journaldev.mongodb.model.Data;
import com.journaldev.mongodb.utils.Pair;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class DataConverter {

	// convert Person Object to MongoDB DBObject
	// take special note of converting id String to ObjectId
	public static DBObject toDBObject(Data d) {

		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
				.append("user_name", d.getUser_name())
				.append("event_id", d.getEvent_id())
				.append("time_interval", d.getTime_interval())
				.append("data", d.getBluetooth_strengths().toString());
		return builder.get();
	}

	// convert DBObject Object to Person
	// take special note of converting ObjectId to String
	public static Data toEvent(DBObject doc) {
		String s = (String) doc.get("data");
		// "[[(a b),(c d)]]"

		List<List<Pair<String, Integer>>> bluetooth_data = new ArrayList<List<Pair<String, Integer>>>();
		s = s.replace("[", "").replace("]", "");
		String[] split = s.split(",");
		for (int i = 0; i < split.length; i++) {
			String k = s.replace("[","").replace("]", "");
			String[] split_inner = k.split(",");
			List<Pair<String,Integer>> inner_data = new ArrayList<Pair<String,Integer>>();
			for(int j = 0; j<split_inner.length; j++){
				inner_data.add(new Pair<String,Integer>(split[j].split(" ")[0],
						Integer.parseInt(split[j].split(" ")[1])));
			}
			bluetooth_data.add(inner_data);
			
		}

		return new Data((String) doc.get("user_name"),
				(String) doc.get("event_id"),
				(Integer) doc.get("time_interval"), bluetooth_data);

	}

	public static String getData(DBObject doc) {
		String user_name = (String)doc.get("user_name");
		String event_id = (String)doc.get("event_id");
		Integer time_interval = (Integer)doc.get("time_interval");
		String data = (String)doc.get("data");
		return "{user_name : " + user_name + " , event_id : " + event_id + " , time_interval : " + time_interval.toString() + " , data : " + data +  " }";
		
	}

}
