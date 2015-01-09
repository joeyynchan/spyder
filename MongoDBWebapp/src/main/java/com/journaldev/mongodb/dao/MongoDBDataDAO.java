package com.journaldev.mongodb.dao;

import java.util.ArrayList;
import java.util.List;

import com.journaldev.mongodb.converter.DataConverter;
import com.journaldev.mongodb.model.Data;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
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

	public List<String> getAllData(String user_name) {
			DBObject query = BasicDBObjectBuilder.start()
					.append("user_name", user_name).get();
			System.out.println(query.toString());
			DBCursor cursor = this.col.find(query);
			List<String> res = new ArrayList<String>();
			while(cursor.hasNext()){
				DBObject data = cursor.next();
				String user_data = DataConverter.getData(data);
				res.add(user_data);
			}
			return res;
	}

    public List<String> getDataForEvent(String event_id) {
        DBObject query = BasicDBObjectBuilder.start()
                .append("event_id", event_id).get();
        DBCursor cursor = this.col.find(query);
        List<String> res = new ArrayList<String>();
        while(cursor.hasNext()){
            DBObject data = cursor.next();
            String user_data = DataConverter.getData(data);
            res.add(user_data);
        }
        return res;
    }

}
