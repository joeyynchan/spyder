package com.journaldev.mongodb.dao;

import org.bson.types.ObjectId;

import com.journaldev.mongodb.converter.ProfileConverter;
import com.journaldev.mongodb.model.Profile;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDBProfileDAO {

    private DBCollection col;

    public MongoDBProfileDAO(MongoClient mongo) {
        this.col = mongo.getDB("SpyderDB").getCollection("Profile");
    }

    public Profile createProfile(Profile e) {
        DBObject doc = ProfileConverter.toDBObject(e);
        this.col.insert(doc);
        ObjectId id = (ObjectId) doc.get("_id");
        e.setId(id.toString());
        return e;
    }

    public void updateProfile(Profile e) {
        DBObject query = BasicDBObjectBuilder.start()
                .append("_id", new ObjectId(e.getId())).get();
        this.col.update(query, ProfileConverter.toDBObject(e));
    }

    public void deleteProfile(Profile e) {
        DBObject query = BasicDBObjectBuilder.start()
                .append("_id", new ObjectId(e.getId())).get();
        this.col.remove(query);
    }

    public Profile readProfile(Profile e) {
        DBObject query = BasicDBObjectBuilder.start()
                .append("_id", new ObjectId(e.getId())).get();
        DBObject data = this.col.findOne(query);
        return ProfileConverter.toProfile(data);
    }

    public Profile readProfileByName(String name) {
        DBObject query = BasicDBObjectBuilder.start()
                .append("user_name", name).get();
        DBObject data = this.col.findOne(query);
        if (data != null) {
            return ProfileConverter.toProfile(data);
        }
        return null;
    }


}
