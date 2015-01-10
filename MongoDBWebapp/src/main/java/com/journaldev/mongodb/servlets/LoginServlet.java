package com.journaldev.mongodb.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.journaldev.mongodb.dao.MongoDBUsersDAO;
import com.journaldev.mongodb.model.User;
import com.journaldev.mongodb.utils.Encryption;
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
		StringBuilder jsonBuilder = new StringBuilder();
		if (br != null) {
			String nextLine = br.readLine();
			while (nextLine != null) {
				jsonBuilder.append(nextLine);
				nextLine = br.readLine();
			}
		}
		json = jsonBuilder.toString();

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "nocache");
		response.setCharacterEncoding("utf-8");

		try {
			JSONObject jsonObj = new JSONObject(json);
			String user_name = (String) jsonObj.get("user_name");
			String password = (String) jsonObj.get("password");
			if(user_name.equals("")){
				response.sendError(410);
				return;
			}
			if(password.equals("")){
				response.sendError(411);
				return;
			}
			
			String mac_address = (String) jsonObj.get("mac_address");
			String gcm_id = (String) jsonObj.get("gcm_id");
			

			MongoClient mongo = (MongoClient) request.getServletContext()
					.getAttribute("MONGO_CLIENT");
			MongoDBUsersDAO muDAO = new MongoDBUsersDAO(mongo);
			User login_user = muDAO.getUserByName(user_name);
			System.out.println("Login User: " + login_user);

			System.out.println();

			if (login_user == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
			String encrypted_pass = Encryption.sha1_encypt(password
					+ login_user.get_salt());
			if (!login_user.getPassword().endsWith(encrypted_pass)) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}

			if (login_user != null
					&& (login_user.getMacAddress() == null || login_user
							.getGCM() == null)) {
				login_user.setMacAddress(mac_address);
				login_user.setGCM(gcm_id);
				muDAO.updateUser(login_user);
				operation = true;
				response.sendError(HttpServletResponse.SC_CREATED);
			} else if (login_user != null
					&& login_user.getPassword().endsWith(encrypted_pass)
					&& ((!login_user.getGCM().equals(gcm_id) && !gcm_id
							.equals("")) || (!login_user.getMacAddress()
							.equals(mac_address) && !mac_address.equals("")))) {
				response.sendError(HttpServletResponse.SC_CONFLICT);
				return;
			} else if (login_user != null
					&& (login_user.getMacAddress().equals(mac_address) || mac_address
							.equals(""))
					&& (login_user.getGCM().equals(gcm_id) || gcm_id.equals(""))
					&& login_user.getPassword().endsWith(encrypted_pass)) {
				operation = true;
			}

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
		} catch (NoSuchAlgorithmException e) {
			System.out.println("encryption error");
			e.printStackTrace();
		}
	}

}
