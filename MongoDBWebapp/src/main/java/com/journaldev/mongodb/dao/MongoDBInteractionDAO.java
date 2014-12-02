package com.journaldev.mongodb.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.journaldev.mongodb.converter.InteractionConverter;
import com.journaldev.mongodb.model.Interaction;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDBInteractionDAO {

	private DBCollection col;

	public MongoDBInteractionDAO(MongoClient mongo) {
		this.col = mongo.getDB("SpyderDB").getCollection("Interactions");
	}
	
	public Interaction createInteraction(Interaction i) {
		if (i != null) {
			DBObject interactionObj = InteractionConverter.toDBObject(i);
			this.col.insert(interactionObj);
			ObjectId objId = (ObjectId) interactionObj.get("_id");
			i.setId(objId.toString());
		}
		return i;
	}
	
	public void updateInteraction(Interaction i) {
		if (i != null) {
			DBObject query = BasicDBObjectBuilder.start()
					.append("_id", new ObjectId(i.getId())).get();
			this.col.update(query, InteractionConverter.toDBObject(i));
		}
	}
	
	public List<Interaction> findInteractionBetweenUsers(String event_id, String user1, String user2) {
		List<Interaction> interactions = new ArrayList<Interaction>();
		DBObject query = BasicDBObjectBuilder.start()
				.append("event_id", event_id)
				.append("user1", user1)
				.append("user2", user2).get();
		DBCursor it = this.col.find(query);
		
		while (it.hasNext()) {
			interactions.add(InteractionConverter.toInteraction(it.next()));
		}
		return interactions;
	}
	
	public List<Interaction> getAllInteraction(String event_id) {
		List<Interaction> interactions = new ArrayList<Interaction>();
		DBObject query = BasicDBObjectBuilder.start()
				.append("event_id", event_id).get();
		DBCursor it = this.col.find(query);
		
		while (it.hasNext()) {
			interactions.add(InteractionConverter.toInteraction(it.next()));
		}
		return interactions;
	}
	
	public List<Interaction> getUserEventInteraction(String event_id, String user) {
		List<Interaction> interactions = new ArrayList<Interaction>();
		DBObject query = BasicDBObjectBuilder.start()
				.append("event_id", event_id)
				.append("user1", user).get();
		DBCursor it = this.col.find(query);
		while (it.hasNext()) {
			interactions.add(InteractionConverter.toInteraction(it.next()));
		}
		
		query = BasicDBObjectBuilder.start().append("event_id", event_id)
				.append("user2", user).get();
		it = this.col.find(query);
		while (it.hasNext()) {
			interactions.add(InteractionConverter.toInteraction(it.next()));
		}
		
		return interactions;
	}
	
	public List<Interaction> getUserInteraction(String user) {
		List<Interaction> interactions = new ArrayList<Interaction>();
		DBObject query = BasicDBObjectBuilder.start().append("user1", user).get();
		DBCursor it = this.col.find(query);
		while (it.hasNext()) {
			interactions.add(InteractionConverter.toInteraction(it.next()));
		}
		
		query = BasicDBObjectBuilder.start().append("user2", user).get();
		it = this.col.find(query);
		while (it.hasNext()) {
			interactions.add(InteractionConverter.toInteraction(it.next()));
		}
		
		return interactions;
	}
	
	@SuppressWarnings("deprecation")
	public Interaction findLatestInteraction(String event_id, String user1, String user2) {
		List<Interaction> interactions = findInteractionBetweenUsers(event_id, user1, user2);
		long milliseconds = 0;
		Interaction latestInteraction = null;
		for (Interaction it : interactions) {
			Date date = new Date(it.getEnd_time());
			if (date.getTime() > milliseconds) {
				milliseconds = date.getTime();
				latestInteraction = it;
			}
		}
		return latestInteraction;
	}
	
}
