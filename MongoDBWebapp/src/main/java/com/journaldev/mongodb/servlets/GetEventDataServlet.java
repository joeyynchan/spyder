package com.journaldev.mongodb.servlets;

import java.io.IOException;
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

/**
 * Servlet implementation class GetEventDataServlet
 */
@WebServlet("/event_data")
public class GetEventDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetEventDataServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String event_id = request.getParameter("event_id");

		MongoClient mongo = (MongoClient) request.getServletContext()
				.getAttribute("MONGO_CLIENT");

		MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);

		List<Event> events = eventDAO.readAllEvent();
		Event event = null;
		if (event_id != null && !event_id.equals("")) {
			for (Event e : events) {
				if (e.getId().equals(event_id)) {
					System.out.println("Found event !");
					event = e;
				}
			}
		}

		
			PrintWriter printout = response.getWriter();
			JSONObject jObject = new JSONObject();
			if (event == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			} else {
				try {
				jObject.put("id", event.getId());
				jObject.put("address", event.getAddress());
				jObject.put("description", event.getDescription());
				jObject.put("end_time", event.getEnd_time());
				jObject.put("start_time", event.getStart_time());
				jObject.put("organiser", event.getOrganiser_id());
				jObject.put("speaker", event.getSpeaker_id());
				jObject.put("name", event.getName());
				jObject.put("attendees", event.getAttendees());

				printout.print(jObject);
				printout.flush();
			}catch (JSONException excep) {
				System.out.println("JSON Exception");
			}
		} 
	}
}
