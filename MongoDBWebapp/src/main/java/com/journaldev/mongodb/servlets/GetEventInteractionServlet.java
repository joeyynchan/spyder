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
import javax.swing.*;

import org.json.JSONException;
import org.json.JSONObject;

import com.journaldev.mongodb.dao.MongoDBUsersDAO;
import com.journaldev.mongodb.model.User;
import com.mongodb.MongoClient;

@WebServlet("/event/interaction")
public class GetEventInteractionServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String event_id = request.getParameter("event_id");

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "nocache");
		response.setCharacterEncoding("utf-8");

		PrintWriter printout = response.getWriter();
		JSONObject JObject = new JSONObject();
		try {
			JObject.put("interaction", "");
		} catch (JSONException excep) {
			System.out.println("JSON Exception");
		}

		printout.print(JObject);
		printout.flush();
	}

}
