package com.journaldev.mongodb.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.journaldev.mongodb.dao.MongoDBProfileDAO;
import com.journaldev.mongodb.dao.MongoDBUsersDAO;
import com.journaldev.mongodb.model.Profile;
import com.journaldev.mongodb.model.User;
import com.journaldev.mongodb.utils.Encryption;
import com.mongodb.MongoClient;

@WebServlet("/register")
public class AddUserServlet extends HttpServlet {

	private static final long serialVersionUID = -7060758261496829905L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

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
			String password = (String) jsonObj.get("password");
			if(user_name.equals("")){
				response.sendError(410);
				return;
			}
			if(password.equals("")){
				response.sendError(411);
				return;
			}
			String salt = Encryption.generate_salt();
			User mu = new User();
			mu.setUserName(user_name);
			mu.setPassword(Encryption.sha1_encypt(password+salt));
			mu.set_salt(salt);
			MongoClient mongo = (MongoClient) request.getServletContext() 
					.getAttribute("MONGO_CLIENT");
			MongoDBUsersDAO muDAO = new MongoDBUsersDAO(mongo);
			MongoDBProfileDAO profileDAO = new MongoDBProfileDAO(mongo);

            response.setContentType("application/json");
            response.setHeader("Cache-Control", "nocache");
            response.setCharacterEncoding("utf-8");

            if (muDAO.getUserByName(user_name) != null) {
                response.sendError(HttpServletResponse.SC_CONFLICT);
                return;
            }

			mu.setId(muDAO.createUser(mu).getId());
			System.out.println("id " + mu.getId());

			PrintWriter printout = response.getWriter();

			JSONObject JObject = new JSONObject();
			try {
				JObject.put("UserID", mu.getId());
			} catch (JSONException excep) {

			}

			Profile profile = new Profile(user_name, user_name, null, null, null, null, null, null, null, new ArrayList<String>());
			profileDAO.createProfile(profile);
			
            response.sendError(HttpServletResponse.SC_CREATED);

			printout.print(JObject);
			printout.flush();
			
		} catch (JSONException exp) {
			System.out.println("INVALID JSON OBJECT !!");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Encryption error");
			e.printStackTrace();
		}

	}

}
