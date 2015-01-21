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

import com.journaldev.mongodb.dao.MongoDBEventDAO;
import com.journaldev.mongodb.dao.MongoDBProfileDAO;
import com.journaldev.mongodb.dao.MongoDBUsersDAO;
import com.journaldev.mongodb.model.Profile;
import com.journaldev.mongodb.model.User;
import com.journaldev.mongodb.utils.Pair;
import com.mongodb.MongoClient;

@WebServlet("/searchUsers")
public class SearchUserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				request.getInputStream()));
		StringBuilder builder = new StringBuilder("");
		if (br != null) {
			String newLine = br.readLine();
			while(newLine != null) {
				builder.append(newLine);
				newLine = br.readLine();
			}
		}
		String json = builder.toString();
		
		try {
			JSONObject jsonObj = new JSONObject(json);
			String event_id = (String) jsonObj.get("event_id");
			String user_search_string = (String) jsonObj.get("user_search_string");
			
			MongoClient mongo = (MongoClient) request.getServletContext()
					.getAttribute("MONGO_CLIENT");
			MongoDBUsersDAO userDAO = new MongoDBUsersDAO(mongo);
			MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
			MongoDBProfileDAO profileDAO = new MongoDBProfileDAO(mongo);
			
			List<User> users = new ArrayList<User>();
			if (event_id == null || event_id.equals("")) {
				users = userDAO.readAllUsers();
			} else {
				Set<String> userIDs = eventDAO.getAllUsersIDEvent(event_id);
				for (String userID : userIDs) {
					users.add(userDAO.getUserByName(userID));
				}
			}
			
			List<Pair<String, String>> relevantUsers = new ArrayList<Pair<String, String>>();
			if (user_search_string != null && !user_search_string.equals("")) {
				for (User user : users) {
					Profile userProfile = profileDAO.readProfileByName(user.getUserName());
					if (user.getUserName().toLowerCase().contains(user_search_string.toLowerCase())
							|| (userProfile != null && userProfile.getName() != null &&
							userProfile.getName().toLowerCase().contains(user_search_string.toLowerCase()))) {
						relevantUsers.add(new Pair<String, String>(user.getUserName(), userProfile.getName()));
					}
				}
			}
			
			PrintWriter printout = response.getWriter();
			JSONArray jArray = new JSONArray();
			try {
				for (Pair<String, String> user : relevantUsers) {
					JSONObject jObj = new JSONObject();
					jObj.put("user_name", user.getA());
					jObj.put("name", user.getB());
					jArray.put(jObj);
				}
			} catch (JSONException excep) {
				System.out.println("JSON Exception");
				response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
			}
			
			printout.print(jArray);
			printout.flush();
			
		} catch (JSONException e) {
			System.out.println("INVALID JSON");
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
	}
	
}
