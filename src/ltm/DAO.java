/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ltm;

import model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author huuan
 */
public class DAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/ltm?characterEncoding=UTF-8&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";
    private static final String LOGIN = "select * from user where username=? and password=?";
    private static final String UPDATE_SCORE = "update user set point = ?,win=? where id = ?;";
    private static final String SELECT_ALL = "select * from user";
    private static final String SELECT_ALL_WIN = "select * from user order by win desc";
    private static final String SELECT_ALL_POINT = "select * from user order by point desc";
    private static final String UPDATE_STATUS = "update user set status = ? where id = ?;";
    private static final String SELECTUSER = "select * from user where username=?";

    
    public DAO() {
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    public User logIn(String username, String password) {
        User user = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
                // Step 2:Create a statement using connection object
                PreparedStatement preparedStatement = connection.prepareStatement(LOGIN);) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String userName = rs.getString("username");
                String passWord = rs.getString("password");
                double point = rs.getDouble("point");
                int win = rs.getInt("win");
                String status = rs.getString("status");
                int id = rs.getInt("id");
                user = new User(id, userName, passWord, point, win, status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public User selectUser(String username) {
        User user = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
                // Step 2:Create a statement using connection object
                PreparedStatement preparedStatement = connection.prepareStatement(SELECTUSER);) {
            preparedStatement.setString(1, username);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String userName = rs.getString("username");
                String passWord = rs.getString("password");
                double point = rs.getDouble("point");
                int win = rs.getInt("win");
                String status = rs.getString("status");
                int id = rs.getInt("id");
                user = new User(id, userName, passWord, point, win, status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean updateScore(User user) throws SQLException {
        boolean rowUpdated;
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_SCORE);
        statement.setDouble(1, user.getPoint());
        statement.setInt(2, user.getWin());
        statement.setInt(3, user.getId());
        System.out.println(statement);
        rowUpdated = statement.executeUpdate() > 0;
        return rowUpdated;
    }
    
    public boolean updateStatus(User user,String status) throws SQLException {
        boolean rowUpdated;
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_STATUS);
        statement.setString(1,status );
        statement.setInt(2, user.getId());
        System.out.println(statement);
        rowUpdated = statement.executeUpdate() > 0;
        return rowUpdated;
    }

    public ArrayList<User> selectAllUser() {

        // using try-with-resources to avoid closing resources (boiler plate code)
        ArrayList<User> users = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
                // Step 2:Create a statement using connection object
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                double point = rs.getDouble("point");
                int win = rs.getInt("win");
                String password = rs.getString("password");
                String status = rs.getString("status");
                users.add(new User(id, username, password, point, win, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    public ArrayList<User> selectAllUserWinDesc() {

        // using try-with-resources to avoid closing resources (boiler plate code)
        ArrayList<User> users = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
                // Step 2:Create a statement using connection object
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WIN);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                double point = rs.getDouble("point");
                int win = rs.getInt("win");
                String password = rs.getString("password");
                String status = rs.getString("status");
                users.add(new User(id, username, password, point, win, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    public ArrayList<User> selectAllUserPointDesc() {

        // using try-with-resources to avoid closing resources (boiler plate code)
        ArrayList<User> users = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
                // Step 2:Create a statement using connection object
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_POINT);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                double point = rs.getDouble("point");
                int win = rs.getInt("win");
                String password = rs.getString("password");
                String status = rs.getString("status");
                users.add(new User(id, username, password, point, win, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

}
