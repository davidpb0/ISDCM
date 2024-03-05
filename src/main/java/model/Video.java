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
public class Video {
    private int id;  // Assuming id is a numeric type in the database
    private String title;
    private String autor;
    private String creationDate;
    private String durations;
    private int reproductions;  // Assuming reproductions is a numeric type in the database
    private String description;
    private String format;

    // Constructor
    public Video(int id, String title, String autor, String creationDate, String durations, int reproductions, String description, String format) {
        this.id = id;
        this.title = title;
        this.autor = autor;
        this.creationDate = creationDate;
        this.durations = durations;
        this.reproductions = reproductions;
        this.description = description;
        this.format = format;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getDurations() {
        return durations;
    }

    public void setDurations(String durations) {
        this.durations = durations;
    }

    public int getReproductions() {
        return reproductions;
    }

    public void setReproductions(int reproductions) {
        this.reproductions = reproductions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    // Get all videos in DB
    public static List<Video> getAllVideos() {
        List<Video> videoList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2", "pr2", "pr2")) {
            String query = "SELECT * FROM videos";
            try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Video video = new Video(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("autor"),
                            resultSet.getString("creationDate"),
                            resultSet.getString("durations"),
                            resultSet.getInt("reproduction"),
                            resultSet.getString("description" ),
                            resultSet.getString("format")
                    );
                    videoList.add(video);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return videoList;
    }
    
    //Save video to DB
    public boolean saveVideo() {
        if (!this.checkVideoExistance()){
            try (Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2", "pr2", "pr2")) {
                String query = "INSERT INTO videos (id, title, autor, creationDate, durations, reproduction, description, format) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setInt(1, this.id);
                    statement.setString(2, this.title);
                    statement.setString(3, this.autor);
                    statement.setString(4, this.creationDate);
                    statement.setString(5, this.durations);
                    statement.setInt(6, this.reproductions);
                    statement.setString(7, this.description);
                    statement.setString(8, this.format);
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
    
    public static Video getVideoByTitle(String title) {
    Video video = null;
    try (Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2", "pr2", "pr2")) {
        String query = "SELECT * FROM videos WHERE title = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, title);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    video = new Video(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("autor"),
                            resultSet.getString("creationDate"),
                            resultSet.getString("durations"),
                            resultSet.getInt("reproduction"),
                            resultSet.getString("description" ),
                            resultSet.getString("format")
                    );
                }
            }
        }
    } catch (SQLException e) {
        System.out.println(e);
    }
    return video;
}
    
public boolean checkVideoExistance() {
    try (Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2", "pr2", "pr2")) {
        String query = "SELECT * FROM video WHERE id = ? OR title = ?";
        try (PreparedStatement checkStatement = connection.prepareStatement(query)) {
            checkStatement.setInt(1, this.id);
            checkStatement.setString(2, this.title);
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
    
public static Video getVideoByAutor(String autor) {
    Video video = null;
    try (Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2", "pr2", "pr2")) {
        String query = "SELECT * FROM videos WHERE autor = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, autor);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    video = new Video(
                            resultSet.getInt("id"),
                            resultSet.getString("title"),
                            resultSet.getString("autor"),
                            resultSet.getString("creationDate"),
                            resultSet.getString("durations"),
                            resultSet.getInt("reproduction"),
                            resultSet.getString("description" ),
                            resultSet.getString("format")
                    );
                }
            }
        }
    } catch (SQLException e) {
        System.out.println(e);
    }
    return video;
}   
    
    
    
}
