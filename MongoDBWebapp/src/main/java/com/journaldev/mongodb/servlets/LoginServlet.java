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

import com.journaldev.mongodb.dao.MongoDBUsersDAO;
import com.journaldev.mongodb.model.User;
import com.mongodb.MongoClient;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		boolean operation = false;
		String user_name = request.getParameter("user_name");
		String password = request.getParameter("password");
		String mac_address = request.getParameter("mac_address");

		MongoClient mongo = (MongoClient) request.getServletContext()
				.getAttribute("MONGO_CLIENT");
		MongoDBUsersDAO muDAO = new MongoDBUsersDAO(mongo);
		User login_user = muDAO.getUserByQuery(user_name, password);

		if (login_user != null) {
			login_user.setMacAddress(mac_address);
			muDAO.updateUser(login_user);
			operation = true;
		}

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "nocache");
		response.setCharacterEncoding("utf-8");

		PrintWriter printout = response.getWriter();
		JSONObject JObject = new JSONObject();
		try {
			JObject.put("res", operation);
		} catch (JSONException excep) {
			System.out.println("JSON Exception");
		}

		printout.print(JObject);
		printout.flush();
	}

}
