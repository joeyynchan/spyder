package com.journaldev.mongodb.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.journaldev.mongodb.dao.MongoDBProfileDAO;
import com.journaldev.mongodb.dao.MongoDBUsersDAO;
import com.journaldev.mongodb.model.Profile;
import com.mongodb.MongoClient;

@WebServlet("/user/connections")
public class EditUserConnectionsServlet extends HttpServlet {

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
                	if (profile.getConnections().isEmpty()) {
                		JObject.put("connections","[]");
                	} else {
            JObject.put("connections", profile.getConnections().toString());
                	}
                } catch (JSONException excep) {
                        System.out.println("JSON Exception");
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
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
            String operation = (String) jsonObj.get("operation");
            JSONArray connectionArray = (JSONArray) jsonObj.get("connections");
            List<String> connections = new ArrayList<String>();

            for (int i = 0; i < connectionArray.length(); i++) {
                connections.add(connectionArray.getString(i));
            }

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

            if (operation.equals("add")) {
                Profile profile = profileDAO.readProfileByName(user_id);
                if (profile == null) {
                    Profile newProfile = new Profile(user_id, "", "","", "",
                            "", "", "", "", new ArrayList<String>());
                    profileDAO.createProfile(newProfile);
                    profile = newProfile;
                }

                List<String> existing = new ArrayList<String>();
                existing.addAll(toArray(profile.getConnections()));
                existing.addAll(connections); 

                profile.setConnections(existing);
                profileDAO.updateProfile(profile);

                for (String connection : connections) {
                        if (userDAO.getUserByName(connection) != null) {
                                Profile profile1 = profileDAO.readProfileByName(connection);
                        if (profile1 == null) {
                            Profile newProfile = new Profile(connection, "", "","", "",
                                    "", "", "", "", new ArrayList<String>());
                            profileDAO.createProfile(newProfile);
                            profile1 = newProfile;
                        }

                        List<String> existing1 = new ArrayList<String>();
                        existing1.addAll(toArray(profile1.getConnections()));
                        existing1.add(user_id);
                        profile1.setConnections(existing1);
                        profileDAO.updateProfile(profile1);

                        }
                }
            } else if (operation.equals("delete")) {
                Profile profile = profileDAO.readProfileByName(user_id);
                if (profile == null) {
                    response.sendError(HttpServletResponse.SC_NO_CONTENT);
                    return;
                }

                List<String> existing = new ArrayList<String>();
                existing.addAll(profile.getConnections());
                existing.removeAll(connections);
                profile.setConnections(existing);
                profileDAO.updateProfile(profile);

                for (String connection : connections) {
                        if (userDAO.getUserByName(connection) != null) {
                                Profile profile1 = profileDAO.readProfileByName(connection);

                        List<String> existing1 = profile1.getConnections();
                        existing1.remove(user_id);
                        profile1.setConnections(existing1);
                        profileDAO.updateProfile(profile1);

                        }
                }
            }

        } catch (JSONException exp) {
            System.out.println("INVALID JSON OBJECT !!");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

	private Collection<? extends String> toArray(List<String> connections) {
		String[] list = new String[0];
		if (!connections.isEmpty()) {
			list = connections.get(0).replace("[", "").replace("]", "").split(",");
		}
		return Arrays.asList(list);
	}

}
