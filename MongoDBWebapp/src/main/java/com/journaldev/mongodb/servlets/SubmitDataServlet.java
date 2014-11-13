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

@WebServlet("/event/submit_data")
public class SubmitDataServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                         HttpServletResponse response) {

        String user_id = request.getParameter("user_id");
        String event_id = request.getParameter("event_id");

        BufferedReader br = new BufferedReader(new InputStreamReader(
                request.getInputStream()));
        String json = "";
        if (br != null) {
            json = br.readLine();
        }

        try {
            JSONObject jsonObj = new JSONObject(json);
            JSONArray data = (JSONArray) jsonObj.get("data");
            String time_interval = (String) jsonObj.get("time_interval");

            response.setContentType("application/json");
            response.setHeader("Cache-Control", "nocache");
            response.setCharacterEncoding("utf-8");

        } catch (JSONException exp) {
            System.out.println("INVALID JSON OBJECT !!");
        }
    }

}
