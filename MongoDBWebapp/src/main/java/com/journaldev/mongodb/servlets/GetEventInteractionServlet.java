package com.journaldev.mongodb.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;

import org.json.JSONException;
import org.json.JSONObject;

import com.journaldev.mongodb.dao.MongoDBUsersDAO;
import com.journaldev.mongodb.model.User;
import com.mongodb.MongoClient;

@WebServlet("/event/interaction")
public class GetEventInteractionServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String event_id = request.getParameter("event_id");

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "nocache");
		response.setCharacterEncoding("utf-8");

        MongoClient mongo = (MongoClient) request.getServletContext()
                .getAttribute("MONGO_CLIENT");
        MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
        MongoDBDataDAO dataDAO = new MongoDBDataDAO(mongo);

        if (eventDAO.getEventByID(event_id) == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        List<String> event_interactions = dataDAO.getDataForEvent(event_id);

		PrintWriter printout = response.getWriter();
		JSONObject JObject = new JSONObject();
		try {
			JObject.put("interaction", event_interactions.toString());
		} catch (JSONException excep) {
			System.out.println("JSON Exception");
		}

		printout.print(JObject);
		printout.flush();
	}

}
