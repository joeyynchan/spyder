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

import com.journaldev.mongodb.dao.MongoDBMobileUsersDAO;
import com.journaldev.mongodb.model.MobileUser;
import com.mongodb.MongoClient;

@WebServlet("/register_mac")
public class AddMobileUserServlet extends HttpServlet {

	private static final long serialVersionUID = -7060758261496829905L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String user_name = request.getParameter("user_name");
		String mac_address = request.getParameter("mac_address");
		MobileUser mu = new MobileUser();
		mu.setUserName(user_name);
		mu.setMacAddress(mac_address);
		MongoClient mongo = (MongoClient) request.getServletContext()
				.getAttribute("MONGO_CLIENT");
		MongoDBMobileUsersDAO muDAO = new MongoDBMobileUsersDAO(mongo);
		muDAO.createMobileUser(mu);
		System.out.println("Person Added Successfully with id=" + mu.getId());
		
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "nocache");
		response.setCharacterEncoding("utf-8");

		PrintWriter printout = response.getWriter();

		JSONObject JObject = new JSONObject();
		try {
			JObject.put("MobileUserID", mu.getId());
		} catch (JSONException excep) {
			
		}

		printout.print(JObject);
		printout.flush();

	}

}
