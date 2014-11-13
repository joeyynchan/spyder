package com.journaldev.mongodb.dao;

import com.journaldev.mongodb.converter.DataConverter;
import com.journaldev.mongodb.model.Data;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

//DAO class for different MongoDB CRUD operations
//take special note of "id" String to ObjectId conversion and vice versa
//also take note of "_id" key for primary key
public class MongoDBDataDAO {

	private DBCollection col;

	public MongoDBDataDAO(MongoClient mongo) {
		this.col = mongo.getDB("SpyderDB").getCollection("Data");
	}

	public void createData(Data d) {
		DBObject doc = DataConverter.toDBObject(d);
		this.col.insert(doc);
	}

}
