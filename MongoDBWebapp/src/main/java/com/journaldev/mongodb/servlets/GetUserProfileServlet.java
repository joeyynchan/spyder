package com.journaldev.mongodb.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.journaldev.mongodb.dao.MongoDBProfileDAO;
import com.journaldev.mongodb.dao.MongoDBUsersDAO;
import com.journaldev.mongodb.model.Profile;
import com.mongodb.MongoClient;

@WebServlet("/user/profile")
public class GetUserProfileServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String user_id = request.getParameter("user_name");

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "nocache");
		response.setCharacterEncoding("utf-8");

        MongoClient mongo = (MongoClient) request.getServletContext()
                .getAttribute("MONGO_CLIENT");
        MongoDBProfileDAO profileDAO = new MongoDBProfileDAO(mongo);
        MongoDBUsersDAO userDAO = new MongoDBUsersDAO(mongo);

        System.out.println("get: " + user_id + ": " + userDAO.getUserByName(user_id));
        if (userDAO.getUserByName(user_id) == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        } else if (profileDAO.readProfileByName(user_id) == null) {
            response.sendError(HttpServletResponse.SC_NO_CONTENT);
            return;
        }

        Profile profile = profileDAO.readProfileByName(user_id);

		PrintWriter printout = response.getWriter();
		JSONObject JObject = new JSONObject();
		try {
			JObject.put("name", profile.getName());
			JObject.put("job", profile.getJob());
			JObject.put("company", profile.getCompany());
			JObject.put("photo", profile.getPhoto());
			JObject.put("email", profile.getEmail());
			JObject.put("phone", profile.getPhone());
			JObject.put("external_link", profile.getExternal_link());
			JObject.put("gender", profile.getGender());
            JObject.put("connections", profile.getConnections().toString());
            JObject.put("gcm_id", userDAO.getUserByName(user_id).getGCM());
		} catch (JSONException excep) {
			System.out.println("JSON Exception");
		}

		printout.print(JObject);
		printout.flush();
	}

    protected void doPost(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {

        String user_id = request.getParameter("user_name");

        BufferedReader br = new BufferedReader(new InputStreamReader(
                request.getInputStream()));
        String json = "";
        if (br != null) {
            json = br.readLine();
        }

        try {
            JSONObject jsonObj = new JSONObject(json);
            String name = (String) jsonObj.get("name");
            String job = (String) jsonObj.get("job");
            String company = (String) jsonObj.get("company");
            String photo = (String) jsonObj.get("photo");
            String email = (String) jsonObj.get("email");
            String phone = (String) jsonObj.get("phone");
            String external_link = (String) jsonObj.get("external_link");
            String gender = (String) jsonObj.get("gender");
            String connectionList = (String) jsonObj.get("connections");
            List<String> connections = Arrays.asList(connectionList.replace('[', ' ').replace(']',' ').split(","));

            response.setContentType("application/json");
            response.setHeader("Cache-Control", "nocache");
            response.setCharacterEncoding("utf-8");

            MongoClient mongo = (MongoClient) request.getServletContext()
                    .getAttribute("MONGO_CLIENT");
            MongoDBProfileDAO profileDAO = new MongoDBProfileDAO(mongo);
            MongoDBUsersDAO userDAO = new MongoDBUsersDAO(mongo);

            System.out.println("get: " + user_id + ": " + userDAO.getUserByName(user_id));
            if (userDAO.getUserByName(user_id) == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            Profile profile = profileDAO.readProfileByName(user_id);
            if (profile == null) {
                Profile newProfile = new Profile(user_id, name, job, company, photo,
                        email, phone, external_link, gender, connections);
                profileDAO.createProfile(newProfile);
                response.sendError(HttpServletResponse.SC_CREATED);
            } else {
                Profile newProfile = new Profile(user_id, name, job, company, photo,
                        email, phone, external_link, gender, connections);
                newProfile.setId(profile.getId());
                System.out.println(profile.getId());
                System.out.println(newProfile.getId());
                profileDAO.updateProfile(newProfile);
                response.sendError(HttpServletResponse.SC_CREATED);
                return;
            }

        } catch (JSONException exp) {
            System.out.println("INVALID JSON OBJECT !!");
        }

    }

}
