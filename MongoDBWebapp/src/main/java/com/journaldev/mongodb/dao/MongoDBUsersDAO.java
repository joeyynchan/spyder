package com.journaldev.mongodb.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.journaldev.mongodb.converter.UserConverter;
import com.journaldev.mongodb.model.User;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

//DAO class for different MongoDB CRUD operations
//take special note of "id" String to ObjectId conversion and vice versa
//also take note of "_id" key for primary key
public class MongoDBUsersDAO {

	private DBCollection col;

	public MongoDBUsersDAO(MongoClient mongo) {
		this.col = mongo.getDB("SpyderDB").getCollection("Users");
	}

	public User createUser(User p) {
		DBObject doc = UserConverter.toDBObject(p);
		this.col.insert(doc);
		ObjectId id = (ObjectId) doc.get("_id");
		p.setId(id.toString());
		return p;
	}

	public void updateUser(User p) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(p.getId())).get();
		this.col.update(query, UserConverter.toDBObject(p));
	}

	public List<User> readAllUsers() {
		List<User> data = new ArrayList<User>();
		DBCursor cursor = col.find();
		while (cursor.hasNext()) {
			DBObject doc = cursor.next();
			User p = UserConverter.toUser(doc);
			data.add(p);
		}
		return data;
	}

	public void deleteUser(User p) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(p.getId())).get();
		this.col.remove(query);
	}

	public User readUser(User p) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(p.getId())).get();
		DBObject data = this.col.findOne(query);
		return UserConverter.toUser(data);
	}

	public List<User> getAllUsers(List<String> user_id_list) {
		List<User> result = new ArrayList<User>();
		for (String id : user_id_list) {
			DBObject query = BasicDBObjectBuilder.start()
					.append("_id", new ObjectId(id)).get();
			DBObject data = this.col.findOne(query);
			result.add(UserConverter.toUser(data));
		}
		return result;
	}

	public User getUserByQuery(String user_name, String password) {
		System.out.println(user_name);
		System.out.println(password);
		DBObject query = BasicDBObjectBuilder.start()
				.append("user_name", user_name).append("password", password)
				.get();
		DBObject data = this.col.findOne(query);
		return UserConverter.toUser(data);
	}
	
}
