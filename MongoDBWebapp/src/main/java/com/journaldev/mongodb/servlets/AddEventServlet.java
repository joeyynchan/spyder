package com.journaldev.mongodb.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");
		String address = request.getParameter("address");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String speaker_id = request.getParameter("speaker_id");
		String organiser_id = request.getParameter("organiser_id");
		String attendees = request.getParameter("attendees");

		Event e = new Event(start_time, end_time, address, name, description,
				speaker_id, organiser_id, attendees);
		MongoClient mongo = (MongoClient) request.getServletContext()
				.getAttribute("MONGO_CLIENT");
		MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
		eventDAO.createEvent(e);
		System.out.println("Event Added Successfully with id=" + e.getId());

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "nocache");
		response.setCharacterEncoding("utf-8");

		PrintWriter printout = response.getWriter();

		JSONObject JObject = new JSONObject();
		try {
			JObject.put("Response", "1");
		} catch (JSONException excep) {

		}
		try {
			JObject.put("Message", "Event " + e.getId() + " added successfully");
		} catch (JSONException excep) {

		}

		printout.print(JObject);
		printout.flush();

	}
}
