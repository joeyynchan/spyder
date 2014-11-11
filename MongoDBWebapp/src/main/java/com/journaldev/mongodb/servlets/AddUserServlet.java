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

@WebServlet("/register")
public class AddUserServlet extends HttpServlet {

	private static final long serialVersionUID = -7060758261496829905L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String user_name = request.getParameter("user_name");
		String password = request.getParameter("password");
		User mu = new User();
		mu.setUserName(user_name);
		mu.setPassword(password);
		MongoClient mongo = (MongoClient) request.getServletContext()
				.getAttribute("MONGO_CLIENT");
		MongoDBUsersDAO muDAO = new MongoDBUsersDAO(mongo);
		muDAO.createUser(mu);
		System.out.println("id" + mu.getId());
		
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "nocache");
		response.setCharacterEncoding("utf-8");

		PrintWriter printout = response.getWriter();

		JSONObject JObject = new JSONObject();
		try {
			JObject.put("UserID", mu.getId());
		} catch (JSONException excep) {
			
		}

		printout.print(JObject);
		printout.flush();

	}

}
