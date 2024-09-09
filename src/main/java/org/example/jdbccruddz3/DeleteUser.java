package org.example.jdbccruddz3;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "deleteUser", value = "/deleteUser")
public class DeleteUser extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

        String userId = request.getParameter("id");
        if (userId != null) {
            try {
                int id = Integer.parseInt(userId);
                UserDao.deleteUser(id);
                response.getWriter().write("{\"status\":\"User deleted\"}");
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().write("{\"status\":\"Error deleting user\"}");
            } catch (NumberFormatException e) {
                response.getWriter().write("{\"status\":\"Invalid user ID\"}");
            }
        } else {
            response.getWriter().write("{\"status\":\"User ID not provided\"}");
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
