package com.journaldev.mongodb.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.journaldev.mongodb.dao.MongoDBEventDAO;
import com.journaldev.mongodb.dao.MongoDBUsersDAO;
import com.journaldev.mongodb.model.Event;
import com.journaldev.mongodb.model.User;
import com.journaldev.mongodb.utils.Pair;
import com.mongodb.MongoClient;

@WebServlet("/searchEvent")
public class SearchEventServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(
				request.getInputStream()));
		String json = "";
		StringBuilder jsonBuilder = new StringBuilder();
		if (br != null) {
			String nextLine = br.readLine();
			while (nextLine != null) {
				jsonBuilder.append(nextLine);
				nextLine = br.readLine();
			}
		}
		json = jsonBuilder.toString();

		try {
			JSONObject jsonObj = new JSONObject(json);
			String user_name = (String) jsonObj.get("user_name");
			String event_search_string = (String) jsonObj.get("event_search_string");

			MongoClient mongo = (MongoClient) request.getServletContext()
					.getAttribute("MONGO_CLIENT");
			MongoDBUsersDAO muDAO = new MongoDBUsersDAO(mongo);
			MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
			
			User user = muDAO.getUserByName(user_name);
			if (!user_name.equals("") && user == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			System.out.println("Search User event: " + user_name + ", " + event_search_string);

			List<Event> events = eventDAO.readAllEvent();
			if (!event_search_string.equals("")) {
				List<Event> event = new ArrayList<Event>();
				for (Event e : events) {
					if (e.getName() != null 
							&& e.getName().toLowerCase().contains(event_search_string.toLowerCase())) {
						event.add(e);
					}
				}
				events = event;
			} 

			List<Pair<Event, String>> eventStatus = new ArrayList<Pair<Event, String>>();
			for (Event e : events) {
				if (e.getAttendees().contains(user_name)) {
					eventStatus.add((new Pair<Event, String>(e, "Attending")));
				} else if (e.getOrganiser_id() != null && e.getOrganiser_id().equals(user_name)) {
					eventStatus.add(new Pair<Event, String>(e, "Organising"));
				} else {
					eventStatus.add(new Pair<Event, String>(e, "Nothing"));
				}
			}

			PrintWriter printout = response.getWriter();
			JSONArray jArray = new JSONArray();
			try {
				for (Pair<Event, String> pair : eventStatus) {
					JSONObject jObject = new JSONObject();
					jObject.put("event_id", pair.getA().getId());
					if (pair.getA().getName() == null) {
						jObject.put("event_name", "");
					} else {
						jObject.put("event_name", pair.getA().getName());
					}
					jObject.put("status", pair.getB());
					jArray.put(jObject);
				}
			} catch (JSONException excep) {
				System.out.println("JSON Exception");
			}
			
			printout.print(jArray);
			printout.flush();
		} catch (JSONException main_excp) {
			System.out.println("INVALID JSON OBJECT !!");
			response.sendError(HttpServletResponse.SC_BAD_GATEWAY);
		}

	}
}
