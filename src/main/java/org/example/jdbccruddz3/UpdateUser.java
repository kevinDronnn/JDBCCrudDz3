package org.example.jdbccruddz3;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "updateUser", value = "/updateUser")
public class UpdateUser extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        String userId = request.getParameter("id");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String ageParam = request.getParameter("age");

        PrintWriter out = response.getWriter();
        int status = 0;

        if (userId != null && name != null && surname != null && ageParam != null) {
            try {
                int id = Integer.parseInt(userId);
                int age = Integer.parseInt(ageParam);

                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setSurname(surname);
                user.setAge(age);

                try {
                    status = UserDao.updateUser(user);
                } catch (SQLException e) {
                    e.printStackTrace();
                    out.write("{\"status\":\"Error updating user\"}");
                    return;
                }

                if (status > 0) {
                    out.write("{\"status\":\"User updated successfully\"}");
                } else {
                    out.write("{\"status\":\"Unable to update user\"}");
                }
            } catch (NumberFormatException e) {
                out.write("{\"status\":\"Invalid number format\"}");
            }
        } else {
            out.write("{\"status\":\"Missing required parameters\"}");
        }
    }
}
