package com.journaldev.mongodb.converter;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.journaldev.mongodb.model.User;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class UserConverter {

	// convert Person Object to MongoDB DBObject
	// take special note of converting id String to ObjectId
	public static DBObject toDBObject(User p) {

		BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
				.append("user_name", p.getUserName()).append("password",p.getPassword()).append("mac_address", p.getMacAddress());
		if (p.getId() != null)
			builder = builder.append("_id", new ObjectId(p.getId()));
		return builder.get();
	}

	// convert DBObject Object to Person
	// take special note of converting ObjectId to String
	public static User toUser(DBObject doc) {
		User p = new User();
		p.setUserName((String) doc.get("user_name"));
		p.setPassword((String) doc.get("password"));
		p.setMacAddress((String) doc.get("mac_address"));
		ObjectId id = (ObjectId) doc.get("_id");
		p.setId(id.toString());
		return p;

	}

	public static List<String> getUsers(DBObject doc) {
		String userData = (String)doc.get("attendees");
		System.out.println(userData);
		String[] users = userData.split(",");
		List<String> result = new ArrayList<String>();
		for(int i=0;i<users.length;i++){
			result.add(users[i]);
		}
		return result;
	}
	
}
