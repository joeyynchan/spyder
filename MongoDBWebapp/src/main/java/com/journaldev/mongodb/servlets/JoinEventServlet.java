package com.journaldev.mongodb.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.journaldev.mongodb.dao.MongoDBEventDAO;
import com.journaldev.mongodb.dao.MongoDBUsersDAO;
import com.journaldev.mongodb.model.Event;
import com.mongodb.MongoClient;

@WebServlet("/event/join")
public class JoinEventServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String event_id = request.getParameter("event_id");

		BufferedReader br = new BufferedReader(new InputStreamReader(
				request.getInputStream()));
		StringBuilder jsonBuilder = new StringBuilder();
		while (br != null) {
			String nextLine = br.readLine();
			if (nextLine == null) {
				break;
			}
			jsonBuilder.append(nextLine);
		}
		String json = jsonBuilder.toString();
		System.out.println("json " + json);

		try {
			JSONObject jsonObj = new JSONObject(json);
			System.out.println(jsonObj.toString());
			String user_id = (String) jsonObj.get("user_name");
			MongoClient mongo = (MongoClient) request.getServletContext()
					.getAttribute("MONGO_CLIENT");
			MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
			MongoDBUsersDAO usersDAO = new MongoDBUsersDAO(mongo);
			
			Event event = new Event(null, null, null, null, null, null,null, null);
			event.setId(event_id);
			Event theEvent = eventDAO.readEvent(event);
			if (theEvent == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			} else if (usersDAO.getUserByName(user_id) == null) {
				response.sendError(HttpServletResponse.SC_CONFLICT);
				return;
			} else if (theEvent.getAttendees().contains(user_id)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}

			theEvent.setId(event_id);
			Set<String> attendees = new HashSet<String>(theEvent.getAttendees());
			attendees.add(user_id);
			theEvent.setAttendees(attendees);
			eventDAO.updateEvent(theEvent);
			
			response.setContentType("application/json");
			response.setHeader("Cache-Control", "nocache");
			response.setCharacterEncoding("utf-8");

		} catch (JSONException exp) {
			System.out.println("INVALID JSON OBJECT !!");
		}
	}

}
