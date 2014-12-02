package com.journaldev.mongodb.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.journaldev.mongodb.dao.MongoDBEventDAO;
import com.journaldev.mongodb.model.Event;
import com.mongodb.MongoClient;

@WebServlet("/addEvent")
public class AddEventServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(
				request.getInputStream()));
		StringBuilder jsonBuilder = new StringBuilder();
		if (br != null) {
			String nextLine = br.readLine();
			while (nextLine != null) {
				jsonBuilder.append(nextLine);
				nextLine = br.readLine();
			}
		}
		String json = jsonBuilder.toString();
		System.out.println("json: " + json);
		
		JSONObject jObj;
		try {
			jObj = new JSONObject(json);
		
		String start_time = (String) jObj.get("start_time");
		String end_time = (String) jObj.get("end_time");
		String address = (String) jObj.get("address");
		String name = (String) jObj.get("name");
		String description = (String) jObj.get("description");
		String speaker_id = (String) jObj.get("speaker_id");
		String organiser_id = (String) jObj.get("organiser_id");
		JSONArray attendeesParam = (JSONArray) jObj.get("attendees");
		if (attendeesParam == null) {
			attendeesParam = new JSONArray();
		}
		Set<String> attendees = new HashSet<String>();
		for (int i = 0; i < attendeesParam.length(); i++) {
			attendees.add((String) attendeesParam.getJSONObject(i).get("user_name"));
		}
		
		Event e = new Event(start_time, end_time, address, name, description,
				speaker_id, organiser_id, attendees);
		MongoClient mongo = (MongoClient) request.getServletContext()
				.getAttribute("MONGO_CLIENT");
		MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
		List<Event> allEvents = eventDAO.readAllEvent();

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "nocache");
		response.setCharacterEncoding("utf-8");

		PrintWriter printout = response.getWriter();

		for (Event event : allEvents) {
			if (event.getName() != null && event.getName().equals(name)) {

				response.sendError(HttpServletResponse.SC_CONFLICT);
				JSONObject JObject = new JSONObject();
				try {
					JObject.put("start_time", event.getStart_time());
					JObject.put("end_time", event.getEnd_time());
					JObject.put("address", event.getAddress());
					JObject.put("name", event.getName());
					JObject.put("description", event.getDescription());
					JObject.put("speaker_id", event.getSpeaker_id());
					JObject.put("organiser_id", event.getOrganiser_id());
					JObject.put("attendees", event.getAttendees());
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				printout.print(JObject);
				printout.flush();
				return;

			}
		}
		eventDAO.createEvent(e);
		System.out.println("Event Added Successfully with id=" + e.getId());

		JSONObject JObject = new JSONObject();
		try {
			JObject.put("Response", "1");
			JObject.put("event_id", e.getId());
		} catch (JSONException excep) {

		}
		try {
			JObject.put("Message", "Event " + e.getId() + " added successfully");
		} catch (JSONException excep) {

		}

		response.sendError(HttpServletResponse.SC_CREATED);

		printout.print(JObject);
		printout.flush();

		} catch (JSONException e2) {
			System.out.println("INVALUED JSON");
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
	}
}
