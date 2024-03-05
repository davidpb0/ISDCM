/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Video;

@WebServlet("/servletRegistroVid")
public class ServletRegistroVid extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve data from the form
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String autor = request.getParameter("autor");
        String creationDate = request.getParameter("creationDate");
        String durations = request.getParameter("durations");
        int reproductions = Integer.parseInt(request.getParameter("reproductions"));
        String description = request.getParameter("description");
        String format = request.getParameter("format");

        // Create a Video object
        Video video = new Video(id, title, autor, creationDate, durations, reproductions, description, format);

        // Save the video to the database
        if (video.saveVideo()) {
            response.sendRedirect("listadoVid.jsp"); // Redirect to the list page
        } else {
            response.getWriter().println("Error saving video"); // Handle error
        }
    }
}

