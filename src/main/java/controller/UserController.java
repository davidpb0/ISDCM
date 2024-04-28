/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.json.Json;
import model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import model.Video;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import java.io.StringReader;
import java.net.URLEncoder;

/**
 *
 * @author davidpb0
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})
public class UserController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
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
        
        if (null == action) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
        }
        else switch (action) {
            case "registerUser":
                registerUser(request, response);
                break;
            case "loginUser":
                loginUser(request, response);
                break;
            case "logoutUser":
                logoutUser(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
                break;
        }
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nickname = request.getParameter("nickname");
        String username = request.getParameter("username");
        String surnames = request.getParameter("surnames");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        User user = new User(nickname, username, surnames, email, password);
        
        if (user.saveUser()){
            HttpSession session = request.getSession(true);
            session.setAttribute("user", nickname);
            response.sendRedirect("home.jsp");
        }
        else {
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "The user with that username or email already exists");
            response.sendRedirect("register.jsp");
        }
        
    }
    
    private void loginUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        
        if (User.authenticateUser(nickname, password)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", nickname);
            
            // Make an HTTP PUT request to the REST endpoint
            URL url = new URL("http://localhost:8080/ISDCMRest/resources/jakartaee9/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // Change content type
            conn.setDoOutput(true);

            // Construct form-urlencoded payload
            String formData = "username=" + URLEncoder.encode(nickname, "UTF-8") +
                              "&password=" + URLEncoder.encode(password, "UTF-8");

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = formData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            System.out.println(responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
                reader.close();
                
                JsonReader jsonReader = Json.createReader(new StringReader(responseBuilder.toString()));
                JsonObject jsonResponse = jsonReader.readObject();
                String jsonToken = jsonResponse.getString("token");
                
                session.setAttribute("JTK", jsonToken);
                conn.disconnect();
                response.sendRedirect("home.jsp");
                
            } else {
                session = request.getSession();
                session.setAttribute("errorMessage", "Invalid username or password");
                response.sendRedirect("login.jsp");
              }
        }
        else {
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "Invalid username or password");
            response.sendRedirect("login.jsp");
        }
    }
    
    private void logoutUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("login.jsp");
    }


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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
