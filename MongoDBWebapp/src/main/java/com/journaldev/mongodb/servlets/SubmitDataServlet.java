package com.journaldev.mongodb.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

import com.journaldev.mongodb.dao.MongoDBDataDAO;
import com.journaldev.mongodb.dao.MongoDBEventDAO;
import com.journaldev.mongodb.dao.MongoDBUsersDAO;
import com.journaldev.mongodb.model.Data;
import com.journaldev.mongodb.model.User;
import com.journaldev.mongodb.utils.Pair;
import com.mongodb.MongoClient;

@WebServlet("/submit_data")
public class SubmitDataServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		boolean success = false;
		String user_name = request.getParameter("user_name");
		String event_id = request.getParameter("event_id");
		
		

		BufferedReader br = new BufferedReader(new InputStreamReader(
				request.getInputStream()));
		String json = "";
		if (br != null) {
			json = br.readLine();
		}

		MongoClient mongo = (MongoClient) request.getServletContext()
				.getAttribute("MONGO_CLIENT");
		MongoDBUsersDAO userDAO = new MongoDBUsersDAO(mongo);
		
		boolean user_attended = false;
		MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
		List<String> user_id_list = eventDAO.getAllUsersIDEvent(event_id);
		List<User> user_list = userDAO.getAllUsers(user_id_list);
		
		for(User user: user_list){
			if(user.getUserName().equals(user_name)){
				user_attended = true;
				break;
			}
		}

		if (userDAO.getUserByName(user_name) != null && user_attended) {
			try {
				JSONObject jsonObj = new JSONObject(json);
				JSONArray data = (JSONArray) jsonObj.get("data");
				Integer time_interval = Integer.parseInt((String) jsonObj.get("time_interval"));

				List<Pair<String, Integer>> strengths = new ArrayList<Pair<String, Integer>>();
				for (int i = 0; i < data.length(); i++) {
					strengths.add(new Pair<String, Integer>(data
							.getJSONObject(i).getString("user_name"), data
							.getJSONObject(i).getInt("strength")));
				}

				Data interaction_data = new Data(user_name, event_id,
						time_interval, strengths);
				MongoDBDataDAO dataDAO = new MongoDBDataDAO(mongo);
				dataDAO.createData(interaction_data);
				success = true;

			} catch (JSONException exp) {
				System.out.println("INVALID JSON OBJECT !!");
				exp.printStackTrace();
			}

		} else {
			success = false;
		}

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "nocache");
		response.setCharacterEncoding("utf-8");

		PrintWriter printout = response.getWriter();
		JSONObject JObject = new JSONObject();
		try {
			JObject.put("res", success);
		} catch (JSONException excep) {
			System.out.println("JSON Exception");
		}

		printout.print(JObject);
		printout.flush();

	}

}
