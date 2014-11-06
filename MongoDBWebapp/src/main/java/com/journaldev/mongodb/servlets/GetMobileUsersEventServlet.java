package com.journaldev.mongodb.servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.journaldev.mongodb.dao.MongoDBMobileUsersDAO;
import com.journaldev.mongodb.model.MobileUser;
import com.mongodb.MongoClient;

@WebServlet("/eventUsers")
public class GetMobileUsersEventServlet extends HttpServlet {

	private static final long serialVersionUID = -6554920927964049383L;

	protected void doPost(HttpServletRequest request,
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
		MongoDBMobileUsersDAO mobileDAO = new MongoDBMobileUsersDAO(mongo);

		List<String> user_id_list = eventDAO.getAllUsersIDEvent(event_id);
		List<MobileUser> user_list = mobileDAO.getAllUsers(user_id_list);

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "nocache");
		response.setCharacterEncoding("utf-8");

		PrintWriter printout = response.getWriter();

		JSONArray ja = new JSONArray();
		for (MobileUser mob_user : user_list) {
			JSONObject jo = new JSONObject();
			try {
				jo.put("mac_address", mob_user.getMacAddress());
				jo.put("user_id", mob_user.getId());
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
