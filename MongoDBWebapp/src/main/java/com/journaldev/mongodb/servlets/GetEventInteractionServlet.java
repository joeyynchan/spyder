package com.journaldev.mongodb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.journaldev.mongodb.dao.MongoDBEventDAO;
import com.journaldev.mongodb.dao.MongoDBInteractionDAO;
import com.journaldev.mongodb.model.Event;
import com.journaldev.mongodb.model.Interaction;
import com.mongodb.MongoClient;

@WebServlet("/event/interaction")
public class GetEventInteractionServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String event_id = request.getParameter("event_id");

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "nocache");
		response.setCharacterEncoding("utf-8");

        MongoClient mongo = (MongoClient) request.getServletContext()
                .getAttribute("MONGO_CLIENT");
        MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
        MongoDBInteractionDAO interactionDAO = new MongoDBInteractionDAO(mongo);

        Event e = new Event(null, null, null, null, null, null, null, null);
        e.setId(event_id);
        if (eventDAO.readEvent(e) == null) {
        	response.sendError(HttpServletResponse.SC_NOT_FOUND);
        	return;
        }
        e = eventDAO.readEvent(e);

        List<Interaction> event_interactions = interactionDAO.getAllInteraction(event_id);

		PrintWriter printout = response.getWriter();
		JSONObject JObject = new JSONObject();
		JSONArray jArray = new JSONArray();
		try {
			for (Interaction interaction : event_interactions) {
				JSONObject interactionObj = new JSONObject();
				interactionObj.put("event_id", interaction.getEvent_id());
				interactionObj.put("user1", interaction.getUser1());
				interactionObj.put("user2", interaction.getUser2());
				interactionObj.put("start_time", interaction.getStart_time());
				interactionObj.put("end_time", interaction.getEnd_time());
				jArray.put(interactionObj);
			}
			JObject.put("event_start_time", e.getStart_time());
			JObject.put("event_end_time", e.getEnd_time());
			JObject.put("interaction", jArray);
		} catch (JSONException excep) {
			System.out.println("JSON Exception");
		}

		printout.print(JObject);
		printout.flush();
	}

}
