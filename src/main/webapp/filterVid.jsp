<%-- 
    Document   : filterVid.jsp
    Created on : 23 mar 2024, 17:46:52
    Author     : davidpb0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.io.File" %>
<%@ page import="java.nio.file.Files" %>
<%@ page import="java.util.Base64" %>
<%@ page import="java.util.Map" %>
<%@ page import="model.Video" %>
<%@ page import="java.util.AbstractMap.SimpleEntry" %>
<%
    // Check if user is logged in
    String user = (String) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp"); 
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Videos Page</title>
    <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css">
    <style>
        .swiper-container {
            width: 500px;
            margin: 0 auto; 
        }

        .swiper-slide {
            text-align: center;
            font-size: 18px;
            background: #fff;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .video-container {
            max-width: 100%;
            height: auto;
        }
        
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        .container {
            display: flex;
            height: 100vh;
        }

        .sidebar {
            width: 150px;
            background-color: #1e2852;
            color: #fff;
            padding: 10px;
            z-index: 100;
        }

        .main-content {
            margin-top: 85px;
            flex: 1;
            padding: 20px;
            background-color: #fff; 
            color: #000; 
            position: relative;
        }

        .toolbar {
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 10px 25px;
            background-color: #1e2852;
            color: #fff;
            position: fixed;
            width: 100%;
            top: 0;
            left: 0;
            z-index: 1000;
        }

        .menu-button {
            display: block;
            width: 100%;
            padding: 10px 20px;
            margin-bottom: 10px;
            text-align: left;
            background-color: #44506a;
            color: #fff;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .menu-button:hover {
            background-color: #1e2852;
        }

        .content {
            margin-top: 200px;
        }

        .sidebar ul {
            margin-top: 150px;
            padding: 0;
            list-style: none;
        }
        
        input[type="text"]#format {
            border: none;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #4caf50;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }
        

    </style>
</head>
<body>
    <div class="toolbar">
        <form id="fetchVideosForm" action="servletREST" method="post">
            <input type="hidden" name="action" value="fetchFilterVideos">
            <label for="filter">Filter By:</label>
            <select id="filter" name="filter">
                <option value="author">Author</option>
                <option value="title">Title</option>
                <option value="creationdate">Creation Date</option>
            </select>
            <input type="text" id="value" name="value">
            <button type="submit">Search</button>
        </form>
    </div>
    
    <script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
    
    <div class="container">
        <div class="sidebar">
            <ul>
                <button class="menu-button" onclick="navigateToHome()">Go Back</button>
                
            </ul>
        </div>

        <div class="main-content">


            <div class="swiper-container">
                <div class="swiper-wrapper">
                    <% 
                    Map<String, SimpleEntry<Video, File>> videoFileMap = (Map<String, SimpleEntry<Video, File>>) request.getAttribute("videoMap");
                    if (videoFileMap != null && !videoFileMap.isEmpty()) {
                        for (Map.Entry<String, SimpleEntry<Video, File>> entry : videoFileMap.entrySet()) {
                            SimpleEntry<Video, File> videoEntry = entry.getValue();
                            Video videoMetadata = videoEntry.getKey();
                            File videoFile = videoEntry.getValue();
                            try {
                                byte[] videoBytes = Files.readAllBytes(videoFile.toPath());
                                String base64Video = Base64.getEncoder().encodeToString(videoBytes);
                    %>
                                <div class="swiper-slide">
                                    <div style="margin: 10px;">
                                        <h3 style="margin: 0;"><%= videoMetadata.getTitle() %></h3>
                                        <p style="margin: 0;"><%= videoMetadata.getDescription() %></p>
                                        <video id ="<%= videoFile.getName() %>" class="video-container" controls>
                                            <source src="data:video/<%= videoMetadata.getFormat() %>;base64, <%= base64Video %>" type="video/<%= videoMetadata.getFormat() %>">
                                            Your browser does not support the video tag.
                                        </video>
                                        <div style="font-size: 12px; display: flex; align-items: center;">
                                            <p style="margin: 0; margin-right: 330px;">Uploaded by <%= videoMetadata.getAuthor() %></p>
                                            <p style="margin: 0;"><%= videoMetadata.getReproductions() %> views</p>
                                        </div>
                                    </div>
                                     <script>
                                        var videoElement = document.getElementById("<%= videoFile.getName() %>");

                                        videoElement.addEventListener('play', function() {
                                            event.preventDefault();
                                            var form = new FormData(); 
                                            form.append('action', 'addVisualization');

                                            form.append('title', '<%= videoMetadata.getTitle() %>');
                                            form.append('author', '<%= videoMetadata.getAuthor() %>');

                                            fetch('servletREST?action=addVisualization&title=<%= videoMetadata.getTitle() %>&author=<%= videoMetadata.getAuthor() %>', {
                                                method: 'POST',
                                                body: form
                                            })
                                            .then(response => {
                                                console.log('Form submitted successfully');
                                            })
                                            .catch(error => {
                                                console.error('Error submitting form:', error);
                                            });

                                        });
                                    </script>
                                </div>

                    <% 
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                    %>
                        <p style="margin-top: 100px">No videos found</p>
                    <%
                    }
                    %>
                </div>
                <div class="swiper-pagination"></div>

                <div class="swiper-button-prev"></div>
                <div class="swiper-button-next"></div>
            </div>
        </div>
    </div>

    <script>
        // Initialization of Swiper after DOM ready
        document.addEventListener('DOMContentLoaded', function () {
            var swiper = new Swiper('.swiper-container', {
                loop: true,
                spaceBetween: 100,
                slidesPerView: '1',

                pagination: {
                    el: '.swiper-pagination',
                    clickable: true
                },

                navigation: {
                    nextEl: '.swiper-button-next',
                    prevEl: '.swiper-button-prev'
                }
            });
        });
        
        function navigateToHome() {
            window.location.href = 'home.jsp';
        }
        
    </script>
</body>
</html>
