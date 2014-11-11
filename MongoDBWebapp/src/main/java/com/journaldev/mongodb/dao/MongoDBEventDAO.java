package com.journaldev.mongodb.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.journaldev.mongodb.converter.EventConverter;
import com.journaldev.mongodb.converter.UserConverter;
import com.journaldev.mongodb.model.Event;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

//DAO class for different MongoDB CRUD operations
//take special note of "id" String to ObjectId conversion and vice versa
//also take note of "_id" key for primary key
public class MongoDBEventDAO {

	private DBCollection col;

	public MongoDBEventDAO(MongoClient mongo) {
		this.col = mongo.getDB("SpyderDB").getCollection("Events");
	}

	public Event createEvent(Event e) {
		DBObject doc = EventConverter.toDBObject(e);
		this.col.insert(doc);
		ObjectId id = (ObjectId) doc.get("_id");
		e.setId(id.toString());
		return e;
	}

	public void updateEvent(Event e) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(e.getId())).get();
		this.col.update(query, EventConverter.toDBObject(e));
	}

	public List<Event> readAllEvent() {
		List<Event> data = new ArrayList<Event>();
		DBCursor cursor = col.find();
		while (cursor.hasNext()) {
			DBObject doc = cursor.next();
			Event e = EventConverter.toEvent(doc);
			data.add(e);
		}
		return data;
	}

	public void deleteEvent(Event e) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(e.getId())).get();
		this.col.remove(query);
	}

	public Event readEvent(Event e) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(e.getId())).get();
		DBObject data = this.col.findOne(query);
		return EventConverter.toEvent(data);
	}

	public List<String> getAllUsersIDEvent(String event_id) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(event_id)).get();
		System.out.println(query.toString());
		DBObject data = this.col.findOne(query);
		System.out.println("data: " + data);
		return UserConverter.getUsers(data);
	}

}
