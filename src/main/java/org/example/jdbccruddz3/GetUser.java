package org.example.jdbccruddz3;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import java.io.PrintWriter;

@WebServlet(name = "getUser", value = "/getUser")
public class GetUser extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

        List<User> list = null;

        try {
            list = UserDao.getAllUsers();
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter out = response.getWriter()) {
                out.write("{\"status\":\"Error retrieving users\"}");
            }
            return;
        }

        if (list != null) {
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(list);
            try (PrintWriter out = response.getWriter()) {
                out.write(jsonResponse);
            }
        } else {
            try (PrintWriter out = response.getWriter()) {
                out.write("{\"status\":\"No users found\"}");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
