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

@WebServlet("/user/profile")
public class GetUserProfileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response) {

        String user_id = request.getParameter("user_id");

        try {
            response.setContentType("application/json");
            response.setHeader("Cache-Control", "nocache");
            response.setCharacterEncoding("utf-8");

            PrintWriter printout = response.getWriter();
            JSONObject JObject = new JSONObject();
            try {
                JObject.put("name", "");
                JObject.put("job", "");
                JObject.put("company", "");
                JObject.put("photo", "");
                JObject.put("email", "");
                JObject.put("phone", "");
                JObject.put("external_link", "");
                JObject.put("connections", "");
                JObject.put("gender", "");

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
