package com.journaldev.mongodb.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

		BufferedReader br = new BufferedReader(new InputStreamReader(
				request.getInputStream()));
		String json = "";
		if (br != null) {
			json = br.readLine();
		}
		try {
			JSONObject jsonObj = new JSONObject(json);
			System.out.println(json);
			String user_name = (String) jsonObj.get("user_name");
			String event_id = (String) jsonObj.get("event_id");

			MongoClient mongo = (MongoClient) request.getServletContext()
					.getAttribute("MONGO_CLIENT");
			MongoDBUsersDAO userDAO = new MongoDBUsersDAO(mongo);

			boolean user_attended = false;
			MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
			Set<String> user_name_list = eventDAO.getAllUsersIDEvent(event_id);
			Set<User> user_list = userDAO.getAllUsers(user_name_list);

			for (User user : user_list) {
				if (user.getUserName().equals(user_name)) {
					user_attended = true;
					break;
				}
			}

			if (userDAO.getUserByName(user_name) != null && user_attended) {

				System.out.println("USER HAS ATTENDED");
				JSONArray data = (JSONArray) jsonObj.get("data");
				Integer time_interval = Integer.parseInt((String) jsonObj
						.get("time_interval"));

				List<List<Pair<String, Integer>>> strengths = new ArrayList<List<Pair<String, Integer>>>();
				for (int i = 0; i < data.length(); i++) {
					List<Pair<String, Integer>> inner_strengths = new ArrayList<Pair<String, Integer>>();
					JSONArray inner_data = (JSONArray) data.get(i);
					for (int j = 0; j < inner_data.length(); j++) {
						inner_strengths.add(new Pair<String, Integer>(
								inner_data.getJSONObject(j).getString(
										"user_name"), inner_data.getJSONObject(
										j).getInt("strength")));
					}
					strengths.add(inner_strengths);
				}

				Data interaction_data = new Data(user_name, event_id,
						time_interval, strengths);
				MongoDBDataDAO dataDAO = new MongoDBDataDAO(mongo);
				dataDAO.createData(interaction_data);
				success = true;

			} else {
				success = false;
			}

		} catch (JSONException exp) {
			System.out.println("INVALID JSON OBJECT !!");
			exp.printStackTrace();
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
