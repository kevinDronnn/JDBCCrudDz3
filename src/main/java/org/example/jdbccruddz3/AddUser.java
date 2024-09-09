package org.example.jdbccruddz3;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "saveUser", value = "/saveUser")
public class AddUser extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String ageParam = request.getParameter("age");
        int status = 0;

        // Validate parameters
        if (name != null && surname != null && ageParam != null) {
            try {
                int age = Integer.parseInt(ageParam);
                User user = new User();
                user.setName(name);
                user.setSurname(surname);
                user.setAge(age);

                try {
                    status = UserDao.addUser(user);
                } catch (SQLException e) {
                    e.printStackTrace();
                    response.getWriter().write("{\"status\":\"Error saving user\"}");
                    return;
                }

                if (status > 0) {
                    response.getWriter().write("{\"status\":\"User saved\"}");
                } else {
                    response.getWriter().write("{\"status\":\"Unable to save user\"}");
                }
            } catch (NumberFormatException e) {
                response.getWriter().write("{\"status\":\"Invalid age format\"}");
            }
        } else {
            response.getWriter().write("{\"status\":\"Missing required parameters\"}");
        }
    }
}
