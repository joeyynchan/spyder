package com.journaldev.mongodb.converter;

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;

import com.journaldev.mongodb.model.Profile;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class ProfileConverter {

    public static DBObject toDBObject(Profile profile) {
        BasicDBObjectBuilder builder = BasicDBObjectBuilder.start()
                .append("user_name", profile.getUser_name())
                .append("name", profile.getName())
                .append("job", profile.getJob())
                .append("company", profile.getCompany())
                .append("photo", profile.getPhoto())
                .append("email", profile.getEmail())
                .append("phone", profile.getPhone())
                .append("external_link", profile.getExternal_link())
                .append("gender", profile.getGender())
                .append("connections", profile.getConnections().toString());
        if (profile.getId() != null)
            builder = builder.append("_id", new ObjectId(profile.getId()));
        return builder.get();
    }

    public static Profile toProfile(DBObject doc) {
        String s = (String) doc.get("connections");
        s.replace('[', ' ').replace(']', ' ');

        List<String> connections = Arrays.asList(s.split(","));

        Profile newProfile = new Profile((String) doc.get("user_name"),
                (String) doc.get("name"),
                (String) doc.get("job"),
                (String) doc.get("company"),
                (String) doc.get("photo"),
                (String) doc.get("email"),
                (String) doc.get("phone"),
                (String) doc.get("external_link"),
                (String) doc.get("gender"),
                connections);
        newProfile.setId(((ObjectId) doc.get("_id")).toString());
        return newProfile;

    }

    public static String getData(DBObject doc) {
        String name = (String) doc.get("name");
        String job = (String) doc.get("job");
        String company = (String) doc.get("company");
        String photo = (String) doc.get("photo");
        String email = (String) doc.get("email");
        String phone = (String) doc.get("phone");
        String external_link = (String) doc.get("external_link");
        String gender = (String) doc.get("gender");
        String connections = (String) doc.get("connections");
        return "{name : " + name +
                " , job : " + job +
                " , company : " + company +
                " , photo : " + photo +
                " , email : " + email +
                " , phone : " + phone +
                " , company : " + company +
                " , external_link : " + external_link +
                " , gender : " + gender +
                " , connections : " + connections +  " }";

    }

}
