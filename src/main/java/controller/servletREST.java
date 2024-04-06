/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Video;

/**
 *
 * @author alumne
 */
@WebServlet(name = "servletREST", urlPatterns = {"/servletREST"})
public class servletREST extends HttpServlet {


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       String action = request.getParameter("action");

        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing");
            return;
        }
        switch (action) {
            case "addVisualization":
                addVisualization(request, response);
                break;
            case "fetchFilterVideos":
                fetchFilterVideos(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
                break;
        }
    }
    
     private void addVisualization(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            String title = request.getParameter("title");
            String author = request.getParameter("author");

            // Make an HTTP POST request to the REST endpoint
            URL url = new URL("http://localhost:8080/ISDCMRest/resources/jakartaee9/updateReproductions");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Construct JSON payload
            String jsonInputString = "{\"title\": \"" + title + "\", \"author\": \"" + author + "\"}";

            // Send JSON payload
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            System.out.println(responseCode);

            conn.disconnect();

            // Check if the request was successful
            if (responseCode == HttpURLConnection.HTTP_OK) {
                request.getRequestDispatcher("home.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }
     
     private List<File> getAllVideoFiles(String folderPath) {
        List<File> videoFiles = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    videoFiles.add(file);
                }
            }
        }
        return videoFiles;
    }
    private void fetchFilterVideos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            System.out.println("Entro");
            Map<String, AbstractMap.SimpleEntry<Video, File>> videoMap = new HashMap<>();
            
            String videosFolderPath = "/home/alumne/WebAppVideos";
            
            String searchEndpoint = "http://localhost:8080/ISDCMRest/resources/jakartaee9/searchVideo"; // Change this to your actual endpoint
            
            String filter = request.getParameter("filter");
            String value = request.getParameter("value");
            
            String requestBody = "{\"filter\": \"" + filter + "\", \"value\": \"" + value + "\"}";
            
            // Send POST request to searchVideo endpoint
            URL url = new URL(searchEndpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Check if the response is successful (status code 2xx)
            int responseCode = conn.getResponseCode();
            System.out.println(responseCode);
            if (responseCode >= 200 && responseCode < 300) {
                // Read response
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
                reader.close();

                List<Video> videoMetadataList = new ArrayList<>();
                
                String jsonResponse = responseBuilder.toString();
                
                JsonArray jsonArray = Json.createReader(new StringReader(jsonResponse)).readArray();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonObject = jsonArray.getJsonObject(i);
                    Video video = new Video(
                                jsonObject.getString("title"),
                                jsonObject.getString("author"),
                                jsonObject.getString("duration"),
                                jsonObject.getString("description"),
                                jsonObject.getString("format"),
                                jsonObject.getString("path"),
                                jsonObject.getInt("reproductions"),
                                jsonObject.getInt("id"),
                                jsonObject.getString("creationDate")
                        );
                    
                    videoMetadataList.add(video);
                }
                
                List<File> fileVideos = getAllVideoFiles(videosFolderPath);

                

                if (videoMetadataList != null && fileVideos != null){
                    
                    for (Video videoMetadata : videoMetadataList) {
                        String key = videoMetadata.getTitle() + "_" + videoMetadata.getAuthor();
                        AbstractMap.SimpleEntry<Video, File> entry = new AbstractMap.SimpleEntry<>(videoMetadata, null);
                        videoMap.put(key, entry);
                    }

                    for (File file : fileVideos) {
                        String fileName = file.getName();
                        String titleAuthor = fileName.substring(0, fileName.lastIndexOf('.'));
                        AbstractMap.SimpleEntry<Video, File> entry = videoMap.get(titleAuthor);
                        if (entry != null) {
                            entry.setValue(file);
                        }
                    }
                }
                request.setAttribute("videoMap", videoMap);
            } else {
                // Handle error response
                System.out.println("Error in rest request");
            }
            request.getRequestDispatcher("filterVid.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }
    


}
