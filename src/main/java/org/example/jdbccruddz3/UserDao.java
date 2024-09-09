package org.example.jdbccruddz3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public static Connection connectDB() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/course", "root", "Artem08082003");
        } catch (Exception message) {
            System.out.println(message);
        }
        return connection;
    }

    public static int addUser(User user) throws SQLException {
        int result = 0;
        Connection connect = UserDao.connectDB();

        PreparedStatement preparedStatement = connect.prepareStatement("insert into course.user(name,surname,age) values (?,?,?)");

        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setInt(3, user.getAge());

        result = preparedStatement.executeUpdate();

        connect.close();

        return result;
    }
    public static int updateUser(User user) throws SQLException {
        int result = 0;

        Connection connect = UserDao.connectDB();

        PreparedStatement preparedStatement = connect.prepareStatement("update course.user set name=?,surname=?,age=? where id=?");

        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setInt(3, user.getAge());
        preparedStatement.setInt(4, user.getId());

        result = preparedStatement.executeUpdate();

        connect.close();

        return result;
    }

    public static User getUserById(int id) throws SQLException {
        User user = new User();

        Connection connect = UserDao.connectDB();

        PreparedStatement preparedStatement = connect.prepareStatement("select * from course.user where id=?");

        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            user.setId(resultSet.getInt(1));
            user.setName(resultSet.getString(2));
            user.setSurname(resultSet.getString(3));
            user.setAge(resultSet.getInt(4));
        }

        connect.close();
        return user;
    }

    public static int deleteUser(int id) throws SQLException {
        int result = 0;

        Connection connect = UserDao.connectDB();

        PreparedStatement preparedStatement = connect.prepareStatement("delete from course.user where id =?");
        preparedStatement.setInt(1, id);

        result = preparedStatement.executeUpdate();

        connect.close();

        return result;
    }

    public static List<User> getAllUsers() throws SQLException {

        List<User> list = new ArrayList<User>();

        Connection connect = UserDao.connectDB();

        PreparedStatement preparedStatement = connect.prepareStatement("select * from course.user");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt(1));
            user.setName(resultSet.getString(2));
            user.setSurname(resultSet.getString(3));
            user.setAge(resultSet.getInt(4));
            list.add(user);
        }

        connect.close();

        return list;
    }
}
