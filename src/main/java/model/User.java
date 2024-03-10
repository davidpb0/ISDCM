/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alumne
 */
public class User {
    private String nickname;
    private String username;
    private String surnames;
    private String email;
    private String password;
    
    // Constructor
    public User(String nickname, String username, String surnames, String email, String password) {
        this.nickname = nickname;
        this.username = username;
        this.surnames = surnames;
        this.email = email;
        this.password = password;
    }
    
    // Getters & Setters
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Get all users in DB
    public static List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2", "pr2", "pr2")) {
            String query = "SELECT * FROM users";
            try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User(
                            resultSet.getString("nickname"),
                            resultSet.getString("username"),
                            resultSet.getString("surnames"),
                            resultSet.getString("email"),
                            resultSet.getString("password")
                    );
                    userList.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return userList;
    }
    
    //Save user to DB
    public boolean saveUser() {
        if (!this.checkUserExistance()){
            try (Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2", "pr2", "pr2")) {
                String query = "INSERT INTO users (nickname, username, surnames, email, password) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, this.nickname);
                    statement.setString(2, this.username);
                    statement.setString(3, this.surnames);
                    statement.setString(4, this.email);
                    statement.setString(5, this.password);
                    statement.executeUpdate();
                    return true;
                }
            } catch (SQLException e) {
                System.out.println(e);
                return false;
            }
        }
        return false;
    }
    
    public static User getUserByNickname(String nickname) {
    User user = null;
    try (Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2", "pr2", "pr2")) {
        String query = "SELECT * FROM users WHERE nickname = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nickname);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User(
                            resultSet.getString("nickname"),
                            resultSet.getString("username"),
                            resultSet.getString("surnames"),
                            resultSet.getString("email"),
                            resultSet.getString("password")
                    );
                }
            }
        }
    } catch (SQLException e) {
        System.out.println(e);
    }
    return user;
}
    
public boolean checkUserExistance() {
    try (Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2", "pr2", "pr2")) {
        String query = "SELECT * FROM users WHERE email = ? OR username = ?";
        try (PreparedStatement checkStatement = connection.prepareStatement(query)) {
            checkStatement.setString(1, this.email);
            checkStatement.setString(2, this.username);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                    return resultSet.next(); 
            }
            catch (SQLException e) {
                return true;
            }
        }
        
    } catch (SQLException e) {
        System.out.println(e);
    }
        return true;
}
    
public static User getUserByEmail(String email) {
    User user = null;
    try (Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2", "pr2", "pr2")) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User(
                            resultSet.getString("nickname"),
                            resultSet.getString("username"),
                            resultSet.getString("surnames"),
                            resultSet.getString("email"),
                            resultSet.getString("password")
                    );
                }
            }
        }
    } catch (SQLException e) {
        System.out.println(e);
    }
    return user;
}

 public static boolean authenticateUser(String nickname, String password) {        
        String query = "SELECT * FROM users WHERE nickname = ? AND password = ?";
        
        try (Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2", "pr2", "pr2");
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nickname);
            statement.setString(2, password);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            return false; 
        }
    }


    
    
    
    
}
