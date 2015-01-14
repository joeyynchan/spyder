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

/**
 * Servlet implementation class DeAllocateUserServlet
 */
@WebServlet("/UnlinkUser")
public class UnlinkUserServlet extends HttpServlet {
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
		

		System.out.println("Unlinked called");

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "nocache");
		response.setCharacterEncoding("utf-8");

		try {
			JSONObject jsonObj = new JSONObject(json);
			String user_name = (String) jsonObj.get("user_name");
			String password = (String) jsonObj.get("password");

			MongoClient mongo = (MongoClient) request.getServletContext()
					.getAttribute("MONGO_CLIENT");
			MongoDBUsersDAO muDAO = new MongoDBUsersDAO(mongo);
			User login_user = muDAO.getUserByName(user_name);
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

			if (login_user != null && login_user.getMacAddress() == null) {
				System.out.println("Trying to unlink: "  + login_user);
				
				System.out.println("UNLINK FAIL !! " + login_user.getUserName());
				operation = true;
				response.sendError(HttpServletResponse.SC_OK);

			} else if (login_user != null
					&& login_user.getPassword().endsWith(encrypted_pass)) {
				System.out.println("UNLINK SUCCESS !! " + login_user.getUserName());
				login_user.setMacAddress(null);
				login_user.setGCM(null);
				muDAO.updateUser(login_user);
				operation = true;
				response.sendError(HttpServletResponse.SC_OK);

			}

			PrintWriter printout = response.getWriter();
			JSONObject JObject = new JSONObject();
			try {
				JObject.put("res", operation);
			} catch (JSONException excep) {
				System.out.println("JSON Exception");
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}

			printout.print(JObject);
			printout.flush();
		} catch (JSONException main_excp) {
			System.out.println("INVALID JSON OBJECT-Unlink !!");
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

		} catch (NoSuchAlgorithmException e) {
			System.out.println("Encryption error in unlink !");
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
	}

}
