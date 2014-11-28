package com.journaldev.mongodb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
import com.mongodb.MongoClient;

/**
 * Servlet implementation class GetEventServlets
 */
@WebServlet("/getEvents")
public class GetEventServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String user_name = request.getParameter("user_name");
		System.out.println(user_name);
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "nocache");
		response.setCharacterEncoding("utf-8");

        MongoClient mongo = (MongoClient) request.getServletContext()
                .getAttribute("MONGO_CLIENT");
        MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
        MongoDBUsersDAO userDAO = new MongoDBUsersDAO(mongo);

       if (userDAO.getUserByName(user_name) == null) {
    	   response.sendError(HttpServletResponse.SC_NOT_FOUND);
    	   return;
       }

       List<Event> userEvents = new ArrayList<Event>();
        List<Event> events = eventDAO.readAllEvent();
        for (Event e : events) {
        	if (e.getAttendees().contains(user_name) || e.getOrganiser_id().equals(user_name)) {
        		userEvents.add(e);
        	}
        }

		PrintWriter printout = response.getWriter();
		JSONArray jArray = new JSONArray();
		try {
			for (Event e : userEvents) {
				JSONObject eventObj = new JSONObject();
				eventObj.put("event_id", e.getId());
				eventObj.put("name", e.getName());
				eventObj.put("description", e.getDescription());
				eventObj.put("start_time", e.getStart_time());
				eventObj.put("end_time", e.getEnd_time());
				eventObj.put("organiser", e.getOrganiser_id());
				jArray.put(eventObj);
			}
		} catch (JSONException excep) {
			System.out.println("JSON Exception");
		}

		printout.print(jArray);
		printout.flush();
	}

}
