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
			User mu = new User();
			mu.setUserName(user_name);
			mu.setPassword(password);
			MongoClient mongo = (MongoClient) request.getServletContext()
					.getAttribute("MONGO_CLIENT");
			MongoDBUsersDAO muDAO = new MongoDBUsersDAO(mongo);

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

            response.sendError(HttpServletResponse.SC_CREATED);

			printout.print(JObject);
			printout.flush();
			
		} catch (JSONException exp) {
			System.out.println("INVALID JSON OBJECT !!");
		}

	}

    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
            MongoClient mongo = (MongoClient) request.getServletContext()
                    .getAttribute("MONGO_CLIENT");
            MongoDBUsersDAO muDAO = new MongoDBUsersDAO(mongo);

            response.setContentType("application/json");
            response.setHeader("Cache-Control", "nocache");
            response.setCharacterEncoding("utf-8");

            User user = muDAO.getUserByName(user_name);

            if (user == null || !user.getPassword().equals(password)) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            muDAO.deleteUser(user);

            response.sendError(HttpServletResponse.SC_NO_CONTENT);

        } catch (JSONException exp) {
            System.out.println("INVALID JSON OBJECT !!");
        }

    }


}
