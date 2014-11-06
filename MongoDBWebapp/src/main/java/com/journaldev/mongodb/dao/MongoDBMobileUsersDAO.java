package com.journaldev.mongodb.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.journaldev.mongodb.converter.MobileUserConverter;
import com.journaldev.mongodb.model.MobileUser;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

//DAO class for different MongoDB CRUD operations
//take special note of "id" String to ObjectId conversion and vice versa
//also take note of "_id" key for primary key
public class MongoDBMobileUsersDAO {

	private DBCollection col;

	public MongoDBMobileUsersDAO(MongoClient mongo) {
		this.col = mongo.getDB("SpyderDB").getCollection("MobileUsers");
	}

	public MobileUser createMobileUser(MobileUser p) {
		DBObject doc = MobileUserConverter.toDBObject(p);
		this.col.insert(doc);
		ObjectId id = (ObjectId) doc.get("_id");
		p.setId(id.toString());
		return p;
	}

	public void updateMobileUser(MobileUser p) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(p.getId())).get();
		this.col.update(query, MobileUserConverter.toDBObject(p));
	}

	public List<MobileUser> readAllMobileUsers() {
		List<MobileUser> data = new ArrayList<MobileUser>();
		DBCursor cursor = col.find();
		while (cursor.hasNext()) {
			DBObject doc = cursor.next();
			MobileUser p = MobileUserConverter.toMobileUser(doc);
			data.add(p);
		}
		return data;
	}

	public void deleteMobileUser(MobileUser p) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(p.getId())).get();
		this.col.remove(query);
	}

	public MobileUser readMobileUser(MobileUser p) {
		DBObject query = BasicDBObjectBuilder.start()
				.append("_id", new ObjectId(p.getId())).get();
		DBObject data = this.col.findOne(query);
		return MobileUserConverter.toMobileUser(data);
	}

	public List<MobileUser> getAllUsers(List<String> user_id_list) {
		List<MobileUser> result = new ArrayList<MobileUser>();
		for(String id:user_id_list){
			DBObject query = BasicDBObjectBuilder.start()
					.append("_id", new ObjectId(id)).get();
			DBObject data = this.col.findOne(query);
			result.add(MobileUserConverter.toMobileUser(data));
		}
		return result;
	}
}
