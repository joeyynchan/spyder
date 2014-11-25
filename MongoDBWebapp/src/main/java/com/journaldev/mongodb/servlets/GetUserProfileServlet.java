package com.journaldev.mongodb.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;

import com.journaldev.mongodb.*;

import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/user/profile")
public class GetUserProfileServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String user_id = request.getParameter("user_id");

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "nocache");
		response.setCharacterEncoding("utf-8");

        MongoClient mongo = (MongoClient) request.getServletContext()
                .getAttribute("MONGO_CLIENT");
        MongoDBProfileDAO profileDAO = new MongoDBProfileDAO(mongo);
        MongoDBUsersDAO userDAO = new MongoDBUsersDAO(mongo);

        if (userDAO.readUserByName(user_id) == null) {
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

		} catch (JSONException excep) {
			System.out.println("JSON Exception");
		}

		printout.print(JObject);
		printout.flush();
	}

    protected void doPut(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {

        String user_id = request.getParameter("user_id");

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
            String connectionList = jsonObj.get("connections");
            List<String> connections = connectionList.replace('[', '').replace(']','').split(",");

            response.setContentType("application/json");
            response.setHeader("Cache-Control", "nocache");
            response.setCharacterEncoding("utf-8");

            MongoClient mongo = (MongoClient) request.getServletContext()
                    .getAttribute("MONGO_CLIENT");
            MongoDBProfileDAO profileDAO = new MongoDBProfileDAO(mongo);
            MongoDBUsersDAO userDAO = new MongoDBUsersDAO(mongo);

            if (userDAO.readUserByName(user_id) == null) {
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
                profileDAO.updateProfile(newProfile);
                return;
            }

        } catch (JSONException exp) {
            System.out.println("INVALID JSON OBJECT !!");
        }

    }

}
