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
		BufferedReader br = new BufferedReader(new InputStreamReader(
				request.getInputStream()));
		String json = "";
		if (br != null) {
			json = br.readLine();
		}
		try {
			JSONObject jsonObj = new JSONObject(json);
			String user_name = (String) jsonObj.get("user_name");
			String password = (String) jsonObj.get("password");
			String mac_address = (String) jsonObj.get("mac_address");

			MongoClient mongo = (MongoClient) request.getServletContext()
					.getAttribute("MONGO_CLIENT");
			MongoDBUsersDAO muDAO = new MongoDBUsersDAO(mongo);
			User login_user = muDAO.getUserByQuery(user_name, password);
			System.out.println(login_user);

			if (login_user != null && login_user.getMacAddress() == null) {
				login_user.setMacAddress(mac_address);
				muDAO.updateUser(login_user);
				operation = true;
			} else if (login_user != null
					&& login_user.getMacAddress().equals(mac_address)
					&& login_user.getPassword().endsWith(password)) {
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
		} catch (JSONException main_excp) {
			System.out.println("INVALID JSON OBJECT !!");
		}
	}

}
