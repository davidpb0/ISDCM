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
 * @author davidpb0
 */
@WebServlet(name = "serverletRegistroVid", urlPatterns = {"/serverletRegistroVid"})
@MultipartConfig
public class serverletRegistroVid extends HttpServlet {

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
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            String title = request.getParameter("title");
            String author = request.getParameter("authorHidden");
            String duration = request.getParameter("duration");
            String description = request.getParameter("description");
            String format = request.getParameter("formatHidden");
            
            Part filePart = request.getPart("file");
            
            String uploadDirectory = "/home/alumne/WebAppVideos";
            String fileName = title + "_" + author + "." + format;
            String tempFilePath = uploadDirectory + File.separator + "temp_" + fileName;
            String encryptedFilePath = uploadDirectory + File.separator + fileName;

            // Save the uploaded file to a temporary location
            try (InputStream fileContent = filePart.getInputStream()) {
                Files.copy(fileContent, Paths.get(tempFilePath));
            } catch (IOException e) {
                session.setAttribute("errorMessage", "Failed to save the video");
                response.sendRedirect("registroVid.jsp");
                return;
            }

            // Encrypt the video file
            String secretKey = "5Uo7r8YfGhJk2NmP";
            try {
                VideoEncryptionUtil.encryptVideo(tempFilePath, encryptedFilePath, secretKey);
            } catch (Exception e) {
                Files.deleteIfExists(Paths.get(tempFilePath));
                session.setAttribute("errorMessage", "Failed to encrypt the video");
                response.sendRedirect("registroVid.jsp");
                return;
            }
            
            Files.deleteIfExists(Paths.get(tempFilePath));

            
            Video video = new Video(title, author, duration, description, format, encryptedFilePath);
            
            boolean saved = video.saveVideo();

            if (saved) {
               String successMessage = "Video saved successfully!";
               request.setAttribute("successMessage", successMessage);
               request.getRequestDispatcher("home.jsp").forward(request, response);
            } else {
                session.setAttribute("errorMessage", "The video with that title already exist!");
                response.sendRedirect("registroVid.jsp");
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }

}
