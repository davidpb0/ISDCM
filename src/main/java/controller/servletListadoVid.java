/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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

@WebServlet("/servletListadoVid")
public class ServletListadoVid extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get all videos from the database
        List<Video> videos = Video.getAllVideos();

        // Set the videos as an attribute in the request
        request.setAttribute("videos", videos);

        // Forward to the list page
        RequestDispatcher dispatcher = request.getRequestDispatcher("listadoVid.jsp");
        dispatcher.forward(request, response);
    }
}

