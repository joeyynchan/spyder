package com.journaldev.mongodb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.journaldev.mongodb.dao.MongoDBProfileDAO;
import com.journaldev.mongodb.dao.MongoDBUsersDAO;
import com.journaldev.mongodb.model.Profile;
import com.journaldev.mongodb.model.User;
import com.mongodb.MongoClient;

@WebServlet("/eventUsers")
public class GetUsersEventServlet extends HttpServlet {

	private static final long serialVersionUID = -6554920927964049383L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String event_id = request.getParameter("event_id");
		System.out.println(event_id);
		if (event_id == null || "".equals(event_id)) {
			throw new ServletException("id missing for edit operation");
		}
		System.out.println("Conference Requested with " + event_id);
		MongoClient mongo = (MongoClient) request.getServletContext()
				.getAttribute("MONGO_CLIENT");
		MongoDBEventDAO eventDAO = new MongoDBEventDAO(mongo);
		MongoDBUsersDAO mobileDAO = new MongoDBUsersDAO(mongo);
		MongoDBProfileDAO profileDAO = new MongoDBProfileDAO(mongo);
		
		Set<String> user_name_list = eventDAO.getAllUsersIDEvent(event_id);
		System.out.println(user_name_list);
		Set<User> user_list = mobileDAO.getAllUsers(user_name_list);
		System.out.println(user_list);

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "nocache");
		response.setCharacterEncoding("utf-8");

		PrintWriter printout = response.getWriter();

		JSONArray ja = new JSONArray();
		for (User mob_user : user_list) {
			Profile userProfile = profileDAO.readProfileByName(mob_user.getUserName());
			JSONObject jo = new JSONObject();
			try {
				String mac_address = mob_user.getMacAddress() == null ? "" : mob_user.getMacAddress();
				jo.put("mac_address", mac_address);
				jo.put("user_name", mob_user.getUserName());
				if (userProfile != null && userProfile.getName() != null) {
					jo.put("name", userProfile.getName());
				} else {
					jo.put("name", mob_user.getUserName());
				}
			} catch (JSONException e) {
			}
			ja.put(jo);
		}
		JSONObject mainObj = new JSONObject();
		try {
			mainObj.put("user_mappings", ja);
		} catch (JSONException e) {
		}

		printout.print(mainObj);
		printout.flush();

	}
}
