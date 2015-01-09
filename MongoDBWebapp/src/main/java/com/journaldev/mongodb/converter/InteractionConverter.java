package com.journaldev.mongodb.converter;

import org.bson.types.ObjectId;

import com.journaldev.mongodb.model.Interaction;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class InteractionConverter {
	
	public static DBObject toDBObject(Interaction interaction) {
		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
                .append("event_id", interaction.getEvent_id())
                .append("user1", interaction.getUser1())
                .append("user2", interaction.getUser2())
                .append("start_time", interaction.getStart_time())
                .append("end_time", interaction.getEnd_time());
        if (interaction.getId() != null)
            builder = builder.append("_id", new ObjectId(interaction.getId()));
        return builder.get();
	}
	
	public static Interaction toInteraction(DBObject doc) {
		Interaction interaction = new Interaction((String) doc.get("event_id"),
                (String) doc.get("user1"),
                (String) doc.get("user2"),
                (String) doc.get("start_time"),
                (String) doc.get("end_time"));
		interaction.setId(((ObjectId) doc.get("_id")).toString());
        return interaction;
    }
	
	public static String getData(DBObject doc) {
        String event_id = (String) doc.get("event_id");
        String user1 = (String) doc.get("user1");
        String user2 = (String) doc.get("user2");
        String start_time = (String) doc.get("start_time");
        String end_time = (String) doc.get("end_time");
        return "{event_id : " + event_id +
                " , user1 : " + user1 +
                " , user2 : " + user2 +
                " , start_time : " + start_time +
                " , end_time : " + end_time + " }";

    }
	
	
}