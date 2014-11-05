package com.journaldev.mongodb.converter;

import org.bson.types.ObjectId;

import com.journaldev.mongodb.model.Event;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class EventConverter {

	// convert Person Object to MongoDB DBObject
	// take special note of converting id String to ObjectId
	public static DBObject toDBObject(Event e) {

		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
				.append("name", e.getName())
				.append("start_time", e.getStart_time())
				.append("end_time", e.getEnd_time())
				.append("address", e.getAddress()).append("name", e.getName())
				.append("description", e.getDescription())
				.append("speaker_id", e.getSpeaker_id())
				.append("organiser_id", e.getOrganiser_id())
				.append("attendees", e.getAttendees());
		if (e.getId() != null)
			builder = builder.append("_id", new ObjectId(e.getId()));
		return builder.get();
	}

	// convert DBObject Object to Person
	// take special note of converting ObjectId to String
	public static Event toEvent(DBObject doc) {
		return new Event((String) doc.get("start_time"),
				(String) doc.get("end_time"), (String) doc.get("address"),
				(String) doc.get("name"), (String) doc.get("description"),
				(String) doc.get("speaker_id"),
				(String) doc.get("organiser_id"), (String) doc.get("attendees"));

	}

}
