/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import model.Video;

/**
 *
 * @author alumne
 */
@WebServlet(name = "serverletRegistroVid", urlPatterns = {"/serverletRegistroVid"})
@MultipartConfig
public class serverletRegistroVid extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet serverletRegistroVid</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet serverletRegistroVid at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        processRequest(request, response);
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
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            // User is logged in, proceed to save video
            String title = request.getParameter("title");
            String author = request.getParameter("authorHidden");
            String duration = request.getParameter("duration");
            String description = request.getParameter("description");
            String format = request.getParameter("formatHidden");
            
            Part filePart = request.getPart("file");
            
            String uploadDirectory = "/home/alumne/WebAppVideos";
            String fileName = title + "_" + author + "." + format;
            String filePath = uploadDirectory + File.separator + fileName;
            
            Video video = new Video(title, author, duration, description, format, filePath);
            
            try (InputStream fileContent = filePart.getInputStream()) {
                Files.copy(fileContent, Paths.get(filePath));
            } catch (IOException e) {
                session.setAttribute("errorMessage", "The video with that title already exist!");
                // Redirect back to the registration page
                response.sendRedirect("registroVid.jsp");
                System.out.println(e);
                return;
            }

            // Save the video
            boolean saved = video.saveVideo();

            if (saved) {
                session.setAttribute("successMessage", "Video saved successfully!");
                // Redirect to home page
                response.sendRedirect("home.jsp");
            } else {
                // Set error message attribute in session
                session.setAttribute("errorMessage", "The video with that title already exist!");
                // Redirect back to the registration page
                response.sendRedirect("registroVid.jsp");
            }
        } else {
            // User is not logged in, redirect to login page
            response.sendRedirect("login.jsp"); // Replace 'login.html' with your actual login page
        }
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
