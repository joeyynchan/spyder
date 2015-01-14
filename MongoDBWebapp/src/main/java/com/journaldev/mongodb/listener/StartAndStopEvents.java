package com.journaldev.mongodb.listener;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.journaldev.mongodb.dao.MongoDBEventDAO;
import com.journaldev.mongodb.dao.MongoDBUsersDAO;
import com.journaldev.mongodb.model.Event;
import com.journaldev.mongodb.model.User;
import com.mongodb.MongoClient;

public class StartAndStopEvents implements Runnable {

	private final String apiKey = "AIzaSyBz679jaiOiPAcGVXX95QXhU-l0e3sxtAE";
	MongoClient mongo;
	
	@Override
	public void run() {
		System.out.println("RUN");
		URL url;
		try {
			url = new URL("https://android.googleapis.com/gcm/send");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		HttpURLConnection conn;
		try {
			conn = (HttpURLConnection)url.openConnection();
			conn.setDoOutput(true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		try {
			conn.setRequestMethod("POST");
		} catch (ProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
		MongoDBUsersDAO userDAO = new MongoDBUsersDAO(mongo);
		List<Event> events = eventDAO.readAllEvent();
		Date d = new Date();
		for (Event event : events) {
			JSONObject data = new JSONObject();
			ArrayList<String> gcm_ids = new ArrayList<String>();
			long millsec = d.getTime() - new Date(event.getStart_time()).getTime();
			if (millsec > 0 && millsec < 5000) {
				try {
					data.put("action", "START_EVENT");
					data.put("event_id", event.getId());
					data.put("event_name", event.getName());
					for (String user_name : event.getAttendees()) {
						User user = userDAO.getUserByName(user_name);
						if (user != null) {
							gcm_ids.add(user.getGCM());
						}
					}
		            JSONObject params = new JSONObject();
		            params.put("data", data);
		            params.put("registration_ids", new JSONArray(gcm_ids));
		            conn.setRequestMethod("POST");
		    		conn.setRequestProperty("Content-Type", "application/json");
		    		conn.setRequestProperty("Authorization", "key="+apiKey);
		    		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		    		wr.writeBytes(params.toString());
		    		wr.flush();
		    		wr.close();
		    		System.out.println(d.getMinutes());
		    		System.out.println("Start Response Code : " + conn.getResponseCode());
				} catch (JSONException | IOException e) {
					e.printStackTrace();
					return;
				}
				
			}
			millsec = d.getTime() - new Date(event.getEnd_time()).getTime();
			if (millsec > 0 && millsec < 5000) {
				try {
					data.put("action", "STOP_EVENT");
					data.put("event_id", event.getId());
					data.put("event_name", event.getName());
					for (String user_name : event.getAttendees()) {
						User user = userDAO.getUserByName(user_name);
						if (user != null) {
							gcm_ids.add(user.getGCM());
						}
					}
		            JSONObject params = new JSONObject();
		            params.put("data", data);
		            params.put("registration_ids", new JSONArray(gcm_ids));
		            conn.setRequestMethod("POST");
		    		conn.setRequestProperty("Content-Type", "application/json");
		    		conn.setRequestProperty("Authorization", "key="+apiKey);
		    		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		    		wr.writeBytes(params.toString());
		    		wr.flush();
		    		wr.close();
		    		System.out.println("Stop Response Code : " + conn.getResponseCode());
				} catch (JSONException | IOException e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}
	
	public void setClient(MongoClient client) {
		mongo = client;
	}

}
